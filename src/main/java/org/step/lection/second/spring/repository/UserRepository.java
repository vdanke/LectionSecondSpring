package org.step.lection.second.spring.repository;

import org.step.lection.second.spring.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    List<User> findAll();

    Optional<User> findById(UUID id);

    boolean save(User user);

    Optional<User> findByUsername(String username);
}
