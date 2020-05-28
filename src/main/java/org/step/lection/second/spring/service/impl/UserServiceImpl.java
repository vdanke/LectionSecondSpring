package org.step.lection.second.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.step.lection.second.spring.model.User;
import org.step.lection.second.spring.repository.UserRepository;
import org.step.lection.second.spring.repository.UserRepositorySpringData;
import org.step.lection.second.spring.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRepositorySpringData userRepositorySpringData;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRepositorySpringData userRepositorySpringData) {
        this.userRepository = userRepository;
        this.userRepositorySpringData = userRepositorySpringData;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("ID is null");
        }
        UUID userId = UUID.fromString(id);

        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User with ID %d not found", id)));
    }

    @Override
    @Transactional
    public boolean save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }
        boolean isPasswordEmpty = user.getPassword().isEmpty();
        boolean isUsernameEmpty = user.getUsername().isEmpty();

        if (isPasswordEmpty || isUsernameEmpty) {
            throw new IllegalArgumentException("Username or password is empty");
        }
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found"));
    }

//    @EventListener(value = {ContextRefreshedEvent.class})
//    public void createUsers() {
//        User first = new User("firstuser@mail.ru", "firstpassword", 20);
//        User second = new User("seconduser@mail.ru", "firstpassword", 20);
//        User third = new User("thirduser@mail.ru", "firstpassword", 20);
//
//        userRepositorySpringData.saveAll(Arrays.asList(first, second, third));
//    }
}
