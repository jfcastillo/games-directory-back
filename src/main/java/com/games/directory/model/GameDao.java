package com.games.directory.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ANIMAL")
public class GameDao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GAME_GENERATOR")
    @SequenceGenerator(name = "GAME_GENERATOR", sequenceName = "GAME_SEQ", allocationSize = 1)
    private long id;
    private String name;
    private String console;
    private String genre;
    private String img;

    public GameDao(String name, String console, String genre, String img) {
        this.name = name;
        this.console = console;
        this.genre = genre;
        this.img = img;
    }
}
