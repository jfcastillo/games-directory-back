package com.games.directory.repository;

import com.games.directory.model.GameDao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<GameDao, Long> {
}
