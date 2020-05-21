package org.step.lection.second.spring;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.step.lection.second.spring.configuration.DatabaseConfiguration;
import org.step.lection.second.spring.model.User;
import org.step.lection.second.spring.repository.UserRepository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class})
public class TestClass {

    private DataSource dataSource;
    private UserRepository userRepository;

    @Test
    public void dataSourceShouldNotBeNull() {
        Assert.assertNotNull(userRepository);
        Assert.assertNotNull(dataSource);
    }

    @Test
    public void shouldReturnListOfUsers() {
        List<User> all = userRepository.findAll();

        Assert.assertNotNull(all);
    }

    @Test
    public void shouldReturnUserById() {
        List<User> all = userRepository.findAll();

        Long id = all.get(0).getId();

        Optional<User> userById = userRepository.findById(id);

        Assert.assertTrue(userById.isPresent());
        Assert.assertEquals(id, userById.get().getId());
    }

    @Autowired
    public void setDataSource(@Qualifier(value = "devDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
