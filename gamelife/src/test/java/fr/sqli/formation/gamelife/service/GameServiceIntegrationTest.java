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
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Import(TestContainerConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
@Testcontainers
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
    void givenGameRequest_whenCreateGame_thenReturnGameResponse() {
        GameRequest gameRequest = createDefaultGameRequest("name");

        GameResponse createdGameResponse = this.gameService.createGame(gameRequest);

        Assertions.assertNotNull(createdGameResponse.getId());
        Assertions.assertEquals(gameRequest.getName(), createdGameResponse.getName());
        Assertions.assertEquals(gameRequest.getDescription(), createdGameResponse.getDescription());
        Assertions.assertEquals(gameRequest.getGenres(), createdGameResponse.getGenres());
        Assertions.assertEquals(gameRequest.getPlatforms(), createdGameResponse.getPlatforms());
        Assertions.assertEquals(gameRequest.getImages(), createdGameResponse.getImages());
    }

    @Test
    void givenExistingGameRequest_whenCreateGame_thenThrowEntityExistsException() {
        GameRequest gameRequest = createDefaultGameRequest("name");

        this.gameService.createGame(gameRequest);
        Assertions.assertThrows(EntityExistsException.class, () -> this.gameService.createGame(gameRequest));
    }

    @Test
    void givenGameRequest_whenGetGameByName_thenReturnGameResponse() {
        GameRequest gameRequest = createDefaultGameRequest("name");
        GameResponse createdGameResponse = this.gameService.createGame(gameRequest);

        GameResponse gameResponse = this.gameService.getGameByName(createdGameResponse.getName());

        Assertions.assertNotNull(gameResponse);
        Assertions.assertEquals(gameRequest.getName(), gameResponse.getName());
        Assertions.assertEquals(gameRequest.getDescription(), gameResponse.getDescription());
        Assertions.assertEquals(gameRequest.getGenres(), gameResponse.getGenres());
        Assertions.assertEquals(gameRequest.getPlatforms(), gameResponse.getPlatforms());
        Assertions.assertEquals(gameRequest.getImages(), gameResponse.getImages());
    }

    @Test
    void givenInvalidGameName_whenGetGameByName_thenThrowEntityNotFoundException() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> this.gameService.getGameByName(RandomStringUtils.randomAlphabetic(20)));
    }

    @Test
    void givenGameId_whenGetGameById_thenReturnGameResponse() {
        GameRequest gameRequest = createDefaultGameRequest("name");
        GameResponse createdGameResponse = this.gameService.createGame(gameRequest);

        GameResponse gameResponse = this.gameService.getGameById(createdGameResponse.getId());

        Assertions.assertNotNull(gameResponse);
        Assertions.assertEquals(createdGameResponse.getId(), gameResponse.getId());
        Assertions.assertEquals(gameRequest.getName(), gameResponse.getName());
        Assertions.assertEquals(gameRequest.getDescription(), gameResponse.getDescription());
        Assertions.assertEquals(gameRequest.getGenres(), gameResponse.getGenres());
        Assertions.assertEquals(gameRequest.getPlatforms(), gameResponse.getPlatforms());
        Assertions.assertEquals(gameRequest.getImages(), gameResponse.getImages());
    }

    @Test
    void givenInvalidGameId_whenGetGameById_thenThrowEntityNotFoundException() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> this.gameService.getGameById(UUID.randomUUID()));
    }

    @Test
    void givenPageAndSizeOfGame_whenGetGamesByPage_thenReturnPageWithGames() {
        int page = 0;
        int size = 5;
        GameRequest gameRequest = createDefaultGameRequest("name");

        this.gameService.createGame(gameRequest);
        Page<GameResponse> gameResponsePage = this.gameService.getGamesByPage(page, size);

        Assertions.assertNotNull(gameResponsePage);
        Assertions.assertFalse(gameResponsePage.isEmpty());
    }

    @Test
    void givenExistingGameRequest_whenUpdateGame_thenReturnUpdatedGameResponse() {
        GameRequest gameRequest1 = createDefaultGameRequest("name");
        GameResponse createdGameResponse = this.gameService.createGame(gameRequest1);
        GameRequest gameRequest2 = createDefaultGameRequest("name updated");
        gameRequest2.setId(createdGameResponse.getId());
        GameResponse updatedGameResponse = this.gameService.updateGame(gameRequest2);

        Assertions.assertNotNull(createdGameResponse);
        Assertions.assertNotNull(updatedGameResponse);
        Assertions.assertEquals(createdGameResponse.getId(), updatedGameResponse.getId());
        Assertions.assertEquals(createdGameResponse.getName(), updatedGameResponse.getName());
        Assertions.assertEquals(createdGameResponse.getDescription(), updatedGameResponse.getDescription());
        Assertions.assertEquals(createdGameResponse.getGenres(), updatedGameResponse.getGenres());
        Assertions.assertEquals(createdGameResponse.getPlatforms(), updatedGameResponse.getPlatforms());
        Assertions.assertEquals(createdGameResponse.getImages(), updatedGameResponse.getImages());
    }

    @Test
    void givenNotExistingGameRequest_whenUpdateGame_thenThrowEntityNotFoundException() {
        GameRequest gameRequest = createDefaultGameRequest("name");
        this.gameService.createGame(gameRequest);
        GameRequest updatedGameRequest = createDefaultGameRequest("name updated");
        updatedGameRequest.setId(UUID.randomUUID());
        Assertions.assertThrows(EntityNotFoundException.class, () -> this.gameService.updateGame(updatedGameRequest));
    }

    @Test
    void givenGameId_whenDeleteGameById_thenReturnNothing() {
        GameRequest gameRequest = createDefaultGameRequest("name");
        GameResponse createdGameResponse = this.gameService.createGame(gameRequest);
        this.gameService.deleteGameById(createdGameResponse.getId());

        Assertions.assertThrows(EntityNotFoundException.class, () -> this.gameService.getGameById(createdGameResponse.getId()));
    }

    @Test
    void givenGameId_whenDeleteGameById_thenThrowEntityNotFoundException() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> this.gameService.deleteGameById(UUID.randomUUID()));
    }

    @Test
    void givenGamesIds_whenDeleteGamesByIds_thenReturnNothing() {
        GameRequest gameRequest1 = createDefaultGameRequest("name1");
        GameRequest gameRequest2 = createDefaultGameRequest("name2");

        GameResponse gameResponse1 = this.gameService.createGame(gameRequest1);
        GameResponse gameResponse2 = this.gameService.createGame(gameRequest2);

        List<UUID> gamesIds = List.of(gameResponse1.getId(), gameResponse2.getId());
        this.gameService.deleteGamesByIds(gamesIds);

        for (UUID gameId : gamesIds) {
            Assertions.assertThrows(EntityNotFoundException.class, () -> this.gameService.getGameById(gameId));
        }
    }

    @Test
    void givenGamesIds_whenDeleteGamesByIds_thenThrowEntityNotFoundException() {
        List<UUID> gamesIds = List.of(UUID.randomUUID(), UUID.randomUUID());
        Assertions.assertThrows(EntityNotFoundException.class, () -> this.gameService.deleteGamesByIds(gamesIds));
    }
}