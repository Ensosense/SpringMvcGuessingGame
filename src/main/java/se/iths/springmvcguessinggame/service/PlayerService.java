package se.iths.springmvcguessinggame.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.iths.springmvcguessinggame.entity.Player;
import se.iths.springmvcguessinggame.entity.Result;
import se.iths.springmvcguessinggame.repository.PlayerRepo;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class PlayerService {


    @Autowired
    PlayerRepo repo;

    public PlayerService() {
    }

    public List<Player> getPlayerByUsername(String username) {
        if (repo.existsByUsername(username)) {
            return repo.findByUsername(username);
        }
        return null;
    }

    public String welcomeAndSavePlayerIfNew(Player player){
        if(repo.existsByUsername(player.getUsername())){
            update(player);
            return "Welcome back, " + player.getUsername();
        } else {
            repo.save(player);
            return "Welcome, " + player.getUsername();
        }
    }

  public void update(Player player) {
      if (repo.existsByUsername(player.getUsername())) {
          Player existingPlayer = repo.findByUsername(player.getUsername()).get(0);
          existingPlayer.setResults(player.getResults());

          // update the score for each result in the player's list
          for (Result result : existingPlayer.getResults()) {
              result.setScore(10 - result.getAttempts());
          }

          repo.save(existingPlayer);
      }

  }

    public List<Player> getTop10Players() {
        List<Player> allPlayers = repo.findAll();
        Collections.sort(allPlayers, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                int score1 = calculateTotalScore(p1);
                int score2 = calculateTotalScore(p2);
                return Integer.compare(score2, score1);
            }
        });
        return allPlayers.subList(0, Math.min(allPlayers.size(), 10));
    }

    private int calculateTotalScore(Player player) {
        int totalScore = 0;
        for (Result result : player.getResults()) {
            totalScore += result.getScore();
        }
        return totalScore;
    }





}
