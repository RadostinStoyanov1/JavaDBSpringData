package p11springdataintro.service;

import p11springdataintro.data.entities.Account;

import java.math.BigDecimal;

public interface AccountService {
    void withdrawMoney(BigDecimal money, Integer id);
    void transferMoney(BigDecimal money, Integer id);
    void createAccount(Account account);
}
