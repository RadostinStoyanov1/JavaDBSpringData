package entities.P02SalesDatabase;

import entities.BaseEntity;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(name = "credit_card_number")
    private String creditCardNumber;
    @OneToMany(mappedBy = "customer")
    private Set<Sale> sales;
}
