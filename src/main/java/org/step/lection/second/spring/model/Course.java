package org.step.lection.second.spring.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

//    @ManyToMany(mappedBy = "courseList")
//    private List<User> userList;
//
//    @OneToMany(mappedBy = "course")
//    private List<CourseCompositeUsers> courseUserList;

    @OneToMany(mappedBy = "course")
    private List<CourseRating> courseRatingList;

    public Course() {
    }

    public Course(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public List<CourseRating> getCourseRatingList() {
        return courseRatingList;
    }

    public void setCourseRatingList(List<CourseRating> courseRatingList) {
        this.courseRatingList = courseRatingList;
    }

    //    public List<CourseCompositeUsers> getCourseUserList() {
//        return courseUserList;
//    }
//
//    public void setCourseUserList(List<CourseCompositeUsers> courseUserList) {
//        this.courseUserList = courseUserList;
//    }

    //    public List<User> getUserList() {
//        return userList;
//    }
//
//    public void setUserList(List<User> userList) {
//        this.userList = userList;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
