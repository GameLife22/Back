package fr.sqli.formation.gamelife.controller;

import fr.sqli.formation.gamelife.dto.request.GameRequest;
import fr.sqli.formation.gamelife.dto.response.GameResponse;
import fr.sqli.formation.gamelife.exception.GameExistsException;
import fr.sqli.formation.gamelife.exception.GameNotFoundException;
import fr.sqli.formation.gamelife.service.IGameService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
     * Retrieves a specific game by its unique identifier.
     *
     * @param pGameId the unique identifier of the game to retrieve
     * @return a ResponseEntity containing the GameResponse object representing the retrieved game
     * @throws GameNotFoundException if the game with the provided ID is not found
     */
    @GetMapping("/games/{id}")
    public ResponseEntity<GameResponse> getGameById(@PathVariable("id") UUID pGameId) throws GameNotFoundException {
        LOGGER.info("Fetching game with ID: {}", pGameId);
        GameResponse gameResponse = this.service.getGameById(pGameId);
        LOGGER.info("Game retrieved successfully");
        return ResponseEntity.ok(gameResponse);
    }

    /**
     * Retrieves a list of GameResponse objects that contain the provided game title (case-insensitive).
     *
     * @param pGameTitle the title of the game to search for
     * @return a ResponseEntity containing a list of GameResponse objects that match the provided game title
     * @throws GameNotFoundException if no games are found with the provided title
     */
    @GetMapping("/games/search")
    public ResponseEntity<List<GameResponse>> findByTitleContainingIgnoreCase(@RequestParam("title") String pGameTitle) throws GameNotFoundException {
        LOGGER.info("Fetching game by title: {}", pGameTitle);
        List<GameResponse> gamesResponses = this.service.findByTitleContainingIgnoreCase(pGameTitle);
        LOGGER.info("Game(s) retrieved successfully by title: {}", pGameTitle);
        return ResponseEntity.ok(gamesResponses);
    }

    /**
     * Retrieves a page of GameResponse objects representing games based on the provided page number and total number of pages.
     *
     * @param page the page number to retrieve
     * @param size the total number of games per page
     * @return a ResponseEntity containing a Page object with GameResponse instances for the specified page
     */
    @GetMapping("/games")
    public ResponseEntity<Page<GameResponse>> getGamesByPage(@RequestParam int page, @RequestParam int size) {
        LOGGER.info("Fetching games for page: {} and size: {}", page, size);
        Page<GameResponse> gameResponsePage = this.service.getGamesByPage(page, size);
        LOGGER.info("Games fetched successfully for page: {} and size: {}", page, size);
        return ResponseEntity.ok(gameResponsePage);
    }

    /**
     * Creates a new game based on the provided GameRequest object.
     *
     * @param pGameRequest the GameRequest object containing the details of the game to be created
     * @return a ResponseEntity containing the GameResponse object representing the newly created game
     * @throws GameExistsException if a game with the same ID already exists
     */
    @PostMapping("/games")
    public ResponseEntity<GameResponse> createGame(@Valid @RequestBody GameRequest pGameRequest) throws GameExistsException {
        LOGGER.info("Creating a new game with request: {}", pGameRequest);
        GameResponse gameResponse = this.service.createGame(pGameRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(gameResponse.getId())
                .toUri();
        LOGGER.info("Game created successfully with ID: {}", gameResponse.getId());
        return ResponseEntity.created(location).body(gameResponse);
    }

    /**
     * Updates a game with the provided game ID and game request data.
     *
     * @param pGameId the ID of the game to update
     * @param pGameRequest the request object containing the updated game information
     * @return the updated GameResponse object
     * @throws GameExistsException if a game with the same ID already exists
     */
    @PatchMapping("/games/{id}")
    public ResponseEntity<GameResponse> updateGame(@PathVariable("id") UUID pGameId, @Valid @RequestBody GameRequest pGameRequest) throws GameNotFoundException {
        LOGGER.info("Updating game with ID: {}", pGameId);
        GameResponse gameResponse = this.service.updateGame(pGameId, pGameRequest);
        LOGGER.info("Game updated successfully with ID: {}", pGameId);
        return ResponseEntity.ok(gameResponse);
    }

    /**
     * Deletes a game by its unique identifier.
     *
     * @param pGameId the unique identifier of the game to delete
     * @return a ResponseEntity with no content to indicate successful deletion
     * @throws GameNotFoundException if the game with the provided ID is not found
     */
    @DeleteMapping("/games/{id}")
    public ResponseEntity<Void> deleteGameById(@PathVariable("id") UUID pGameId) throws GameNotFoundException {
        LOGGER.info("Deleting game with ID: {}", pGameId);
        this.service.deleteGameById(pGameId);
        LOGGER.info("Game deleted successfully with ID: {}", pGameId);
        return ResponseEntity.noContent().build();
    }
}