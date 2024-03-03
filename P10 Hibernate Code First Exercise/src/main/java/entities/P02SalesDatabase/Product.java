package entities.P02SalesDatabase;

import entities.BaseEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;
    @Basic
    private Double quantity;
    @Column(nullable = false)
    private BigDecimal price;
    @OneToMany(mappedBy = "product")
    private Set<Sale> sales;
}
