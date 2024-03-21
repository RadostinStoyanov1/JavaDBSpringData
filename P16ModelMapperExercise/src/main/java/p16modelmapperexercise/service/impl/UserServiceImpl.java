package p16modelmapperexercise.service.impl;

import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import p16modelmapperexercise.data.entities.User;
import p16modelmapperexercise.data.repositories.UserRepository;
import p16modelmapperexercise.service.DTOs.UserLoginDTO;
import p16modelmapperexercise.service.DTOs.UserRegisterDTO;
import p16modelmapperexercise.service.UserService;
import p16modelmapperexercise.util.ValidatorService;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private User logedInUser;
    private final UserRepository userRepository;
    private final ValidatorService validatorService;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ValidatorService validatorService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.validatorService = validatorService;
        this.modelMapper = modelMapper;
    }

    @Override
    public String registerUser(UserRegisterDTO userRegisterDTO) {
        if (!validatorService.isValid(userRegisterDTO)) {
            Set<ConstraintViolation<UserRegisterDTO>> violationsSet = this.validatorService.validate(userRegisterDTO);
            return violationsSet.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("\n"));
        }

        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {
            return "Passwords don't match!";
        }

        Optional<User> optionalUser = this.userRepository.findUserByEmail(userRegisterDTO.getEmail());

        if (optionalUser.isPresent()) {
            return "User with such email already exists.";
        }

        User user = this.modelMapper.map(userRegisterDTO, User.class);
        if (this.userRepository.count() == 0) {
            user.setAdmin(true);
        }
        this.userRepository.saveAndFlush(user);
        return String.format("%s was registered.", user.getFullName());
    }

    @Override
    public String loginUser(UserLoginDTO userLoginDTO) {
        Optional<User> optionalUser = this.userRepository.findByEmailAndPassword(userLoginDTO.getEmail(), userLoginDTO.getPassword());
        if (optionalUser.isEmpty()) {
            return "Wrong username / password";
        }

        this.logedInUser = optionalUser.get();

        return String.format("Successfully logged in %s", logedInUser.getFullName());
    }

    @Override
    public String logout() {
        if (this.logedInUser == null) {
            return "Cannot log out. No user was logged in.";
        }

        String output = String.format("User %s successfully logged out", logedInUser.getFullName());
        this.logedInUser = null;
        return output;
    }

    @Override
    public User getLoggedIn() {
        return this.logedInUser;
    }
}
