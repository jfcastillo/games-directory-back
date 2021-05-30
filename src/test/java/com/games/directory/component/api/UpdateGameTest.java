package com.games.directory.component.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.games.directory.controller.dto.GameDto;
import com.games.directory.domain.Game;
import com.games.directory.model.GameDao;
import com.games.directory.repository.GameRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class UpdateGameTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GameRepository gameRepository;

    @BeforeEach
    public void setUp() {
        GameDao game = new GameDao("Call of Duty: War Zone", "PC", "Action", "www.img-CODWZ.com/CODWZ.jpg");
        gameRepository.save(game);
    }

    @Test
    @SneakyThrows
    public void updateGameWithSuccessStatusCodeAndContentType() {
        GameDto game = new GameDto(1,"Clash Of Clans", "Mobile", "Strategy", "www.img-coc.com/coc.jpg");

        String gameAsString = new ObjectMapper().writeValueAsString(game);

        var response = mockMvc.perform(put("/games").contentType(MediaType.APPLICATION_JSON)
                .content(gameAsString)).andReturn().getResponse();

        assertThat(response.getStatus(), equalTo(HttpStatus.OK.value()));
        assertThat(response.getContentType(), equalTo(MediaType.APPLICATION_JSON.toString()));

        GameDto gameResponse = new ObjectMapper().readValue(response.getContentAsString(), GameDto.class);

        assertEquals("Clash Of Clans",gameResponse.getName());
        assertEquals("Mobile",gameResponse.getConsole());
        assertEquals("Strategy",gameResponse.getGenre());
        assertEquals("www.img-coc.com/coc.jpg",gameResponse.getImg());


        GameDao gameDao = gameRepository.findById(gameResponse.getId()).get();

        assertEquals("Clash Of Clans",gameDao.getName());
        assertEquals("Mobile",gameDao.getConsole());
        assertEquals("Strategy",gameDao.getGenre());
        assertEquals("www.img-coc.com/coc.jpg",gameDao.getImg());
    }

    @Test
    @SneakyThrows
    public void updateGameWithErrorStatusCodeAndContent() {
        Game emptyGame = new Game();

        String emptyGameAsString = new ObjectMapper().writeValueAsString(emptyGame);

        var responseEmptyGame = mockMvc.perform(put("/games").contentType(MediaType.APPLICATION_JSON)
                .content(emptyGameAsString)).andReturn().getResponse();

        assertThat(responseEmptyGame.getStatus(), equalTo(HttpStatus.BAD_REQUEST.value()));
        assertEquals("The game data is incomplete",responseEmptyGame.getContentAsString());


        GameDto nonexistentGame = new GameDto(100,"Clash Of Clans", "Mobile", "Strategy", "www.img-coc.com/coc.jpg");
        String nonexistentGameAsString = new ObjectMapper().writeValueAsString(nonexistentGame);

        var responseExistingGame = mockMvc.perform(put("/games").contentType(MediaType.APPLICATION_JSON)
                .content(nonexistentGameAsString)).andReturn().getResponse();

        assertThat(responseExistingGame.getStatus(), equalTo(HttpStatus.NOT_FOUND.value()));
        assertEquals("The game to update doesn't exist",responseExistingGame.getContentAsString());

    }
}
