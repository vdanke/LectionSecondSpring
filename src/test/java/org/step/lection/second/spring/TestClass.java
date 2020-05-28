package org.step.lection.second.spring;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.step.lection.second.spring.configuration.DatabaseConfiguration;
import org.step.lection.second.spring.configuration.web.WebConfig;
import org.step.lection.second.spring.model.Message;
import org.step.lection.second.spring.model.User;
import org.step.lection.second.spring.repository.UserRepository;
import org.step.lection.second.spring.repository.UserRepositorySpringData;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class, WebConfig.class})
@WebAppConfiguration
@Transactional
public class TestClass {

    private UserRepository userRepository;
    private ValidatorFactory validatorFactory;
    @PersistenceContext
    private EntityManager entityManager;
    private Set<String> usernameSet;
    private UserRepositorySpringData userRepositorySpringData;

    private static Validator validator;

    @BeforeClass
    public static void setupFull() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Before
    public void setup() {
        User first = new User("firstfirst@mail.ru", "first", 18);
        User second = new User("secondsecond@mail.ru", "second", 30);

        Message firstMessage = new Message("first");
        Message secondMessage = new Message("second");

        first.setMessageList(Arrays.asList(firstMessage, secondMessage));
        second.setMessageList(Arrays.asList(firstMessage, secondMessage));

        Set<ConstraintViolation<User>> validate = validator.validate(first);

        validate.stream().map(ConstraintViolation::getMessage)
                .forEach(System.out::println);

        usernameSet = userRepositorySpringData.saveAll(Arrays.asList(first, second))
                .stream()
                .map(User::getUsername)
                .collect(Collectors.toSet());
    }

    @Test
    public void validatorFactoryShouldNotBeNull() {
        Assert.assertNotNull(validatorFactory);
    }

    @Test
    public void shouldSaveUserToDatabase() {
        Object[] objects = usernameSet.toArray();

        Optional<User> byUsername = userRepositorySpringData.findByUsernameNamedQuery((String) objects[0]);

        Assert.assertTrue(byUsername.isPresent());
    }

    @Test
    public void shouldReturnListOfMessagesOfConcreteUser() {
//        List<User> users = userRepositorySpringData.findAll();
//        List<User> collect = userRepositorySpringData.findAllCustomMethod()
//                .sorted(Comparator.comparing(User::getUsername))
//                .collect(Collectors.toList());

        User user = userRepositorySpringData.findAllCustomMethod().unordered().findAny().orElseThrow(() -> new IllegalArgumentException("User is null"));

        user.getMessageList().forEach(message -> {
            System.out.println(message.getDescription());
        });
    }

    @Test
    public void shouldReturnListOfUsersByAttribute() {
        List<User> attribute = userRepositorySpringData.findAllByAttribute("attribute");
    }

    @Test
    public void shouldFindAllUsersFromDatabase() {
        List<User> allUsers = userRepository.findAll();

        allUsers.forEach(user -> System.out.println(user.getId()));

        Assert.assertEquals(2, allUsers.size());
    }

    @After
    public void clean() {
        userRepositorySpringData.deleteAll();
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setValidatorFactory(ValidatorFactory validatorFactory) {
        this.validatorFactory = validatorFactory;
    }

    @Autowired
    public void setUserRepositorySpringData(UserRepositorySpringData userRepositorySpringData) {
        this.userRepositorySpringData = userRepositorySpringData;
    }
}
