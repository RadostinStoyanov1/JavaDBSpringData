package entities.P05BillsPaymentSystem;

import jakarta.persistence.*;

@Entity
@Table(name = "credit_cards")
public class CreditCard extends BillingDetails{
    @Column
    @Enumerated(value = EnumType.STRING)
    private CardType type;
    @Column(name = "expiration_month")
    private int expirationMonth;
    @Column(name = "expiration_year")
    private int expirationYear;

    protected CreditCard() {}

    public CreditCard(CardType type, int number, User user) {
        super(number, user);
        this.type = type;
    }
}
