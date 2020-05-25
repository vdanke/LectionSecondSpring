package org.step.lection.second.spring;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.step.lection.second.spring.configuration.DatabaseConfiguration;
import org.step.lection.second.spring.model.User;
import org.step.lection.second.spring.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class})
public class TestClass {

    private UserRepository userRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void dataSourceShouldNotBeNull() {
        Assert.assertNotNull(userRepository);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void shouldSaveUserToDatabase() {
        User first = new User("first", "first");
        User second = new User("second", "second");

        userRepository.save(first);
        userRepository.save(second);

        Optional<User> byUsername = userRepository.findByUsername(first.getUsername());

        Assert.assertTrue(byUsername.isPresent());
    }

    @Test
    @Transactional(readOnly = true)
    public void shouldFindAllUsersFromDatabase() {
        List<User> allUsers = userRepository.findAll();

        allUsers.forEach(user -> System.out.println(user.getId()));

        Assert.assertEquals(2, allUsers.size());
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
