package com.games.directory.component.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.games.directory.controller.dto.GameDto;
import com.games.directory.domain.Game;
import com.games.directory.model.GameDao;
import com.games.directory.repository.GameRepository;
import lombok.SneakyThrows;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class CreateGameTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GameRepository gameRepository;

    public void setUp() {
        GameDao game = new GameDao("FIFA 21", "Xbox", "Sport", "www.img-fifa.com/fifa.jpg");
        gameRepository.save(game);
    }

    @Test
    @SneakyThrows
    public void createGameWithSuccessStatusCodeAndContentType() {
        Game game = new Game("Call of Duty: War Zone", "PC", "Action", "www.img-CODWZ.com/CODWZ.jpg");

        String gameAsString = new ObjectMapper().writeValueAsString(game);

        var response = mockMvc.perform(post("/games").contentType(MediaType.APPLICATION_JSON)
                .content(gameAsString)).andReturn().getResponse();

        assertThat(response.getStatus(), equalTo(HttpStatus.OK.value()));
        assertThat(response.getContentType(), equalTo(MediaType.APPLICATION_JSON.toString()));

        GameDto gameResponse = new ObjectMapper().readValue(response.getContentAsString(), GameDto.class);

        assertEquals("Call of Duty: War Zone",gameResponse.getName());
        assertEquals("PC",gameResponse.getConsole());
        assertEquals("Action",gameResponse.getGenre());
        assertEquals("www.img-CODWZ.com/CODWZ.jpg",gameResponse.getImg());

        GameDao gameDao = gameRepository.findById(gameResponse.getId()).get();

        assertEquals("Call of Duty: War Zone",gameDao.getName());
        assertEquals("PC",gameDao.getConsole());
        assertEquals("Action",gameDao.getGenre());
        assertEquals("www.img-CODWZ.com/CODWZ.jpg",gameDao.getImg());

    }

    @Test
    @SneakyThrows
    public void createGameWithErrorStatusCodeAndContent() {
        Game emptyGame = new Game();

        String emptyGameAsString = new ObjectMapper().writeValueAsString(emptyGame);

        var responseEmptyGame = mockMvc.perform(post("/games").contentType(MediaType.APPLICATION_JSON)
                .content(emptyGameAsString)).andReturn().getResponse();

        assertThat(responseEmptyGame.getStatus(), equalTo(HttpStatus.BAD_REQUEST.value()));
        assertEquals("The game data is incomplete",responseEmptyGame.getContentAsString());

        setUp();

        Game existingGame = new Game("FIFA 21", "Xbox", "Sport", "www.img-fifa.com/fifa.jpg");
        String existingGameAsString = new ObjectMapper().writeValueAsString(existingGame);

        var responseExistingGame = mockMvc.perform(post("/games").contentType(MediaType.APPLICATION_JSON)
                .content(existingGameAsString)).andReturn().getResponse();

        assertThat(responseExistingGame.getStatus(), equalTo(HttpStatus.BAD_REQUEST.value()));
        assertEquals("The game to create already exist",responseExistingGame.getContentAsString());

    }
}
