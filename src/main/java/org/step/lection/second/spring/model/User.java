package org.step.lection.second.spring.model;

import org.step.lection.second.spring.service.validation.EmailConstraint;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.step.lection.second.spring.model.User.USER_MESSAGES_ENTITY_GRAPH;

@Entity
@Table(name = "users")
@NamedEntityGraph(name = USER_MESSAGES_ENTITY_GRAPH, attributeNodes = {
            @NamedAttributeNode(value = "messageList"),
//            @NamedAttributeNode(value = "courseRatingList", subgraph = "user_courses")
        }
//        subgraphs = {
//            @NamedSubgraph(
//                    name = "user_courses",
//                    attributeNodes = {
//                            @NamedAttributeNode("course")
//                    }
////                    type = Course.class
//            )
//        }
)
@NamedQuery(name = "User.findByUsername", query = "select u from User u where u.username=?1")
public class User extends AbstractBaseEntity {

    public static final String USER_MESSAGES_ENTITY_GRAPH = "User.findAllMessages";

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_user_seq_generator")
//    @SequenceGenerator(name = "id_user_seq_generator", allocationSize = 1, sequenceName = "bd_seq_name")
//    private Long id;
    @GeneratedValue
    private UUID id;

    @EmailConstraint(min = 10, max = 255)
    @Column(name = "username", unique = true, nullable = false, insertable = true, updatable = true)
    private String username;

    @NotBlank(message = "Your password cannot be blank")
    @Column(name = "password", length = 512)
    private String password;

    @Min(value = 18, message = "You are too young")
    @Max(value = 100, message = "You are so old, take care of your grand-grand children")
    @Column(name = "age", nullable = false)
    private Integer age;

//    @ManyToMany
//    @JoinTable(
//            name = "course_user",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "course_id")
//    )
//    private List<Course> courseList;

//    @OneToMany(mappedBy = "user")
//    private List<CourseCompositeUsers> courseUsers;

    @OneToMany(mappedBy = "user")
    private List<CourseRating> courseRatingList;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Portfolio portfolio;

    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Message> messageList;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(value = EnumType.STRING)
    private Set<Role> authorities = new HashSet<>();

    public User() {
    }

    public User(String username, String password, int age) {
        this.username = username;
        this.password = password;
        this.age = age;
    }

    public List<CourseRating> getCourseRatingList() {
        return courseRatingList;
    }

    public void setCourseRatingList(List<CourseRating> courseRatingList) {
        this.courseRatingList = courseRatingList;
    }

    //    public List<CourseCompositeUsers> getCourseUsers() {
//        return courseUsers;
//    }
//
//    public void setCourseUsers(List<CourseCompositeUsers> courseUsers) {
//        this.courseUsers = courseUsers;
//    }

    //    public List<Course> getCourseList() {
//        return courseList;
//    }
//
//    public void setCourseList(List<Course> courseList) {
//        this.courseList = courseList;
//    }

    public Set<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
