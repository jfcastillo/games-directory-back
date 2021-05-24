package com.games.directory.controller;

import com.games.directory.controller.dto.CreateGameBodyDto;
import com.games.directory.controller.dto.GameDto;
import com.games.directory.controller.dto.UpdateGameBodyDto;
import com.games.directory.domain.Game;
import com.games.directory.exceptions.GameAlreadyExistException;
import com.games.directory.exceptions.GameInformationIncompleteException;
import com.games.directory.service.interfaces.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/games")
    public ResponseEntity<Collection<GameDto>> listGames(){
        List<Game> games = gameService.getAll();
        List<GameDto> gamesDto = games.stream().map(game -> map(game)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(gamesDto);
    }

    @GetMapping("/games/{id}")
    public ResponseEntity<?> getGame(@PathVariable("id") long id){
        try{
            Game game = gameService.get(id);
            return ResponseEntity.status(HttpStatus.OK).body(map(game));
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The game not been found");
        }

    }

    @DeleteMapping("/games/{id}")
    public ResponseEntity<?> deleteGame(@PathVariable("id") long id){
        try{
            gameService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("ok");
        }catch (EmptyResultDataAccessException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The game to delete doesn't exist");
        }
    }

    @PutMapping("/games")
    public ResponseEntity<?> updateGame(@RequestBody UpdateGameBodyDto gameDto){
        try{
            Game game = gameService.update(map(gameDto));
            return ResponseEntity.status(HttpStatus.OK).body(map(game));
        }catch (GameInformationIncompleteException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The game data is incomplete");
        }catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The game to update doesn't exist");
        }
    }

    @PostMapping("/games")
    public ResponseEntity<?> createGame(@RequestBody CreateGameBodyDto gameDto){
        try{
            Game game = gameService.create(map(gameDto));
            return ResponseEntity.status(HttpStatus.OK).body(map(game));
        }catch (GameInformationIncompleteException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The game data is incomplete");
        }catch (GameAlreadyExistException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The game to create already exist");
        }
    }

    private GameDto map(Game game){
        return new GameDto(
                game.getId(),
                game.getName(),
                game.getConsole(),
                game.getGenre(),
                game.getImg()
        );
    }

    private Game map(GameDto gameDto){
        return new Game(
                gameDto.getId(),
                gameDto.getName(),
                gameDto.getConsole(),
                gameDto.getGenre(),
                gameDto.getImg()
        );
    }

    private Game map(UpdateGameBodyDto gameDto){
        return new Game(
                gameDto.getId(),
                gameDto.getName(),
                gameDto.getConsole(),
                gameDto.getGenre(),
                gameDto.getImg()
        );
    }

    private Game map(CreateGameBodyDto gameDto){
        return new Game(
                gameDto.getName(),
                gameDto.getConsole(),
                gameDto.getGenre(),
                gameDto.getImg()
        );
    }
}
