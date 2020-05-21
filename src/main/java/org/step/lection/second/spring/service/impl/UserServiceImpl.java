package org.step.lection.second.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.step.lection.second.spring.model.User;
import org.step.lection.second.spring.repository.UserRepository;
import org.step.lection.second.spring.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID is null");
        }
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User with ID %d not found", id)));
    }

    @Override
    public boolean save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }
        boolean isPasswordEmpty = user.getPassword().isEmpty();
        boolean isUsernameEmpty = user.getUsername().isEmpty();

        if (isPasswordEmpty || isUsernameEmpty) {
            throw new IllegalArgumentException("Username or password is empty");
        }
        Long maxId = userRepository.getMaxId();

        user.setId(maxId);

        return userRepository.save(user);
    }
}