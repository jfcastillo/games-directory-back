package com.games.directory.unit;

import com.games.directory.domain.Game;
import com.games.directory.exceptions.GameAlreadyExistException;
import com.games.directory.exceptions.GameInformationIncompleteException;
import com.games.directory.model.GameDao;
import com.games.directory.repository.GameRepository;
import com.games.directory.service.interfaces.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.NoSuchElementException;

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
        assertTrue(games.size() > 0);
    }

    @Test
    void getGameTest(){
        try{
            Game game = gameService.get(100);
        }catch (NoSuchElementException e){
            assertTrue(true);
        }
        Game game = gameService.get(1);
        assertEquals("Halo", game.getName());
    }

    @Test
    void updateGameTest(){
        Game game = gameService.get(1);
        game.setConsole("Xbox One");

        try{
            gameService.update(new Game());
        }catch (GameInformationIncompleteException e){
            assertTrue(true);
        }

        try{
            gameService.update(new Game(100,"Halo", "Xbox", "Action", "www.img-halo.com/halo.jpg"));
        }catch (NoSuchElementException e){
            assertTrue(true);
        }


        Game gameUpdated = gameService.update(game);
        assertEquals("Xbox One", gameUpdated.getConsole());
    }

    @Test
    void deleteGameTest(){
        try{
            gameService.delete(100);
        }catch (EmptyResultDataAccessException e){
            assertTrue(true);
        }

        gameService.delete(1);

        try{
            gameService.get(1);
        }catch (NoSuchElementException e){
            assertTrue(true);
        }
    }

    @Test
    void createGameTest(){
        try{
            gameRepository.save(new GameDao());
        }catch (GameInformationIncompleteException e){
            assertTrue(true);
        }

        gameRepository.save(new GameDao("God of war", "PlayStation", "Action", "www.img-gow.com/gow.jpg"));

        try{
            gameRepository.save(new GameDao("God of war", "PlayStation", "Action", "www.img-gow.com/gow.jpg"));
        }catch (GameAlreadyExistException e){
            assertTrue(true);
        }
    }
}
