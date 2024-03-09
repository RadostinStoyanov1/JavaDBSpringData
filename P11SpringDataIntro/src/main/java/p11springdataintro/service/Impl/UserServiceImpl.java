package p11springdataintro.service.Impl;

import org.springframework.stereotype.Service;
import p11springdataintro.data.entities.User;
import p11springdataintro.data.repositories.UserRepository;
import p11springdataintro.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    public final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(User user) {
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public User findUserById(int id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        System.out.printf("No such user with id: %d\n", id);
        return null;
    }

    @Override
    public User findByName(String username) {
        return this.userRepository.findUserByUsername(username);
    }
}
