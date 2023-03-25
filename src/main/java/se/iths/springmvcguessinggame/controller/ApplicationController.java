package se.iths.springmvcguessinggame.controller;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.iths.springmvcguessinggame.entity.Player;
import se.iths.springmvcguessinggame.entity.Result;
import se.iths.springmvcguessinggame.service.GameService;
import se.iths.springmvcguessinggame.service.PlayerService;

import java.sql.SQLException;
import java.util.List;

@Controller
public class ApplicationController {

    @Autowired
    public PlayerService playerService;

    @Autowired
    public GameService gameService;


    @GetMapping("/user")
    String getUser(Model m) {
        m.addAttribute("player", new Player());
        return "user";
    }

    @PostMapping("/user")
    String userForm(@ModelAttribute Player player, Model m, HttpSession session) {
        session.setAttribute("player", player);
        return "redirect:/game";
    }

    @GetMapping("/game")
    String getGame(Model model, HttpSession session) {
        Player player = (Player) session.getAttribute("player");
        if (player == null) {
            // If there's no player in the session, redirect back to the user page
            return "redirect:/user";
        } else {
            model.addAttribute("player", player);
            model.addAttribute("welcome", playerService.welcomeAndSavePlayerIfNew(player));
            return "game";
        }
    }

    @PostMapping("/game")
    String gameForm(Model model, @RequestParam int tal, HttpSession session) throws SQLException {
        Player player = (Player) session.getAttribute("player");
        model.addAttribute("game", gameService.makeGuess( player.getUsername(), tal));
        model.addAttribute("guesses", gameService.getGuesses());
        System.out.println("Player username: " + player.getUsername());
        return "game";
    }


    @GetMapping("/top10")
    public String getTop10(Model model, @ModelAttribute Result result) {
        List<Player> topTen = playerService.getTop10Players();
       model.addAttribute("score", result.getScore());

        model.addAttribute("top10", topTen);
        return "topten";
    }
}
