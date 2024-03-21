package p16modelmapperexercise.service.impl;

import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import p16modelmapperexercise.data.entities.Game;
import p16modelmapperexercise.data.repositories.GameRepository;
import p16modelmapperexercise.service.DTOs.GameAddDTO;
import p16modelmapperexercise.service.DTOs.GameDetailsDTO;
import p16modelmapperexercise.service.DTOs.GamesAllDTO;
import p16modelmapperexercise.service.GameService;
import p16modelmapperexercise.service.UserService;
import p16modelmapperexercise.util.ValidatorService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final ValidatorService validatorService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public GameServiceImpl(GameRepository gameRepository, ValidatorService validatorService, UserService userService, ModelMapper modelMapper) {
        this.gameRepository = gameRepository;
        this.validatorService = validatorService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public String addGame(GameAddDTO gameAddDTO) {
        if (this.userService.getLoggedIn() != null && this.userService.getLoggedIn().isAdmin()) {
            if (!this.validatorService.isValid(gameAddDTO)) {
                return this.validatorService.validate(gameAddDTO)
                        .stream().map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining("\n"));
            }

            Game newGame = this.modelMapper.map(gameAddDTO, Game.class);
            this.gameRepository.saveAndFlush(newGame);
            return String.format("Added %s", newGame.getTitle());
        }
        return "Logged in user is not admin";
    }

    @Override
    public String editGame(long id, Map<String, String> editPropertiesMap) {
        if (this.userService.getLoggedIn() != null && this.userService.getLoggedIn().isAdmin()) {
            Optional<Game> optionalGame = this.gameRepository.findById(id);
            if (optionalGame.isEmpty()) {
                return "There is no game with such id";
            }

            Game game = optionalGame.get();
            String originalGameName = game.getTitle();

            for (Map.Entry<String, String> entry : editPropertiesMap.entrySet()) {
                switch (entry.getKey()) {
                    case "title":
                        game.setTitle(entry.getValue());
                        break;
                    case "price":
                        game.setPrice(Double.parseDouble(entry.getValue()));
                        break;
                    case "size":
                        game.setSize(Double.parseDouble(entry.getValue()));
                        break;
                    case "trailer":
                        game.setTrailer(entry.getValue());
                        break;
                    case "thumbnail":
                        game.setThumbnail(entry.getValue());
                        break;
                    case "description":
                        game.setDescription(entry.getValue());
                        break;
                    case "releaseDate":
                        game.setReleaseDate(LocalDate.parse(entry.getValue(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                        break;
                }
            }
            this.gameRepository.saveAndFlush(game);
            return String.format("Edited %s", originalGameName);
        }
        return "Logged in user is not admin";
    }

    @Override
    public String deleteGame(long id) {
        if (this.userService.getLoggedIn() != null && this.userService.getLoggedIn().isAdmin()) {
            Optional<Game> optionalGame = this.gameRepository.findById(id);
            if (optionalGame.isEmpty()) {
                return "There is no game with such id";
            }
            String output = String.format("Deleted %s", optionalGame.get().getTitle());
            this.gameRepository.delete(optionalGame.get());
            return output;
        }
        return "Logged in user is not admin";
    }

    @Override
    public Set<GamesAllDTO> getAllGames() {
        return this.gameRepository.findAll()
                .stream()
                .map(g -> this.modelMapper.map(g, GamesAllDTO.class))
                .collect(Collectors.toSet());
    }

    @Override
    public String allGamesReadyForPrint() {
        return this.getAllGames().stream()
                .map(GamesAllDTO::toString)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String printGameDetails(String gameTitle) {
        Optional<Game> foundGame = this.gameRepository.getByTitle(gameTitle);
        if (foundGame.isEmpty()) {
            return "There is no game with such title";
        }

        GameDetailsDTO gameDetailsDTO = this.modelMapper.map(foundGame.get(), GameDetailsDTO.class);
        return gameDetailsDTO.toString();
    }
}
