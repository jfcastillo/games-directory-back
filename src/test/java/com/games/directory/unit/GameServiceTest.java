package com.games.directory.unit;

import com.games.directory.domain.Game;
import com.games.directory.model.GameDao;
import com.games.directory.repository.GameRepository;
import com.games.directory.service.interfaces.GameService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GameServiceTest {
    @Autowired
    private GameService gameService;

    @Autowired
    private GameRepository gameRepository;


    @BeforeEach
    void setUp(){
        gameRepository.save(new GameDao("Halo", "Xbox", "Action", "www.img-halo.com/halo.jpg"));
    }

    @Test
    void getAllGamesTest(){
        List<Game> games = gameService.getAll();
        assertTrue(games.size() == 1);
    }

    @Test
    void getGameTest(){
        Game game = gameService.get(1);
        assertEquals(game.getName(),"Halo");
    }
}
