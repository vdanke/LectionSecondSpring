package org.step.lection.second.spring.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.step.lection.second.spring.model.User;
import org.step.lection.second.spring.repository.UserRepository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    @Autowired
    public UserRepositoryImpl(ValidatorFactory validatorFactory) {
        this.validatorFactory = validatorFactory;
        this.validator = validatorFactory.getValidator();
    }

//    @Autowired
//    private EntityManagerFactory entityManagerFactory;
//
//    public void init() {
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("select u from User u", User.class)
                .getResultList();
    }

    @Override
    public Optional<User> findById(UUID id) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("User.findAllMessages");
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("javax.persistence.loadgraph", entityGraph);

        return Optional.ofNullable(
                entityManager.find(User.class, id, attributes)
        );
    }

    @Override
    public boolean save(User user) {
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        if (constraintViolations.size() != 0) {
            throw new IllegalStateException("Constraint violations");
        }
        entityManager.persist(user);

        return true;
    }

    @Override
    public Optional<User> findByUsername(String username) {
//        Query query = entityManager.createQuery("select u from User u where u.username=:username");
//        TypedQuery<User> query1 = entityManager.createQuery("select u from User u where u.username=:username", User.class);
        return Optional.ofNullable(
                entityManager.createQuery("select u from User u where u.username=:username", User.class)
                .setParameter("username", username)
//                .setParameter(1, username)
                .getSingleResult()
        );
    }

    //    @Autowired
//    public void setDataSource(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
}
