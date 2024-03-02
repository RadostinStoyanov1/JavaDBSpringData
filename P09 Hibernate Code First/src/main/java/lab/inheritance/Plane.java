package lab.inheritance;

import jakarta.persistence.*;
import lab.composition.Company;

import java.math.BigDecimal;

@Entity
public class Plane extends Vehicle{
    private static final String PLANE_TYPE = "PLANE";

    @Basic
    private String airline;
    @Column(name = "passenger_capacity")
    private int passengerCapacity;
    @ManyToOne
    private Company owner;

    public Plane() {};

    public Plane(String model, BigDecimal price, String fuelType, String airline, int passengerCapacity, Company owner) {
        super(PLANE_TYPE, model, price, fuelType);
        this.airline = airline;
        this.passengerCapacity = passengerCapacity;
        this.owner = owner;
    }
}
