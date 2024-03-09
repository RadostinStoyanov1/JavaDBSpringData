package p11springdataintro.service;

import p11springdataintro.data.entities.Account;
import p11springdataintro.data.entities.User;

public interface UserService{
    void register(User user);

    User findUserById(int id);

    User findByName(String username);
}
