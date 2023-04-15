package com.cdpr.gamestore_test.service.impl;

import com.cdpr.gamestore_test.mapper.GameMapper;
import com.cdpr.gamestore_test.model.dto.GameDto;
import com.cdpr.gamestore_test.persistence.entity.Game;
import com.cdpr.gamestore_test.persistence.repository.GameRepository;
import com.cdpr.gamestore_test.service.GameService;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link GameService} interface.
 */
@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    @Override
    @Nullable
    public List<GameDto> getAllGames() {
        List<GameDto> gameDtoList = new ArrayList<>();
        final var games = gameRepository.findAll();
        for(Game game:games){
            gameDtoList.add(gameMapper.gameToGameDto(game));
        }
        return gameDtoList;
    }

    @Override
    @Nullable
    public GameDto searchGame(String name) {
        return gameMapper.gameToGameDto(gameRepository.findGameByName(name));
    }

    @Override
    public Game addGame(@Nonnull final Game game) {
        if (gameRepository.findGameByName(game.getName()) != null) {
            throw new IllegalArgumentException("A game with the same name already exists.");
        }
        return gameRepository.save(game);
    }

    @Override
    @Nullable
    public Game getGameByName(@Nonnull final String name) {
        return gameRepository.findGameByName(name);
    }

    @Override
    public GameDto updateGame(@Nonnull final GameDto gameDto) {
    final var gameName = gameDto.getName();
    Game game = getGameByName(gameName);
    game = Game.builder()
            .id(game.getId())
            .name(gameDto.getName())
            .price(gameDto.getPrice())
            .description(gameDto.getDescription())
            .releaseDate(gameDto.getReleaseDate())
            .company(gameDto.getCompany())
            .created(game.getCreated())
            .updated(LocalDateTime.now())
            .build();
    return gameMapper.gameToGameDto(gameRepository.save(game));
    }

    @Override
    @Transactional
    public void deleteGameByName(@Nonnull final String name) {
        if (getGameByName(name) == null) {
            throw new RuntimeException("Game with current name not found in db");
        }
        gameRepository.deleteGameByName(name);
    }
}
