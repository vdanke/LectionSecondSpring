package org.step.lection.second.spring.repository;

import org.step.lection.second.spring.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    Optional<User> findById(Long id);

    boolean save(User user);

    Long getMaxId();
}
