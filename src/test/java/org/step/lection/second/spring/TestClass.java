package org.step.lection.second.spring;

import javassist.NotFoundException;
import org.hibernate.mapping.Array;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.step.lection.second.spring.configuration.DatabaseConfiguration;
import org.step.lection.second.spring.configuration.web.WebConfig;
import org.step.lection.second.spring.model.Message;
import org.step.lection.second.spring.model.User;
import org.step.lection.second.spring.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class, WebConfig.class})
@WebAppConfiguration
@Transactional
public class TestClass {

    private UserRepository userRepository;
    private ValidatorFactory validatorFactory;
    @PersistenceContext
    private EntityManager entityManager;
    private List<String> usernameList;

    private static Validator validator;

    @BeforeClass
    public static void setupFull() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Before
    public void setup() {
        usernameList = new ArrayList<>();

        User first = new User("firstfirst@mail.ru", "first", 18);
        User second = new User("secondsecond@mail.ru", "second", 30);

        Message firstMessage = new Message("first");
        Message secondMessage = new Message("second");

        first.setMessageList(Arrays.asList(firstMessage, secondMessage));
        second.setMessageList(Arrays.asList(firstMessage, secondMessage));

        Set<ConstraintViolation<User>> validate = validator.validate(first);

        validate.stream().map(ConstraintViolation::getMessage)
                .forEach(System.out::println);

        userRepository.save(first);
        userRepository.save(second);

        usernameList.add(first.getUsername());
        usernameList.add(second.getUsername());
    }

    @Test
    public void validatorFactoryShouldNotBeNull() {
        Assert.assertNotNull(validatorFactory);
    }

    @Test
    public void shouldSaveUserToDatabase() {
        Optional<User> byUsername = userRepository.findByUsername(usernameList.get(0));

        Assert.assertTrue(byUsername.isPresent());
    }

    @Test
    public void shouldReturnListOfMessagesOfConcreteUser() {
        List<User> users = entityManager.createQuery("select u from User u", User.class).getResultList();

        User user = users.stream().findAny().orElseThrow(() -> new IllegalArgumentException("User is null"));

        user.getMessageList().forEach(message -> {
            System.out.println(message.getDescription());
        });
    }

    @Test
    public void shouldFindAllUsersFromDatabase() {
        List<User> allUsers = userRepository.findAll();

        allUsers.forEach(user -> System.out.println(user.getId()));

        Assert.assertEquals(2, allUsers.size());
    }

    @After
    public void clean() {
        entityManager.createNativeQuery("DELETE FROM USERS").executeUpdate();
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setValidatorFactory(ValidatorFactory validatorFactory) {
        this.validatorFactory = validatorFactory;
    }
}
