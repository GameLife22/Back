package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.request.GameRequest;
import fr.sqli.formation.gamelife.dto.response.GameResponse;
import fr.sqli.formation.gamelife.enumeration.Genre;
import fr.sqli.formation.gamelife.enumeration.Platform;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
@Rollback
@ActiveProfiles("test")
class GameServiceIntegrationTest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.1");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @Autowired
    private GameService gameService;

    private GameRequest gameRequest;

    @BeforeEach
    public void setUp() {;
        this.gameRequest = new GameRequest();
        this.gameRequest.setName("name");
        this.gameRequest.setDescription("description");
        this.gameRequest.setGenres(new HashSet<>(Set.of(Genre.ARCADE)));
        this.gameRequest.setPlatforms(new HashSet<>(Set.of(Platform.XBOX)));
        this.gameRequest.setImages(new ArrayList<>(List.of("https://image1.png", "file://image2.jpg")));
    }

    @Test
    void givenGameRequest_whenCreateGame_thenReturnGameResponse() {
        GameResponse createdGameResponse = this.gameService.createGame(this.gameRequest);

        Assertions.assertNotNull(createdGameResponse);
        Assertions.assertNotNull(createdGameResponse.getId());
        Assertions.assertEquals(this.gameRequest.getName(), createdGameResponse.getName());
        Assertions.assertEquals(this.gameRequest.getDescription(), createdGameResponse.getDescription());
        Assertions.assertEquals(this.gameRequest.getGenres(), createdGameResponse.getGenres());
        Assertions.assertEquals(this.gameRequest.getPlatforms(), createdGameResponse.getPlatforms());
        Assertions.assertEquals(this.gameRequest.getImages(), createdGameResponse.getImages());
    }

    @Test
    void givenGameRequest_whenGetGameByName_thenReturnGameResponse() {
        GameResponse createdGameResponse = this.gameService.createGame(this.gameRequest);

        GameResponse gameResponse = this.gameService.getGameByName(createdGameResponse.getName());

        Assertions.assertNotNull(createdGameResponse);
        Assertions.assertNotNull(gameResponse);
        Assertions.assertEquals(this.gameRequest.getName(), gameResponse.getName());
        Assertions.assertEquals(this.gameRequest.getDescription(), gameResponse.getDescription());
        Assertions.assertEquals(this.gameRequest.getGenres(), gameResponse.getGenres());
        Assertions.assertEquals(this.gameRequest.getPlatforms(), gameResponse.getPlatforms());
        Assertions.assertEquals(this.gameRequest.getImages(), gameResponse.getImages());
    }

    @Test
    void givenGameId_whenGetGameById_thenReturnGameResponse() {
        GameResponse createdGameResponse = this.gameService.createGame(this.gameRequest);

        GameResponse gameResponse = this.gameService.getGameById(createdGameResponse.getId());

        Assertions.assertNotNull(createdGameResponse);
        Assertions.assertNotNull(gameResponse);
        Assertions.assertEquals(createdGameResponse.getId(), gameResponse.getId());
        Assertions.assertEquals(gameRequest.getName(), gameResponse.getName());
        Assertions.assertEquals(gameRequest.getDescription(), gameResponse.getDescription());
        Assertions.assertEquals(gameRequest.getGenres(), gameResponse.getGenres());
        Assertions.assertEquals(gameRequest.getPlatforms(), gameResponse.getPlatforms());
        Assertions.assertEquals(gameRequest.getImages(), gameResponse.getImages());
    }

    @Test
    void givenPageAndSizeOfGame_whenGetGamesByPage_thenReturnPageWithGames() {
        int page = 0;
        int size = 5;
        this.gameService.createGame(this.gameRequest);

        Page<GameResponse> gameResponsePage = this.gameService.getGamesByPage(page, size);

        Assertions.assertNotNull(gameResponsePage);
        Assertions.assertFalse(gameResponsePage.isEmpty());
    }

    @Test
    void givenExistingGameRequest_whenUpdateGame_thenReturnUpdatedGameResponse() {
        GameResponse createdGameResponse = this.gameService.createGame(this.gameRequest);

        GameRequest gameRequest2 = new GameRequest();
        gameRequest2.setName("updated name");
        gameRequest2.setDescription("updated description");
        gameRequest2.setGenres(new HashSet<>(Set.of(Genre.BOARD_GAMES)));
        gameRequest2.setPlatforms((new HashSet<>(Set.of(Platform.GAME_BOY))));
        gameRequest2.setImages(new ArrayList<>(List.of("https://image3.png", "file://image4.jpg")));

        GameResponse updatedGameResponse = this.gameService.updateGame(createdGameResponse.getId(), gameRequest2);

        Assertions.assertNotNull(createdGameResponse);
        Assertions.assertNotNull(updatedGameResponse);
        Assertions.assertEquals(createdGameResponse.getId(), updatedGameResponse.getId());
        Assertions.assertNotEquals(createdGameResponse.getName(), updatedGameResponse.getName());
        Assertions.assertNotEquals(createdGameResponse.getDescription(), updatedGameResponse.getDescription());
        Assertions.assertNotEquals(createdGameResponse.getGenres(), updatedGameResponse.getGenres());
        Assertions.assertNotEquals(createdGameResponse.getPlatforms(), updatedGameResponse.getPlatforms());
        Assertions.assertNotEquals(createdGameResponse.getImages(), updatedGameResponse.getImages());
    }

    @Test
    void givenGameId_whenDeleteGameById_thenReturnNothing() {
        GameResponse createdGameResponse = this.gameService.createGame(this.gameRequest);

        this.gameService.deleteGameById(createdGameResponse.getId());

        Assertions.assertNotNull(createdGameResponse);
        Assertions.assertThrows(EntityNotFoundException.class, () -> this.gameService.getGameById(createdGameResponse.getId()));
    }

    @Test
    void givenGamesIds_whenDeleteGamesByIds_thenReturnNothing() {
        GameResponse createdGameResponse1 = this.gameService.createGame(this.gameRequest);
        GameRequest gameRequest2 = new GameRequest();
        gameRequest2.setName("name 2");
        gameRequest2.setDescription("description");
        gameRequest2.setGenres(Set.of(Genre.ARCADE));
        gameRequest2.setPlatforms(Set.of(Platform.XBOX));
        gameRequest2.setImages(List.of("https://image1.png", "file://image2.jpg"));
        GameResponse createdGameResponse2 = this.gameService.createGame(gameRequest2);
        List<UUID> gamesIds = List.of(createdGameResponse1.getId(), createdGameResponse2.getId());

        this.gameService.deleteGamesByIds(gamesIds);

        Assertions.assertNotNull(createdGameResponse1);
        Assertions.assertNotNull(createdGameResponse2);

        for (UUID gameId : gamesIds) {
            Assertions.assertThrows(EntityNotFoundException.class, () -> this.gameService.getGameById(gameId));
        }
    }
}