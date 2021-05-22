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

    public CreateGameBodyDto(String name, String console, String genre) {
        this.name = name;
        this.console = console;
        this.genre = genre;
    }
}
