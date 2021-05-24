package com.games.directory.service;

import com.games.directory.domain.Game;
import com.games.directory.model.GameDao;
import com.games.directory.repository.GameRepository;
import com.games.directory.service.interfaces.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class GameServiceImp implements GameService {
    @Autowired
    private GameRepository gameRepository;

    @Override
    public List<Game> getAll() {
        Iterable<GameDao> gamesDao = gameRepository.findAll();

        return StreamSupport
                .stream(gamesDao.spliterator(), false)
                .map(dao -> this.map(dao)).collect(Collectors.toList());
    }

    @Override
    public Game get(long id) {
        GameDao gameDao = gameRepository.findById(id).get();
        return map(gameDao);
    }

    @Override
    public Game create(Game game) {
        GameDao gameDao = map(game);
        return map(gameRepository.save(gameDao));
    }

    @Override
    public Game update(Game game) {
        GameDao gameDao = map(game);
        gameDao.setId(game.getId());

        return map(gameRepository.save(gameDao));
    }

    @Override
    public void delete(long id) {
        gameRepository.deleteById(id);
    }

    private Game map(GameDao gameDao) {

        return new Game(
                gameDao.getId(),
                gameDao.getName(),
                gameDao.getConsole(),
                gameDao.getGenre(),
                gameDao.getImg()
        );
    }

    private GameDao map(Game game) {
        return new GameDao(
                game.getName(),
                game.getConsole(),
                game.getGenre(),
                game.getImg()
        );
    }
}
