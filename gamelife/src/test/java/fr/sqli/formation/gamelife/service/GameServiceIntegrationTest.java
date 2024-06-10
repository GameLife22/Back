package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.TestContainerConfiguration;
import fr.sqli.formation.gamelife.dto.request.GameRequest;
import fr.sqli.formation.gamelife.dto.response.GameResponse;
import fr.sqli.formation.gamelife.enumeration.Genre;
import fr.sqli.formation.gamelife.enumeration.Platform;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Import(TestContainerConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@SpringBootTest
@ActiveProfiles("test")
class GameServiceIntegrationTest {

    @Autowired
    private GameService gameService;

    private GameRequest createDefaultGameRequest(String name) {
        GameRequest gameRequest = new GameRequest();
        gameRequest.setName(name);
        gameRequest.setDescription("description");
        gameRequest.setGenres(Set.of(Genre.ARCADE));
        gameRequest.setPlatforms(Set.of(Platform.XBOX));
        gameRequest.setImages(List.of("https://image1.png", "file://image2.jpg"));
        return gameRequest;
    }

    @Test
    void CreateGame_ShouldReturnGameResponse() {
        GameRequest gameRequest = createDefaultGameRequest("name");

        GameResponse gameResponse = this.gameService.createGame(gameRequest);

        Assertions.assertNotNull(gameResponse.getId());
        Assertions.assertEquals(gameRequest.getName(), gameResponse.getName());
        Assertions.assertEquals(gameRequest.getDescription(), gameResponse.getDescription());
        Assertions.assertEquals(gameRequest.getGenres(), gameResponse.getGenres());
        Assertions.assertEquals(gameRequest.getPlatforms(), gameResponse.getPlatforms());
        Assertions.assertEquals(gameRequest.getImages(), gameResponse.getImages());
    }

    @Test
    void CreateGame_WithExistingGame_ShouldThrowEntityExistsException() {
        GameRequest gameRequest = createDefaultGameRequest("name");

        this.gameService.createGame(gameRequest);
        Assertions.assertThrows(EntityExistsException.class, () -> this.gameService.createGame(gameRequest));
    }

    @Test
    void GetGameByName_ShouldReturnGameResponse() {
        GameRequest gameRequest = createDefaultGameRequest("name");

        this.gameService.createGame(gameRequest);
        GameResponse gameResponse = this.gameService.getGameByName(gameRequest.getName());

        Assertions.assertNotNull(gameResponse);
        Assertions.assertEquals(gameRequest.getName(), gameResponse.getName());
        Assertions.assertEquals(gameRequest.getDescription(), gameResponse.getDescription());
        Assertions.assertEquals(gameRequest.getGenres(), gameResponse.getGenres());
        Assertions.assertEquals(gameRequest.getPlatforms(), gameResponse.getPlatforms());
        Assertions.assertEquals(gameRequest.getImages(), gameResponse.getImages());
    }

    @Test
    void GetGameByName_WithNonExistentGame_ShouldThrowEntityNotFoundException() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> this.gameService.getGameByName(RandomStringUtils.randomAlphabetic(20)));
    }

    @Test
    void GetGameById_ShouldReturnGameResponse() {
        GameRequest gameRequest = createDefaultGameRequest("name");

        GameResponse createdGame = this.gameService.createGame(gameRequest);
        GameResponse gameResponse = this.gameService.getGameById(createdGame.getId());

        Assertions.assertNotNull(gameResponse);
        Assertions.assertEquals(createdGame.getId(), gameResponse.getId());
        Assertions.assertEquals(gameRequest.getName(), gameResponse.getName());
        Assertions.assertEquals(gameRequest.getDescription(), gameResponse.getDescription());
        Assertions.assertEquals(gameRequest.getGenres(), gameResponse.getGenres());
        Assertions.assertEquals(gameRequest.getPlatforms(), gameResponse.getPlatforms());
        Assertions.assertEquals(gameRequest.getImages(), gameResponse.getImages());
    }

    @Test
    void GetGameById_WithNonExistentGame_ShouldThrowEntityNotFoundException() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> this.gameService.getGameById(UUID.randomUUID()));
    }

    @Test
    void GetGamesByPage_ShouldReturnNonEmptyPage() {
        int page = 0;
        int size = 5;
        GameRequest gameRequest = createDefaultGameRequest("name");

        this.gameService.createGame(gameRequest);
        Page<GameResponse> gameResponsePage = this.gameService.getGamesByPage(page, size);

        Assertions.assertNotNull(gameResponsePage);
        Assertions.assertFalse(gameResponsePage.isEmpty());
    }

    @Test
    void UpdateGame_ShouldReturnUpdatedGameResponse() {
        GameRequest gameRequest1 = createDefaultGameRequest("name");
        GameResponse createdGame = this.gameService.createGame(gameRequest1);

        GameRequest gameRequest2 = new GameRequest();
        gameRequest2.setName("name updated");
        gameRequest2.setDescription("description updated");
        gameRequest2.setGenres(Set.of(Genre.CARD));
        gameRequest2.setPlatforms(Set.of(Platform.PLAYSTATION));
        gameRequest2.setImages(List.of("https://image3.png", "file://image4.jpg"));

        GameResponse updatedGame = this.gameService.updateGame(createdGame.getId(), gameRequest2);

        Assertions.assertNotNull(updatedGame);
        //Assertions.assertEquals(createdGame.getId(), updatedGame.getId());
        Assertions.assertEquals(gameRequest2.getName(), updatedGame.getName());
        Assertions.assertEquals(gameRequest2.getDescription(), updatedGame.getDescription());
        Assertions.assertEquals(gameRequest2.getGenres(), updatedGame.getGenres());
        Assertions.assertEquals(gameRequest2.getPlatforms(), updatedGame.getPlatforms());
        Assertions.assertEquals(gameRequest2.getImages(), updatedGame.getImages());
    }

    @Test
    void UpdateGame_WithNonExistentGame_ShouldThrowEntityNotFoundException() {
        GameRequest gameRequest = createDefaultGameRequest("name updated");
        Assertions.assertThrows(EntityNotFoundException.class, () -> this.gameService.updateGame(UUID.randomUUID(), gameRequest));
    }

    @Test
    void DeleteGame_ShouldSucceed() {
        GameRequest gameRequest = createDefaultGameRequest("name");
        GameResponse createdGame = this.gameService.createGame(gameRequest);
        this.gameService.deleteGame(createdGame.getId());

        Assertions.assertThrows(EntityNotFoundException.class, () -> this.gameService.getGameById(createdGame.getId()));
    }

    @Test
    void DeleteGame_WithNonExistentGame_ShouldThrowEntityNotFoundException() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> this.gameService.deleteGame(UUID.randomUUID()));
    }

    @Test
    void DeleteGames_ShouldSucceed() {
        GameRequest gameRequest1 = createDefaultGameRequest("name1");
        GameRequest gameRequest2 = createDefaultGameRequest("name2");

        GameResponse gameResponse1 = this.gameService.createGame(gameRequest1);
        GameResponse gameResponse2 = this.gameService.createGame(gameRequest2);

        List<UUID> gameIds = List.of(gameResponse1.getId(), gameResponse2.getId());
        this.gameService.deleteGames(gameIds);

        for (UUID gameId : gameIds) {
            Assertions.assertThrows(EntityNotFoundException.class, () -> this.gameService.getGameById(gameId));
        }
    }

    @Test
    void DeleteGames_WithNonExistentGames_ShouldThrowEntityNotFoundException() {
        List<UUID> gameIds = List.of(UUID.randomUUID(), UUID.randomUUID());
        Assertions.assertThrows(EntityNotFoundException.class, () -> this.gameService.deleteGames(gameIds));
    }
}