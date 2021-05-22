package com.games.directory.controller;

import com.games.directory.controller.dto.CreateGameBodyDto;
import com.games.directory.controller.dto.GameDto;
import com.games.directory.controller.dto.UpdateGameBodyDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.HashMap;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
public class GameController {
    private HashMap<Long,GameDto> games;
    private long id;
    public GameController(){
        games = new HashMap<>();
        id = 5;
        games.put((long)0,new GameDto(0,"Halo","Xbox","Action","https://www.enter.co/wp-content/uploads/2019/06/Halo-1024x768.jpg"));
        games.put((long)1,new GameDto(1,"God of war","PlayStation","Action","https://elcanciller.com/wp-content/uploads/2019/09/1524590603-gow-og-image.jpg"));
        games.put((long)2,new GameDto(2,"Assassins Creed","PC","Action","https://images3.alphacoders.com/823/thumb-1920-82365.jpg"));
        games.put((long)3,new GameDto(3,"Albion online es un mmorpg no lineal","PC","Aventure","https://imagekit.androidphoria.com/wp-content/uploads/Descargar-el-APK-de-Albion-Online-Android.jpg"));
        games.put((long)4,new GameDto(4,"Call of Duty Mobile","Mobile","Action","https://cdn1.dotesports.com/wp-content/uploads/sites/4/2021/02/10151019/GarenaWorld.png"));
    }

    @GetMapping("/games")
    public ResponseEntity<Collection<GameDto>> listGames(){
        return ResponseEntity.status(HttpStatus.OK).body(games.values());
    }

    @GetMapping("/games/{id}")
    public ResponseEntity<?> getGame(@PathVariable("id") long id){
        return ResponseEntity.status(HttpStatus.OK).body(games.get(id));
    }

    @DeleteMapping("/games/{id}")
    public ResponseEntity<?> deleteGame(@PathVariable("id") long id){
        return ResponseEntity.status(HttpStatus.OK).body(games.remove(id));
    }

    @PutMapping("/games")
    public ResponseEntity<GameDto> updateGame(@RequestBody UpdateGameBodyDto gameDto){
        GameDto game = games.get(gameDto.getId());
        game.setName(gameDto.getName());
        game.setConsole(gameDto.getConsole());
        game.setGenre(gameDto.getGenre());
        game.setImg(gameDto.getImg());

        return ResponseEntity.status(HttpStatus.OK).body(games.get(game.getId()));
    }

    @PostMapping("/games")
    public ResponseEntity<GameDto> createGame(@RequestBody CreateGameBodyDto gameDto){
        GameDto game = new GameDto();
        game.setId(id);
        game.setName(gameDto.getName());
        game.setConsole(gameDto.getConsole());
        game.setGenre(gameDto.getGenre());
        game.setImg(gameDto.getImg());
        id++;
        return ResponseEntity.status(HttpStatus.OK).body(games.put(game.getId(),game));
    }
}
