package com.games.directory.service.interfaces;

import com.games.directory.domain.Game;

import java.util.List;

public interface GameService {
    List<Game> getAll();
    Game get(long id);
    Game create(Game game);
    Game update(Game game);
    void delete(long id);
}

