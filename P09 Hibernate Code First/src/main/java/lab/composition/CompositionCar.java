package lab.composition;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "composition_car")
public class CompositionCar {
    private static final String CAR_TYPE = "CAR";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Basic
    private String type;
    @Basic
    private String model;
    @Basic
    private BigDecimal price;
    @Column(name = "fueal_type")
    private String fuelType;
    @Basic
    private int seats;
    @OneToOne(optional = false, cascade = {CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "plate_id", referencedColumnName = "id")
    private PlateNumber plateNumber;

    public CompositionCar() {}

    public CompositionCar(String model, BigDecimal price, String fuelType, int seats, PlateNumber plateNumber) {
        //super(CAR_TYPE, model, price, fuelType);
        this.type = CAR_TYPE;
        this.model = model;
        this.price = price;
        this.fuelType = fuelType;
        this.seats = seats;
        this.plateNumber = plateNumber;
    }
}
