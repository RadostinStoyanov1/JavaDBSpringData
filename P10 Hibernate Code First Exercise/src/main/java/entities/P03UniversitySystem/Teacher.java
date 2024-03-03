package entities.P03UniversitySystem;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "teachers")
public class Teacher extends PersonInformation{
    @Column(nullable = false)
    private String email;
    @Column(name = "salary_per_hour", nullable = false)
    private double salaryPerHour;
    @OneToMany(mappedBy = "teacher", targetEntity = Course.class)
    private Set<Course> courses;

    public Teacher() {}

    public Teacher(String firstName, String lastName, String email, double salaryPerHour) {
        super(firstName, lastName);
        this.email = email;
        this.salaryPerHour = salaryPerHour;
        this.courses = new LinkedHashSet<>();
    }
}
