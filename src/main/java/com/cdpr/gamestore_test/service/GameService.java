package com.cdpr.gamestore_test.service;

import com.cdpr.gamestore_test.model.dto.GameDto;
import com.cdpr.gamestore_test.persistence.entity.Game;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * Service interface for managing games.
 */
public interface GameService {
    /**
     * Gets all games.
     *
     * @return the list of games
     */
    List<GameDto> getAllGames();

    /**
     * Searches for a game.
     *
     * @param name the name of a game
     * @return the game
     */
    GameDto searchGame(@Nonnull final String name);

    /**
     * Adds a new game.
     *
     * @param game the game to add
     * @return the created game
     * @see Game
     */
    Game addGame(@Nonnull final Game game);

    /**
     * Gets a game by name.
     *
     * @param name the name of the game to get
     * @return the game, or null if not found
     * @see Game
     */
    Game getGameByName(@Nonnull final String name);

    GameDto updateGame(@Nonnull final GameDto gameDto);

    /**
     * Deletes a game by name.
     *
     * @param name the name of the game to delete
     */
    void deleteGameByName(@Nonnull final String name);
}
