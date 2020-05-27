package org.step.lection.second.spring.service;

import org.step.lection.second.spring.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(String id);

    boolean save(User user);

    User findByUsername(String username);
}
