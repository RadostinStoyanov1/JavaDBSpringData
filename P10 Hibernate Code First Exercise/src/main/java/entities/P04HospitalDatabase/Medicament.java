package entities.P04HospitalDatabase;

import entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "medicaments")
public class Medicament extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @ManyToMany(mappedBy = "medicaments", targetEntity = Diagnose.class)
    private Set<Diagnose> diagnoses;
}
