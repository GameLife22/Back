package fr.sqli.formation.gamelife.repository;

import fr.sqli.formation.gamelife.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface for accessing and managing GameEntity objects in the database.
 * Extends JpaRepository to provide basic CRUD operations for GameEntity entities.
 * Includes custom methods for finding a game by name and deleting games by their IDs.
 */
@Repository
public interface IGameRepository extends JpaRepository<GameEntity, UUID> {
    /**
     * Retrieves an Optional of GameEntity based on the provided game name.
     *
     * @param pGameName the name of the game to search for
     * @return an Optional containing the GameEntity if found, empty otherwise
     */
    public Optional<GameEntity> findByName(String pGameName);

    /**
     * Retrieves an Optional of GameEntity by searching for a game with a name containing the specified case-insensitive substring.
     *
     * @param pGameName the case-insensitive substring to search for in the game names
     * @return an Optional containing the GameEntity if found, empty otherwise
     */
    public List<GameEntity> findByNameContainingIgnoreCase(String pGameName);

    /**
     * Deletes all entities with the specified IDs.
     */
    void deleteAllByIdIn(List<UUID> pGamesIds);

    /**
     * Retrieves the total number of games available in the database.
     *
     * @return the total number of games
     */
    @Query(value = "SELECT gamelife.count_games()", nativeQuery = true)
    int getTotalGames();
}