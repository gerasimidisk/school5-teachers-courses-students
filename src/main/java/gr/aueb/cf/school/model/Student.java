package gr.aueb.cf.school.model;

import lombok.*;
import org.w3c.dom.stylesheets.LinkStyle;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "students")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstname;
    private String lastname;

    @Getter(AccessLevel.PROTECTED)
    @ManyToMany
    @JoinTable(
            name = "students_courses",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id") )
    private Set<Course> courses = new HashSet<>();

    public Set<Course> getAllCourses() {
        return Collections.unmodifiableSet(courses);
    }

    public void addCourse(Course course) {
        this.courses.add(course);
        course.getStudents().add(this);
    }

    public void deleteCourse(Course course) {
        courses.remove(course);
        course.getStudents().remove(this);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}