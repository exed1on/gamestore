package com.cdpr.gamestore_test.mapper;

import com.cdpr.gamestore_test.model.dto.GameDto;
import com.cdpr.gamestore_test.persistence.entity.Game;
import org.springframework.stereotype.Component;

/**
 * This class defines and implements methods for mapping between the {@link Game} and {@link GameDto} classes.
*/
@Component
public class GameMapper {

    /**
     * Maps a {@link Game} object to a {@link GameDto} object.
     *
     * @param game The {@link Game} object to be mapped.
     * @return The resulting {@link GameDto} object.
     */
    public GameDto gameToGameDto(Game game) {
        return GameDto.builder()
                .name(game.getName())
                .price(game.getPrice())
                .description(game.getDescription())
                .releaseDate(game.getReleaseDate())
                .company(game.getCompany())
                .build();
    }

    /**
     * Maps a {@link GameDto} object to a {@link Game} object.
     *
     * @param gameDto The {@link GameDto} object to be mapped.
     * @return The resulting {@link Game} object.
     */
    public Game gameDtoToGame(GameDto gameDto) {
        return Game.builder()
                .name(gameDto.getName())
                .price(gameDto.getPrice())
                .description(gameDto.getDescription())
                .releaseDate(gameDto.getReleaseDate())
                .company(gameDto.getCompany())
                .build();
    }
}