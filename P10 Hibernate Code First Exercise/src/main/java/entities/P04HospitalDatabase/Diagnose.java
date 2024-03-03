package entities.P04HospitalDatabase;

import entities.BaseEntity;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "diagnoses")
public class Diagnose extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Basic
    private String comments;
    @OneToMany(mappedBy = "diagnose", targetEntity = Visitation.class)
    private Set<Visitation> visitations;
    @ManyToMany
    @JoinTable(name = "diagnoses_medicaments",
        joinColumns = @JoinColumn(name = "diagnose_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "medicament_id", referencedColumnName = "id"))
    private Set<Medicament> medicaments;
}
