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
    public GameResponse getGameByName(String pGameName) {
        LOGGER.info("Getting game by name: {}", pGameName);
        return IGameConverter.convertGameEntityToGameResponse(this.gameRepository.findByName(pGameName)
                .orElseThrow(() -> new EntityNotFoundException("Game not found for name: " + pGameName)));
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
        return IGameConverter.convertGamesEntitiesToPageGameResponse(this.gameRepository.findAll(pageable));
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

    private static GameEntity updateGameEntityFromRequest(GameRequest pGameRequest, GameEntity existingGameEntity) {
        existingGameEntity.setName(pGameRequest.getName());
        existingGameEntity.setDescription(pGameRequest.getDescription());
        existingGameEntity.setGenres(pGameRequest.getGenres());
        existingGameEntity.setPlatforms(pGameRequest.getPlatforms());
        existingGameEntity.setImages(pGameRequest.getImages());
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

    @Override
    public void deleteGamesByIds(List<UUID> pGamesIds) {
        LOGGER.info("Deleting games with IDs: {}", pGamesIds);
        for (UUID pGameId : pGamesIds) {
            this.gameRepository.findById(pGameId)
                    .orElseThrow(() -> new EntityNotFoundException("Game not found for ID: " + pGameId));
        }
        this.gameRepository.deleteAllByIdIn(pGamesIds);
        LOGGER.info("Games deleted successfully");
    }
}