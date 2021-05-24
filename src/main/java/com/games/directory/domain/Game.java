package com.games.directory.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Game {
    private long id;
    private String name;
    private String console;
    private String genre;
    private String img;

    public Game(long id, String name, String console, String genre, String img) {
        this.id = id;
        this.name = name;
        this.console = console;
        this.genre = genre;
        this.img = img;
    }

    public Game(String name, String console, String genre, String img) {
        this.name = name;
        this.console = console;
        this.genre = genre;
        this.img = img;
    }
}
