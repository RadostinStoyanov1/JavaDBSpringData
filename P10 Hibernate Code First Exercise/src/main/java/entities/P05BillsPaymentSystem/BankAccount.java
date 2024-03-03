package entities.P05BillsPaymentSystem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "bank_account")
public class BankAccount extends BillingDetails{
    @Column
    private String name;
    @Column(name = "swift_code")
    private String swiftCode;

    public BankAccount() {}
}
