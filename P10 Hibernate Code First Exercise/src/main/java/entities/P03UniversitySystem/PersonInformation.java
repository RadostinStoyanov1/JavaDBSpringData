package entities.P03UniversitySystem;

import entities.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "person_informations")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PersonInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "phone_number")
    private String phoneNumber;

    protected PersonInformation() {}

    public PersonInformation(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
