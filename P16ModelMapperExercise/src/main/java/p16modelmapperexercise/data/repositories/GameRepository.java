package p16modelmapperexercise.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import p16modelmapperexercise.data.entities.Game;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> getByTitle(String gameTitle);


}
