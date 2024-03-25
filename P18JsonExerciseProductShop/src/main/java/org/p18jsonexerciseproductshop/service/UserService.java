package org.p18jsonexerciseproductshop.service;

import org.p18jsonexerciseproductshop.service.dtos.UserAndProductDto;
import org.p18jsonexerciseproductshop.service.dtos.UserSoldProductsDto;

import java.io.FileNotFoundException;
import java.util.List;

public interface UserService {

    void seedUsers() throws FileNotFoundException;

    List<UserSoldProductsDto> getAllUsersWithSoldItems();

    void printAllUsersAndSoldItems();

    UserAndProductDto getALlUsersWithSoldProducts();

    void printGetUserAndProductDto();
}
