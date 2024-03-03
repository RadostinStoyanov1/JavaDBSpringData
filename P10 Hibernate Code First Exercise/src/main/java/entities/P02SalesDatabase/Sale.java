package entities.P02SalesDatabase;

import entities.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "sales")
public class Sale extends BaseEntity {
    @Column(name = "local_date")
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @OneToOne
    @JoinColumn(name = "store_location_id")
    private StoreLocation storeLocation;

}
