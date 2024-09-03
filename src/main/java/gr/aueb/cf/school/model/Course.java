package gr.aueb.cf.school.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "courses")
@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Course extends AbstractEntity {

    @Column(length = 50, nullable = false, unique = true)
    private String title;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = true)
    private Teacher teacher;

    @Getter(AccessLevel.PROTECTED)
    @ManyToMany
    @JoinTable(
            name = "courses_students",
            joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"))
    Set<Student> students = new HashSet<>();


    public Set<Student> getAllStudents() {
        return Collections.unmodifiableSet(students);
    }

    public void addStudent(Student student) {
        this.students.add(student);
        student.getCourses().add(this);
    }

    public void deleteStudent(Student student) {
        students.remove(student);
        student.getCourses().remove(this);
    }

    @Override
    public String toString() {
        return "Course{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", teacher=" + teacher +
                '}';
    }
}