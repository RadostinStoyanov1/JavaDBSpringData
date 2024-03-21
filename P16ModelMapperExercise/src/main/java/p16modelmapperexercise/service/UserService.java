package p16modelmapperexercise.service;

import p16modelmapperexercise.data.entities.User;
import p16modelmapperexercise.service.DTOs.UserLoginDTO;
import p16modelmapperexercise.service.DTOs.UserRegisterDTO;

public interface UserService {
    String registerUser(UserRegisterDTO userRegisterDTO);

    String loginUser(UserLoginDTO userLoginDTO);

    String logout();

    User getLoggedIn();
}
