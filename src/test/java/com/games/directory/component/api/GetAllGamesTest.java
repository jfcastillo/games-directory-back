package com.games.directory.component.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.games.directory.controller.dto.GameDto;
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

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class GetAllGamesTest {
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
    public void getGamesWithSuccessStatusCodeAndContentType() {
        var response = mockMvc.perform(get("/games")).andReturn().getResponse();

        assertThat(response.getStatus(), equalTo(HttpStatus.OK.value()));
        assertThat(response.getContentType(), equalTo(MediaType.APPLICATION_JSON.toString()));
    }

    @Test
    @SneakyThrows
    public void getGamesAndVerifyContent() {
        var response = mockMvc.perform(get("/games")).andReturn().getResponse();
        List<GameDto> games = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<List<GameDto>>(){});

        GameDto game = games.get(0);

        assertEquals("Call of Duty: War Zone",game.getName());
        assertEquals("PC",game.getConsole());
        assertEquals("Action",game.getGenre());
        assertEquals("www.img-CODWZ.com/CODWZ.jpg",game.getImg());
    }
}
