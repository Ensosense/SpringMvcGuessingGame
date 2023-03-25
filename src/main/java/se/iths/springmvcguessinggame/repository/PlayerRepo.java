package se.iths.springmvcguessinggame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.springmvcguessinggame.entity.Player;

import java.util.List;

public interface PlayerRepo extends JpaRepository<Player, Long> {

    List<Player> findByUsername(String username);

    boolean existsByUsername(String username);
}
