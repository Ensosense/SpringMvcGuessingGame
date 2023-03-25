package se.iths.springmvcguessinggame.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.iths.springmvcguessinggame.entity.Player;
import se.iths.springmvcguessinggame.entity.Result;
import se.iths.springmvcguessinggame.repository.PlayerRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class GameService {

    @Autowired
    PlayerService playerService;

    @Autowired
    PlayerRepo playerRepo;

    Player player;
    Result result;

    Random random = new Random();
    int secret;
    List<Integer> guesses;



    public GameService() {
        init();
    }

    public void init(){
        result = new Result();
        guesses = new ArrayList<>();
        secret = random.nextInt(1,11);
    }

    public List<Integer> getGuesses() {
        return guesses;
    }

    public void setGuesses(List<Integer> guesses) {
        this.guesses = guesses;
    }

    public String makeGuess(String username, int guess){
        int score = 10 - result.getAttempts();
        player = playerService.getPlayerByUsername(username).get(0);

        if(guess < secret){
            guesses.add(guess);
            result.iterateAttempt();
            return guess + " is too low";
        } else if (guess > secret) {
            guesses.add(guess);
            result.iterateAttempt();
            return guess + " is too high";
        } else{
            Result newResult = new Result();
            newResult.setAttempts(result.getAttempts());
            newResult.setScore(score);
            player.getResults().add(newResult);
            playerService.update(player);
            guesses.clear();
            result.setAttempts(0);
            init();
            return "You win, your score is: " + score;

        }

    }

}
