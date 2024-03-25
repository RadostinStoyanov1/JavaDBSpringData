package org.p18jsonexerciseproductshop.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.p18jsonexerciseproductshop.data.entities.User;
import org.p18jsonexerciseproductshop.data.repositories.UserRepository;
import org.p18jsonexerciseproductshop.service.UserService;
import org.p18jsonexerciseproductshop.service.dtos.*;
import org.p18jsonexerciseproductshop.util.ValidationUtil;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final String FILE_PATH = "C:\\Rado\\Intellij Tasks Spring Data\\P18JsonExerciseProductShop\\src\\main\\resources\\09. DB-Advanced-JSON-Processing-Exercises-Resources\\09. DB-Advanced-JSON-Processing-Exercises\\users.json";

    private final ModelMapper modelMapper;
    private final Gson gson;
    private final UserRepository userRepository;
    private final ValidationUtil validationUtil;

    public UserServiceImpl(ModelMapper modelMapper, Gson gson, UserRepository userRepository, ValidationUtil validationUtil) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.userRepository = userRepository;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedUsers() throws FileNotFoundException {
        if (this.userRepository.count() == 0) {
            UserSeedDto[] seedUsersArray = this.gson.fromJson(new FileReader(FILE_PATH), UserSeedDto[].class);
            for (UserSeedDto userSeedDto : seedUsersArray) {
                if (!validationUtil.isValid(userSeedDto)) {
                    this.validationUtil.getViolations(userSeedDto)
                            .forEach(v -> System.out.println(v.getMessage()));
                    continue;
                }
                User user = this.modelMapper.map(userSeedDto, User.class);
//                user.setFriends(setFriends());
                this.userRepository.saveAndFlush(user);
            }

        }
    }

    @Override
    public List<UserSoldProductsDto> getAllUsersWithSoldItems() {
        return this.userRepository.findAll()
                .stream().filter(u ->
                        (u.getProductsSold().stream().anyMatch(p -> p.getBuyer() != null)))
                .map(u -> {
                    UserSoldProductsDto userDto = this.modelMapper.map(u, UserSoldProductsDto.class);
                    List<ProductSoldDto> soldProductsDto = u.getProductsSold()
                            .stream()
                            .filter(p -> p.getBuyer() != null)
                            .map(p -> this.modelMapper.map(p, ProductSoldDto.class))
                            .collect(Collectors.toList());
                    userDto.setSoldProductsList(soldProductsDto);
                    return userDto;
                })
                //Sorting is not working as there are users with firstName = null and comparator will return NullPointerException!
                //.sorted(Comparator.comparing(UserSoldProductsDto::getLastName).thenComparing(UserSoldProductsDto::getFirstName))
                .collect(Collectors.toList());
    }

    @Override
    public void printAllUsersAndSoldItems() {
        String json = this.gson.toJson(this.getAllUsersWithSoldItems());
        System.out.println(json);
    }

    @Override
    public UserAndProductDto getALlUsersWithSoldProducts() {
        UserAndProductDto userAndProductDto = new UserAndProductDto();
        List<User> users = this.userRepository.getAllUsersWithSoldProducts();
        List<UserSoldDto> userSoldDtos = this.userRepository.getAllUsersWithSoldProducts()
                .stream()
                .filter(u -> !u.getProductsSold().isEmpty())
                .map(u -> {
                    UserSoldDto userSoldDto = this.modelMapper.map(u, UserSoldDto.class);
                    ProductSoldByUserDto productSoldByUserDto = new ProductSoldByUserDto();

                    List<ProductInfoDto> productInfoDtos = u.getProductsSold()
                            .stream()
                            .map(p -> this.modelMapper.map(p, ProductInfoDto.class))
                            .collect(Collectors.toList());
                    productSoldByUserDto.setProducts(productInfoDtos);
                    productSoldByUserDto.setCount(productInfoDtos.size());

                    userSoldDto.setSoldProducts(productSoldByUserDto);
                    return userSoldDto;
                })
                .sorted((a, b) -> {
                    int countA = a.getSoldProducts().getCount();
                    int countB = b.getSoldProducts().getCount();
                    return Integer.compare(countB,countA);
                })
                .collect(Collectors.toList());

        userAndProductDto.setUsers(userSoldDtos);
        userAndProductDto.setUserCount(userSoldDtos.size());
        return userAndProductDto;
    }

    @Override
    public void printGetUserAndProductDto() {
        String json = this.gson.toJson(this.getALlUsersWithSoldProducts());
        System.out.println(json);
    }

    private Set<User> setFriends() { //Method is now working as at the beginning of adding users, there are no users to choose friends
        Set<User> friends = new HashSet<>();
        int setSize = ThreadLocalRandom.current().nextInt(1, 5);
        for (int i = 0; i < setSize; i++) {
            long randomId = ThreadLocalRandom.current().nextLong(1, this.userRepository.count() + 1);
            User randomFriend = this.userRepository.findById(randomId).get();
            friends.add(randomFriend);
        }
        return friends;
    }
}

