package com.cdpr.gamestore_test.persistence.repository;

import com.cdpr.gamestore_test.persistence.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Basic repository for game.
 */
public interface GameRepository extends JpaRepository<Game, Long> {
    Game findGameByName(String name);

    void deleteGameByName(String name);
}
