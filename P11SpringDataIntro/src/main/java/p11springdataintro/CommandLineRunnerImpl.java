package p11springdataintro;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import p11springdataintro.data.entities.Account;
import p11springdataintro.data.entities.User;
import p11springdataintro.service.AccountService;
import p11springdataintro.service.UserService;

import java.math.BigDecimal;
import java.util.Set;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final UserService userService;
    private final AccountService accountService;

    public CommandLineRunnerImpl(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User("Dilyan", 26);
        this.userService.register(user);

        User user2 = new User("Lilyan", 25);
        this.userService.register(user2);

        User foundUser = this.userService.findUserById(1);
        User foundUser2 = this.userService.findUserById(2);
        Account account = new Account(BigDecimal.valueOf(6000), foundUser2);

        this.accountService.createAccount(account);
        Set<Account> accounts = user.getAccounts();
        System.out.println("Size: " + accounts.size());
        Set<Account> accounts2 = user2.getAccounts();
        System.out.println("Size: " + accounts2.size());
    }
}
