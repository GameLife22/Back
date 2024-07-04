package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.TestContainerConfiguration;
import fr.sqli.formation.gamelife.dto.request.GameRequest;
import fr.sqli.formation.gamelife.dto.response.GameResponse;
import fr.sqli.formation.gamelife.enumeration.Genre;
import fr.sqli.formation.gamelife.enumeration.Platform;
import fr.sqli.formation.gamelife.exception.GameExistsException;
import fr.sqli.formation.gamelife.exception.GameNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

@Import(TestContainerConfiguration.class)
@SpringBootTest
@Transactional
@Rollback
@ActiveProfiles("test")
class GameServiceIntegrationTest {

    @Autowired
    private GameService gameService;

    private GameRequest gameRequest;

    @BeforeEach
    public void setUp() {
        this.gameRequest = new GameRequest();
        this.gameRequest.setName("name");
        this.gameRequest.setDescription("description");
        this.gameRequest.setGenres(new HashSet<>(Set.of(Genre.ARCADE)));
        this.gameRequest.setPlatforms(new HashSet<>(Set.of(Platform.XBOX)));
        this.gameRequest.setImages(new ArrayList<>(List.of("https://image1.png", "file://image2.jpg")));
    }

    @Test
    void givenGameRequest_whenCreateGame_thenReturnGameResponse() throws GameExistsException {
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
    void givenGameRequest_whenFindByNameContainingIgnoreCase_thenReturnGamesResponses() throws GameExistsException, GameNotFoundException {
        GameResponse createdGameResponse = this.gameService.createGame(this.gameRequest);

        List<GameResponse> gameResponse = this.gameService.findByNameContainingIgnoreCase("nam");

        Assertions.assertNotNull(createdGameResponse);
        Assertions.assertNotNull(gameResponse);
        Assertions.assertFalse(gameResponse.isEmpty());
    }

    @Test
    void givenGameId_whenGetGameById_thenReturnGameResponse() throws GameNotFoundException, GameExistsException {
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
    void givenPageAndSizeOfGame_whenGetGamesByPage_thenReturnPageWithGames() throws GameExistsException {
        int page = 0;
        int size = 5;
        this.gameService.createGame(this.gameRequest);

        Page<GameResponse> gameResponsePage = this.gameService.getGamesByPage(page, size);

        Assertions.assertNotNull(gameResponsePage);
        Assertions.assertFalse(gameResponsePage.isEmpty());
    }

    @Test
    void givenExistingGameRequest_whenUpdateGame_thenReturnUpdatedGameResponse() throws GameExistsException, GameNotFoundException {
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
    void givenGameId_whenDeleteGameById_thenReturnNothing() throws GameNotFoundException, GameExistsException {
        GameResponse createdGameResponse = this.gameService.createGame(this.gameRequest);

        this.gameService.deleteGameById(createdGameResponse.getId());

        Assertions.assertNotNull(createdGameResponse);
        Assertions.assertThrows(GameNotFoundException.class, () -> this.gameService.getGameById(createdGameResponse.getId()));
    }
}