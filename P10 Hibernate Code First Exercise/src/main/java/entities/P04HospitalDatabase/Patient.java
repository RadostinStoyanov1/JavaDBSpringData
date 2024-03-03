package entities.P04HospitalDatabase;

import entities.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "patients")
public class Patient extends BaseEntity {
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Basic
    private String address;
    @Basic
    private String email;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Basic
    private String picture;
    @Column(name = "has_insurance")
    private boolean hasInsurance;
    @OneToMany(mappedBy = "patient", targetEntity = Visitation.class)
    private Set<Visitation> visitations;
}
