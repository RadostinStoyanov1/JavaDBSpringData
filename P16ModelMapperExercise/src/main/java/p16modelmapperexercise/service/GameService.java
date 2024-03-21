package p16modelmapperexercise.service;

import p16modelmapperexercise.service.DTOs.GameAddDTO;
import p16modelmapperexercise.service.DTOs.GamesAllDTO;

import java.util.Map;
import java.util.Set;

public interface GameService {
    String addGame(GameAddDTO gameAddDTO);

    String editGame(long id, Map<String, String> editPropertiesMap);

    String deleteGame(long id);

    Set<GamesAllDTO> getAllGames();

    String allGamesReadyForPrint();

    String printGameDetails(String gameTitle);
}
