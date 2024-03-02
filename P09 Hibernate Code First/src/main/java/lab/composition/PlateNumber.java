package lab.composition;

import jakarta.persistence.*;

import java.math.BigInteger;

@Entity
@Table(name = "plate_numbers")
public class PlateNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String number;
    @OneToOne(mappedBy = "plateNumber", targetEntity = CompositionCar.class)
    private CompositionCar compositionCar;

    public PlateNumber() {}

    public PlateNumber(String number) {
        this.number = number;
    }
}
