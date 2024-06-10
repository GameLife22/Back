package fr.sqli.formation.gamelife.controller;

import fr.sqli.formation.gamelife.dto.request.GameRequest;
import fr.sqli.formation.gamelife.dto.response.GameResponse;
import fr.sqli.formation.gamelife.enumeration.Genre;
import fr.sqli.formation.gamelife.enumeration.Platform;
import fr.sqli.formation.gamelife.service.IGameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 * This class represents a REST controller for managing game resources.
 * It provides endpoints for retrieving, creating, updating, and deleting games.
 */
@RestController
@RequestMapping("/api/v1")
public class GameRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameRestController.class);

    private final IGameService service;

    @Autowired
    public GameRestController(IGameService pIGameService) {
        service = pIGameService;
    }

    /**
     * Retrieves a game by its unique identifier.
     *
     * @param pGameId The unique identifier of the game to retrieve
     * @return ResponseEntity<GameResponse> The HTTP response entity containing the game response if found, HttpStatus.NOT_FOUND otherwise
     */
    @GetMapping("/games/{id}")
    public ResponseEntity<GameResponse> getGameById(@PathVariable("id") UUID pGameId) {
        try {
            LOGGER.info("Fetching game with ID: {}", pGameId);
            GameResponse gameResponse = this.service.getGameById(pGameId);
            LOGGER.info("Game retrieved successfully");
            return ResponseEntity.ok(gameResponse);
        } catch(Exception pException) {
            LOGGER.error("Error occurred while fetching game with ID: {}", pGameId, pException);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Retrieves a game by its name.
     *
     * @param pGameName The name of the game to retrieve
     * @return ResponseEntity<GameResponse> The HTTP response entity containing the game response if found, HttpStatus.NOT_FOUND otherwise
     */
    @GetMapping("/games/search")
    public ResponseEntity<GameResponse> getGameByName(@RequestParam("name") String pGameName) {
        try {
            LOGGER.info("Fetching game by name: {}", pGameName);
            GameResponse gameResponse = this.service.getGameByName(pGameName);
            LOGGER.info("Game retrieved successfully by name: {}", pGameName);
            return ResponseEntity.ok(gameResponse);
        } catch (Exception e) {
            LOGGER.error("Error occurred while fetching game by name: {}", pGameName, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Retrieves a page of games with pagination.
     *
     * @param page The page number to retrieve
     * @param size The total number of games per page
     * @return ResponseEntity<Page<GameResponse>> The HTTP response entity containing the page of GameResponse objects if successful, HttpStatus.BAD_REQUEST otherwise
     */
    @GetMapping("/games")
    public ResponseEntity<Page<GameResponse>> getGamesByPage(@RequestParam int page, @RequestParam int size) {
        try {
            LOGGER.info("Fetching games for page: {} and size: {}", page, size);
            Page<GameResponse> gameResponsePage = this.service.getGamesByPage(page, size);
            LOGGER.info("Games fetched successfully for page: {} and size: {}", page, size);
            return ResponseEntity.ok(gameResponsePage);
        } catch(Exception pException) {
            LOGGER.error("Error occurred while fetching games for page: {} and size: {}", page, size, pException);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Creates a new game based on the provided game request.
     *
     * @param pGameRequest The game request containing the details of the game to be created
     * @return ResponseEntity<GameResponse> The HTTP response entity containing the created game response if successful, HttpStatus.CREATED otherwise
     */
    @PostMapping("/games")
    public ResponseEntity<GameResponse> createGame(@Valid @RequestBody GameRequest pGameRequest) {
        try {
            LOGGER.info("Creating a new game with request: {}", pGameRequest);
            GameResponse gameResponse = this.service.createGame(pGameRequest);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(gameResponse.getId())
                    .toUri();
            LOGGER.info("Game created successfully with ID: {}", gameResponse.getId());
            return ResponseEntity.created(location).body(gameResponse);
        } catch (Exception pException) {
            LOGGER.error("Error occurred while creating game: {}", pGameRequest, pException);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Updates an existing game with the provided game request.
     *
     * @param pGameId The unique identifier of the game to update
     * @param pGameRequest The game request containing the updated details of the game
     * @return ResponseEntity<GameResponse> The HTTP response entity containing the updated game response if successful, HttpStatus.NOT_FOUND otherwise
     */
    @PatchMapping("/games/{id}")
    public ResponseEntity<GameResponse> updateGame(@PathVariable("id") UUID pGameId, @Valid @RequestBody GameRequest pGameRequest) {
        try {
            LOGGER.info("Updating game with ID: {}", pGameId);
            GameResponse gameResponse = this.service.updateGame(pGameId, pGameRequest);
            LOGGER.info("Game updated successfully with ID: {}", pGameId);
            return ResponseEntity.ok(gameResponse);
        } catch(Exception pException) {
            LOGGER.error("Error occurred while updating game with ID: {}", pGameId, pException);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Deletes multiple games based on the provided list of game IDs.
     *
     * @param pGamesIds The list of unique identifiers of the games to delete
     * @return ResponseEntity<Void> The HTTP response entity with status HttpStatus.NO_CONTENT if the games are successfully deleted, HttpStatus.BAD_REQUEST otherwise
     */
    @DeleteMapping("/games")
    public ResponseEntity<Void> deleteGames(@RequestBody List<UUID> pGamesIds) {
        try {
            LOGGER.info("Deleting games with IDs: {}", pGamesIds);
            this.service.deleteGames(pGamesIds);
            LOGGER.info("Games deleted successfully");
            return ResponseEntity.noContent().build();
        } catch(Exception pException) {
            LOGGER.error("Error occurred while deleting games: {}", pGamesIds, pException);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Deletes a game based on the provided game ID.
     *
     * @param pGameId The unique identifier of the game to delete
     * @return ResponseEntity<Void> The HTTP response entity with status HttpStatus.NO_CONTENT if the game is successfully deleted, HttpStatus.BAD_REQUEST otherwise
     */
    @DeleteMapping("/games/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable("id") UUID pGameId) {
        try {
            LOGGER.info("Deleting game with ID: {}", pGameId);
            this.service.deleteGame(pGameId);
            LOGGER.info("Game deleted successfully with ID: {}", pGameId);
            return ResponseEntity.noContent().build();
        } catch(Exception pException) {
            LOGGER.error("Error occurred while deleting game with ID: {}", pGameId, pException);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Get the list of available genres for games.
     *
     * @return ResponseEntity containing an array of Genre objects
     */
    @GetMapping("/games/genres")
    public ResponseEntity<Genre[]> getGenres() {
        LOGGER.info("Retrieving list of genres");
        Genre[] genres = Genre.values();
        return ResponseEntity.ok(genres);
    }

    /**
     * Get the list of available platforms for games.
     *
     * @return ResponseEntity containing an array of Platform objects
     */
    @GetMapping("/games/platforms")
    public ResponseEntity<Platform[]> getPlatforms() {
        LOGGER.info("Retrieving list of platforms");
        Platform[] platforms = Platform.values();
        return ResponseEntity.ok(platforms);
    }
}