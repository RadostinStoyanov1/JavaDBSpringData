package entities.P03UniversitySystem;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student extends PersonInformation{
    @Column(name = "average_grade")
    private double averageGrade;
    @Basic
    private int attendance;
    @ManyToMany(mappedBy = "students", targetEntity = Course.class)
    private Set<Course> courses;

    protected Student() {}

    public Student(String firstName, String lastName) {
        super(firstName, lastName);
        this.courses = new LinkedHashSet<>();
    }
}
