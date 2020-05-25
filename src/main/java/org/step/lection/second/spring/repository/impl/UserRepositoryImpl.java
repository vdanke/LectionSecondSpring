package org.step.lection.second.spring.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.step.lection.second.spring.model.User;
import org.step.lection.second.spring.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

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
        return Optional.ofNullable(
                entityManager.find(User.class, id)
        );
    }

    @Override
    public boolean save(User user) {
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
