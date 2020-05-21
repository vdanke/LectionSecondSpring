package org.step.lection.second.spring.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.step.lection.second.spring.model.User;
import org.step.lection.second.spring.model.UserMapper;
import org.step.lection.second.spring.repository.UserRepository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    @Autowired
    public UserRepositoryImpl(@Qualifier("devDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM USERS", new UserMapper());
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE ID = ?", new Object[]{id}, new UserMapper())
        );
    }

    @Override
    public boolean save(User user) {
        int update = jdbcTemplate.update("INSERT INTO USERS(id, username, password) value (?, ?, ?)",
                user.getId(),
                user.getUsername(),
                user.getPassword()
        );
        return update != -1;
    }

    @Override
    public Long getMaxId() {
        return jdbcTemplate.queryForObject("SELECT MAX(id) FROM USERS", Long.class);
    }

    //    @Autowired
//    public void setDataSource(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
}
