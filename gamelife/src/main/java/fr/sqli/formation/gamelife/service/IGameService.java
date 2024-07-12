package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.request.GameRequest;
import fr.sqli.formation.gamelife.dto.response.GameResponse;
import fr.sqli.formation.gamelife.exception.GameExistsException;
import fr.sqli.formation.gamelife.exception.GameNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

/**
 * Interface for managing game-related operations such as retrieving, creating, updating, and deleting games.
 * Includes methods to get a game by its ID, get a list of games with pagination, create a new game,
 * update an existing game, delete a single game by ID, and delete multiple games by their IDs.
 */
public interface IGameService {

    /**
     * Retrieves a list of GameResponse objects that contain the provided game name (case-insensitive).
     *
     * @param pGameTitle the name of the game to search for
     * @return a list of GameResponse objects that match the provided game name
     */
    public List<GameResponse> findByTitleContainingIgnoreCase(String pGameTitle) throws GameNotFoundException;

    /**
     * Represents a service class for retrieving game information.
     * Includes methods to get a specific game by its ID and to retrieve a paginated list of games.
     */
    public GameResponse getGameById(UUID pGameId) throws GameNotFoundException;

    /**
     * Retrieves a page of GameResponse objects representing games based on the provided page number and total number of pages.
     *
     * @param pPage the page number to retrieve
     * @param pTotalPage the total number of pages
     * @return a Page object containing GameResponse instances
     */
    public Page<GameResponse> getGamesByPage(int pPage, int pTotalPage);

    /**
     * Defines the service methods for creating and updating a game.
     */
    public GameResponse createGame(GameRequest pGameRequest) throws GameExistsException;

    /**
     * Updates a game with the provided game ID and game request data.
     *
     * @param pGameId the ID of the game to update
     * @param pGameRequest the request object containing the updated game information
     * @return the updated GameResponse object
     */
    public GameResponse updateGame(UUID pGameId, GameRequest pGameRequest) throws GameNotFoundException;

    /**
     * Method to delete a game by its unique identifier.
     */
    public void deleteGameById(UUID pGameId) throws GameNotFoundException;
}