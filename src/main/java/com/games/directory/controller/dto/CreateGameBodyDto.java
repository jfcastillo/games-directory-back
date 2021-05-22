package com.games.directory.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateGameBodyDto {
    private String name;
    private String console;
    private String genre;
    private String img;

    public CreateGameBodyDto(String name, String console, String genre, String img) {
        this.name = name;
        this.console = console;
        this.genre = genre;
        this.img = img;
    }
}
