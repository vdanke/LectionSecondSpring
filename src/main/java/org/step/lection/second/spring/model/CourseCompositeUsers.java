package org.step.lection.second.spring.model;

import javax.persistence.*;

//@Entity
//@Table(name = "course_composite_users")
public class CourseCompositeUsers {

    @EmbeddedId
    private CourseUserKey courseUserKey;

    @ManyToOne
    @MapsId(value = "course_id")
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @MapsId(value = "user_id")
    @JoinColumn(name = "user_id")
    private User user;

    public CourseUserKey getCourseUserKey() {
        return courseUserKey;
    }

    public void setCourseUserKey(CourseUserKey courseUserKey) {
        this.courseUserKey = courseUserKey;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
