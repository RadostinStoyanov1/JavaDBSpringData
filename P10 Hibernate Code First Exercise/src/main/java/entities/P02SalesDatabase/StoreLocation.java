package entities.P02SalesDatabase;

import entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "store_locations")
public class StoreLocation extends BaseEntity {
    @Column(name = "location_name", nullable = false)
    private String locationName;
    @OneToOne(mappedBy = "storeLocation")
    private Sale sale;
}
