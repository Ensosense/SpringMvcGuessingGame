package se.iths.springmvcguessinggame.entity;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Player {
    @Id
    @GeneratedValue
    private Long id;

    private String username;


    @OneToMany(cascade = CascadeType.ALL)
    private List<Result> results;

    public Player() {
        results = new ArrayList<>();
    }

    public Player(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
