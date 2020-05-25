package org.step.lection.second.spring.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_user_seq_generator")
//    @SequenceGenerator(name = "id_user_seq_generator", allocationSize = 1, sequenceName = "bd_seq_name")
//    private Long id;
    @GeneratedValue
    private UUID id;
    @Column(name = "username", unique = true, nullable = false, insertable = true, updatable = true)
    private String username;
    @Column(name = "password", length = 512)
    private String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
