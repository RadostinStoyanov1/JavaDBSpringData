package p16modelmapperexercise.service.impl;

import org.springframework.stereotype.Service;
import p16modelmapperexercise.data.entities.Game;
import p16modelmapperexercise.data.entities.Order;
import p16modelmapperexercise.data.entities.User;
import p16modelmapperexercise.data.repositories.GameRepository;
import p16modelmapperexercise.data.repositories.OrderRepository;
import p16modelmapperexercise.data.repositories.UserRepository;
import p16modelmapperexercise.service.DTOs.CartItemDTO;
import p16modelmapperexercise.service.ShoppingCartService;
import p16modelmapperexercise.service.UserService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final OrderRepository orderRepository;
    private Set<Game> gamesInCart = new HashSet<>();

    public ShoppingCartServiceImpl(UserService userService, UserRepository userRepository, GameRepository gameRepository, OrderRepository orderRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public String addItem(CartItemDTO item) {
        if (this.userService.getLoggedIn() == null) {
            return "No logged in user";
        }
        Optional<Game> optionalGame = this.gameRepository.getByTitle(item.getTitle());
        if (optionalGame.isEmpty()) {
            return "Such game does not exist";
        }

        this.gamesInCart.add(optionalGame.get());
        return String.format("%s added to cart", item.getTitle());
    }

    @Override
    public String deleteItem(CartItemDTO item) {
        if (this.userService.getLoggedIn() == null) {
            return "No logged in user";
        }
        Optional<Game> optionalGame = this.gameRepository.getByTitle(item.getTitle());
        if (optionalGame.isEmpty()) {
            return "Such game does not exist";
        }
        this.gamesInCart.remove(optionalGame.get());
        return String.format("%s removed from cart", item.getTitle());
    }

    @Override
    public String buyItem() {
        User loggeInUser = this.userService.getLoggedIn();
        if (loggeInUser == null) {
            return "No user is logged in";
        }
        loggeInUser.getGames().addAll(this.gamesInCart);
        this.userRepository.saveAndFlush(loggeInUser);
        this.orderRepository.saveAndFlush(new Order(LocalDateTime.now(), loggeInUser, this.gamesInCart));
        String boughtGamesFromCart = this.gamesInCart.stream().map(Game::getTitle).collect(Collectors.joining("\n -"));
        this.gamesInCart = new HashSet<>();
        return "Successfully bought games:\n" + boughtGamesFromCart;
    }
}
