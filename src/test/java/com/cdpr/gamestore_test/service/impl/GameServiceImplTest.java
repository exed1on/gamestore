package com.cdpr.gamestore_test.service.impl;

import com.cdpr.gamestore_test.mapper.GameMapper;
import com.cdpr.gamestore_test.model.dto.GameDto;
import com.cdpr.gamestore_test.persistence.entity.Game;
import com.cdpr.gamestore_test.persistence.repository.GameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {
    @Mock
    private GameRepository gameRepository;
    @Mock
    private GameMapper gameMapper;
    @InjectMocks
    private GameServiceImpl gameService;

    @Test
    public void shouldReturnListOfGamesWhenGetAllGames() {
        Game game1 = Game.builder()
                .id(1L)
                .name("The Witcher 3")
                .price(10.0)
                .description("A story-driven, open-world RPG set in a dark fantasy universe.")
                .releaseDate(LocalDate.parse("2015-05-19"))
                .company("CD Projekt Red")
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build();
        Game game2 = Game.builder()
                .id(2L)
                .name("Cyberpunk 2077")
                .price(59.99)
                .description("An open-world action-adventure story set in Night City.")
                .releaseDate(LocalDate.parse("2020-12-10"))
                .company("CD Projekt Red")
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build();
        List<Game> games = Arrays.asList(game1, game2);

        GameDto gameDto1 = GameDto.builder()
                .name("The Witcher 3")
                .price(10.0)
                .description("A story-driven, open-world RPG set in a dark fantasy universe.")
                .releaseDate(LocalDate.parse("2015-05-19"))
                .company("CD Projekt Red")
                .build();
        GameDto gameDto2 = GameDto.builder()
                .name("Cyberpunk 2077")
                .price(59.99)
                .description("An open-world action-adventure story set in Night City.")
                .releaseDate(LocalDate.parse("2020-12-10"))
                .company("CD Projekt Red")
                .build();
        List<GameDto> expectedGameDtos = Arrays.asList(gameDto1, gameDto2);

        when(gameRepository.findAll()).thenReturn(games);
        when(gameMapper.gameToGameDto(game1)).thenReturn(gameDto1);
        when(gameMapper.gameToGameDto(game2)).thenReturn(gameDto2);
        List<GameDto> gameDtos = gameService.getAllGames();

        assertEquals(expectedGameDtos, gameDtos);
    }

    @Test
    public void shouldReturnEmptyListWhenNoGamesExist(){
        when(gameRepository.findAll()).thenReturn(Collections.emptyList());
        List<GameDto> gameDtos = gameService.getAllGames();
        assertTrue(gameDtos.isEmpty());
    }

    @Test
    public void shouldReturnGameDtoIfGameExists(){
        final String gameName = "The Witcher 3";
        Game game = Game.builder()
                .id(1L)
                .name("The Witcher 3")
                .price(10.0)
                .description("A story-driven, open-world RPG set in a dark fantasy universe.")
                .releaseDate(LocalDate.parse("2015-05-19"))
                .company("CD Projekt Red")
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build();
        GameDto expectedGameDto = GameDto.builder()
                .name("The Witcher 3")
                .price(10.0)
                .description("A story-driven, open-world RPG set in a dark fantasy universe.")
                .releaseDate(LocalDate.parse("2015-05-19"))
                .company("CD Projekt Red")
                .build();

        when(gameRepository.findGameByName(gameName)).thenReturn(game);
        when(gameMapper.gameToGameDto(game)).thenReturn(expectedGameDto);
        GameDto actualGameDto = gameService.searchGame(gameName);

        assertEquals(expectedGameDto, actualGameDto);
    }

    @Test
    public void shouldThrowRuntimeExceptionIfGameDoesNotExist(){
        final String gameName = "Non-existent Game";

        when(gameRepository.findGameByName(gameName)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> gameService.searchGame(gameName));
    }

    @Test
    public void shouldCreateAndReturnGameIfGameDoesNotExist(){
        Game game = Game.builder()
                .id(1L)
                .name("New Game")
                .price(40.0)
                .description("A new game")
                .releaseDate(LocalDate.now())
                .company("New Company")
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build();

        when(gameRepository.findGameByName(game.getName())).thenReturn(null);
        when(gameRepository.save(game)).thenReturn(game);
        Game result = gameService.addGame(game);

        assertNotNull(result);
        assertEquals(game.getName(), result.getName());
        assertEquals(game.getPrice(), result.getPrice());
        assertEquals(game.getDescription(), result.getDescription());
        assertEquals(game.getReleaseDate(), result.getReleaseDate());
        assertEquals(game.getCompany(), result.getCompany());
        assertNotNull(result.getCreated());
        assertNotNull(result.getUpdated());
        verify(gameRepository, times(1)).findGameByName(game.getName());
        verify(gameRepository, times(1)).save(game);
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenGameNameAlreadyExists(){
        Game existingGame = Game.builder()
                .id(1L)
                .name("Existing Game")
                .price(20.0)
                .description("An existing game")
                .releaseDate(LocalDate.now())
                .company("Existing Company")
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build();
        Game newGame = Game.builder()
                .id(2L)
                .name("Existing Game")
                .price(30.0)
                .description("A new game with a duplicate name")
                .releaseDate(LocalDate.now())
                .company("New Company")
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build();

        when(gameRepository.findGameByName(existingGame.getName())).thenReturn(existingGame);

        assertThrows(RuntimeException.class, () -> gameService.addGame(newGame));
    }

    @Test
    public void shouldReturnGameByName() {
        Game expectedGame = Game.builder()
                .id(1L)
                .name("The Witcher 3")
                .price(10.0)
                .description("A story-driven, open-world RPG set in a dark fantasy universe.")
                .releaseDate(LocalDate.parse("2015-05-19"))
                .company("CD Projekt Red")
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build();
        when(gameRepository.findGameByName(expectedGame.getName())).thenReturn(expectedGame);

        Game actualGame = gameService.getGameByName(expectedGame.getName());

        assertEquals(expectedGame, actualGame);
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenGameNotFound() {
        String name = "Non-existent Game";
        when(gameRepository.findGameByName(name)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> gameService.getGameByName(name));
    }

    @Test
    public void shouldReturnUpdatedGameIfValidInput() {
        GameDto gameDto = GameDto.builder()
                .name("The Witcher 3")
                .price(10.0)
                .description("A story-driven, open-world RPG set in a dark fantasy universe.")
                .releaseDate(LocalDate.parse("2015-05-19"))
                .company("CD Projekt Red")
                .build();

        Game gameFromDb = Game.builder()
                .id(1L)
                .name("The Witcher 3")
                .price(15.0)
                .description("A story-driven, open-world RPG set in a dark fantasy universe.")
                .releaseDate(LocalDate.parse("2015-05-19"))
                .company("CD Projekt Red")
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build();
        Game game = Game.builder()
                .id(2L)
                .name("The Witcher 3")
                .price(10.0)
                .description("A story-driven, open-world RPG set in a dark fantasy universe.")
                .releaseDate(LocalDate.parse("2015-05-19"))
                .company("CD Projekt Red")
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build();
        GameDto expectedGameDto = GameDto.builder()
                .name("The Witcher 3")
                .price(10.0)
                .description("A story-driven, open-world RPG set in a dark fantasy universe.")
                .releaseDate(LocalDate.parse("2015-05-19"))
                .company("CD Projekt Red")
                .build();

        when(gameMapper.gameDtoToGame(gameDto)).thenReturn(game);
        when(gameRepository.findGameByName(gameDto.getName())).thenReturn(gameFromDb);
        when(gameRepository.save(any(Game.class))).thenReturn(gameFromDb);
        when(gameMapper.gameToGameDto(any(Game.class))).thenReturn(expectedGameDto);
        GameDto updatedGameDto = gameService.updateGame(gameDto);

        assertEquals(gameDto.getName(), updatedGameDto.getName());
        assertEquals(gameDto.getCompany(), updatedGameDto.getCompany());
        assertEquals(gameDto.getReleaseDate(), updatedGameDto.getReleaseDate());
        assertEquals(gameDto.getPrice(), updatedGameDto.getPrice());
        assertEquals(gameDto.getDescription(), updatedGameDto.getDescription());
    }

    @Test
    public void shouldRemoveGameFromDbIfGameExists() {
        Game game = Game.builder()
                .id(1L)
                .name("The Witcher 3")
                .price(10.0)
                .description("A story-driven, open-world RPG set in a dark fantasy universe.")
                .releaseDate(LocalDate.parse("2015-05-19"))
                .company("CD Projekt Red")
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build();
        when(gameRepository.findGameByName(game.getName())).thenReturn(game);

        gameService.deleteGameByName(game.getName());

        verify(gameRepository, times(1)).deleteGameByName(game.getName());
    }

    @Test()
    public void shouldThrowRuntimeExceptionWhenGameDoesNotExist() {
        final String gameName = "Non-existent Game";

        when(gameRepository.findGameByName(gameName)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> gameService.deleteGameByName(gameName));
    }
}