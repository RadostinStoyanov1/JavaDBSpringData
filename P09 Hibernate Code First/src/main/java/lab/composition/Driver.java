package lab.composition;

import jakarta.persistence.*;
import lab.inheritance.Truck;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "drivers")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "full_name")
    private String fullName;

    @ManyToMany
    @JoinTable(name = "trucks_drivers",
            joinColumns = @JoinColumn(name = "driver_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "truck_id", referencedColumnName = "id"))
    private List<Truck> trucks;

    public Driver() {
        this.trucks = new ArrayList<>();
    }
}
