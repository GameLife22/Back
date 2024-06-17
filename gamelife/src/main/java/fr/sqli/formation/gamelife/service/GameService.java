package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.utility.converter.IGameConverter;
import fr.sqli.formation.gamelife.dto.request.GameRequest;
import fr.sqli.formation.gamelife.dto.response.GameResponse;
import fr.sqli.formation.gamelife.repository.IGameRepository;
import fr.sqli.formation.gamelife.entity.GameEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import java.util.*;

/**
 * Service class that implements the IGameService interface.
 * Provides methods for retrieving, creating, updating, and deleting game entities.
 */
@Service
public class GameService implements IGameService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);
    private final IGameRepository gameRepository;

    @Autowired
    public GameService(IGameRepository pIGameRepository) {
        this.gameRepository = pIGameRepository;
    }

    @Override
    public List<GameResponse> findByNameContainingIgnoreCase(String pGameName) {
        LOGGER.info("Searching for game by name containing (case-insensitive): {}", pGameName);
        List<GameEntity> gamesEntities = this.gameRepository.findByNameContainingIgnoreCase(pGameName);

        if(gamesEntities.isEmpty())
            throw new EntityNotFoundException("No games found with the name: " + pGameName);

        return IGameConverter.convertGamesEntitiesToGamesResponse(gamesEntities);
    }

    @Override
    public GameResponse getGameById(UUID pGameId) {
        LOGGER.info("Getting game by ID: {}", pGameId);
        return IGameConverter.convertGameEntityToGameResponse(this.gameRepository.findById(pGameId)
                .orElseThrow(() -> new EntityNotFoundException("Game not found for ID: " + pGameId)));
    }

    @Override
    public Page<GameResponse> getGamesByPage(int pPage, int pTotalPages) {
        LOGGER.info("Getting games for page: {} with total pages: {}", pPage, pTotalPages);
        Pageable pageable = PageRequest.of(pPage, pTotalPages);
        return IGameConverter.convertGamesEntitiesToPageGamesResponses(this.gameRepository.findAll(pageable));
    }

    @Override
    public GameResponse createGame(GameRequest pGameRequest) {
        LOGGER.info("Creating a new game with name: {}", pGameRequest.getName());

        Optional<GameEntity> optionalGameEntity = this.gameRepository.findByName(pGameRequest.getName());
        optionalGameEntity.ifPresent(entite -> {
            LOGGER.error("Game with name {} already exists", pGameRequest.getName());
            throw new EntityExistsException("Game with name already exists");
        });

        GameEntity gameEntity = IGameConverter.convertGameRequestToGameEntity(pGameRequest);
        GameEntity savedGameEntity = this.gameRepository.save(gameEntity);
        LOGGER.info("Game created successfully with ID: {}", savedGameEntity.getId());

        return IGameConverter.convertGameEntityToGameResponse(savedGameEntity);
    }

    /**
     * Updates the fields of an existing GameEntity object with the values from a GameRequest object.
     *
     * @param pGameRequest The GameRequest object containing the new values for the fields.
     * @param existingGameEntity The existing GameEntity object to be updated.
     * @return The updated GameEntity object with fields modified based on the GameRequest object.
     */
    private static GameEntity updateGameEntityFromRequest(GameRequest pGameRequest, GameEntity existingGameEntity) {
        LOGGER.info("Updating game entity from request: {}", pGameRequest.getId());
        existingGameEntity.setName(pGameRequest.getName());
        existingGameEntity.setDescription(pGameRequest.getDescription());
        existingGameEntity.setGenres(pGameRequest.getGenres());
        existingGameEntity.setPlatforms(pGameRequest.getPlatforms());
        existingGameEntity.setImages(pGameRequest.getImages());
        LOGGER.info("Game entity updated successfully: {}", existingGameEntity.getId());
        return existingGameEntity;
    }

    @Override
    public GameResponse updateGame(UUID pGameId, GameRequest pGameRequest) {
        LOGGER.info("Updating game with ID: {}", pGameRequest.getId());

        Optional<GameEntity> existingOptionalGameEntity = this.gameRepository.findById(pGameId);

        if (existingOptionalGameEntity.isEmpty())
            throw new EntityNotFoundException("Game with ID " + pGameId + " not found");

        GameEntity gameEntity = updateGameEntityFromRequest(pGameRequest, existingOptionalGameEntity.get());

        GameEntity updatedGameEntity = this.gameRepository.save(gameEntity);

        LOGGER.info("Game updated successfully. Game : {}", updatedGameEntity);
        return IGameConverter.convertGameEntityToGameResponse(updatedGameEntity);
    }

    @Override
    public void deleteGameById(UUID pGameId) {
        LOGGER.info("Deleting game with ID: {}", pGameId);
        this.gameRepository.findById(pGameId)
                .orElseThrow(() -> new EntityNotFoundException("Game not found for ID: " + pGameId));

        this.gameRepository.deleteById(pGameId);
    }
}