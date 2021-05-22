package com.games.directory.controller;

import com.games.directory.controller.dto.GameDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
public class GameController {
    ArrayList<GameDto> games;
    public GameController(){
        games = new ArrayList<>();
        games.add(new GameDto(0,"Halo","Xbox","Action","https://www.enter.co/wp-content/uploads/2019/06/Halo-1024x768.jpg"));
        games.add(new GameDto(1,"God of war","PlayStation","Action","https://elcanciller.com/wp-content/uploads/2019/09/1524590603-gow-og-image.jpg"));
        games.add(new GameDto(2,"Assassins Creed","PC","Action","https://images3.alphacoders.com/823/thumb-1920-82365.jpg"));
        games.add(new GameDto(3,"Albion online es un mmorpg no lineal","PC","Aventure","https://imagekit.androidphoria.com/wp-content/uploads/Descargar-el-APK-de-Albion-Online-Android.jpg"));
        games.add(new GameDto(4,"Call of Duty Mobile","Mobile","Action","https://cdn1.dotesports.com/wp-content/uploads/sites/4/2021/02/10151019/GarenaWorld.png"));
    }

    @GetMapping("/games")
    public ResponseEntity<Collection<GameDto>> listGames(){
        return ResponseEntity.status(HttpStatus.OK).body(games);
    }

    @GetMapping("/games/{name}")
    public ResponseEntity<?> getGame(@PathVariable("name") String name){
        return ResponseEntity.status(HttpStatus.OK).body(games.get(Integer.parseInt(name)));
    }

    @DeleteMapping("/games/{name}")
    public ResponseEntity<?> deleteGame(@PathVariable("name") String name){
        return ResponseEntity.status(HttpStatus.OK).body(games.remove(Integer.parseInt(name)));
    }

    @PutMapping("/games/{name}")
    public ResponseEntity<Collection<GameDto>> updateGame(){
        return ResponseEntity.status(HttpStatus.OK).body(games);
    }

    @PostMapping("/games")
    public ResponseEntity<Collection<GameDto>> createGame(){
        return ResponseEntity.status(HttpStatus.OK).body(games);
    }
}
