package com.cdpr.gamestore_test.controller;

import com.cdpr.gamestore_test.mapper.GameMapper;
import com.cdpr.gamestore_test.model.dto.GameDto;
import com.cdpr.gamestore_test.service.GameService;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST controller for game.
 */
@RestController
@RequestMapping(value = "/games")
@RequiredArgsConstructor
@Slf4j
public class GameController {
    private final GameService gameService;
    private final GameMapper gameMapper;

    /**
     * Retrieves the list of all games in db.
     *
     * @return the list of all games.
     */
    @GetMapping
    public List<GameDto> getAllGames() {
        return gameService.getAllGames();
    }

    /**
     * Retrieves a game with the given name.
     *
     * @param name the name of the game to retrieve
     * @return the {@link GameDto} of the game with given name
     */
    @GetMapping("/search")
    public GameDto searchGame(@RequestParam @Nonnull final String name) {
        return gameService.searchGame(name);
    }

    /**
     * Adds a new game.
     *
     * @param gameDto the {@link GameDto} object based on which game is added
     * @return the {@link GameDto} object of the added game
     */
    @PostMapping("/add")
    public GameDto addGame(@Valid @RequestBody final GameDto gameDto) {
        final var game = gameMapper.gameDtoToGame(gameDto);
        game.setCreated(LocalDateTime.now());
        game.setUpdated(LocalDateTime.now());
        final var addedGame = gameService.addGame(game);

        log.info("New game was created with name: {}", gameDto.getName());
        return gameMapper.gameToGameDto(addedGame);
    }

    /**
     * Updates an existing game.
     *
     * @param gameDto the {@link GameDto} object based on which game is updated
     * @return the {@link GameDto} object of the updated game
     */
    @PutMapping("/update")
    public GameDto updateGame(@Valid @RequestBody final GameDto gameDto) {
        log.info("The game with name {} was updated", gameDto.getName());
        return gameService.updateGame(gameDto);
    }

    /**
     * Deletes a game with given name.
     *
     * @param name the name of the game to delete
     */
    @DeleteMapping("/delete")
    public void deleteGameByName(@RequestParam @Nonnull final String name) {
        log.info("The game with name {} was deleted", name);
        gameService.deleteGameByName(name);
    }
}
