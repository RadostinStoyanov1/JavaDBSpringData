package entities.P05BillsPaymentSystem;

import entities.BaseEntity;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BillingDetails extends BaseEntity {
    @Column(nullable = false)
    private int number;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    protected BillingDetails() {}

    public BillingDetails(int number, User user) {
        this.number = number;
        this.user = user;
    }
}
