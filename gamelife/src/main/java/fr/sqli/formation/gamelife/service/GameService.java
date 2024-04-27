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
import jakarta.transaction.Transactional;
import java.util.*;

/**
 * Service class that implements the IGameService interface.
 * Provides methods for retrieving, creating, updating, and deleting game entities.
 */
@Service
@Transactional
public class GameService implements IGameService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);
    private final IGameRepository gameRepository;

    @Autowired
    public GameService(IGameRepository pIGameRepository) {
        this.gameRepository = pIGameRepository;
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
        LOGGER.info("Creating a new game with name: {}", pGameRequest.name());

        Optional<GameEntity> optionalGameEntity = this.gameRepository.findByName(pGameRequest.name());
        optionalGameEntity.ifPresent(entite -> {
            LOGGER.error("Game with name {} already exists", pGameRequest.name());
            throw new EntityExistsException("Game with name already exists");
        });

        GameEntity gameEntity = IGameConverter.convertGameRequestToGameEntity(pGameRequest);
        GameEntity savedGameEntity = this.gameRepository.save(gameEntity);
        LOGGER.info("Game created successfully with ID: {}", savedGameEntity.getId());

        return IGameConverter.convertGameEntityToGameResponse(savedGameEntity);
    }

    @Override
    public GameResponse updateGame(UUID pGameId, GameRequest pGameRequest) {
        LOGGER.info("Updating game with ID: {}", pGameId);

        this.gameRepository.findById(pGameId)
                .orElseThrow(() -> new EntityNotFoundException("Game with ID " + pGameId + " not found"));

        GameEntity gameEntity = IGameConverter.convertGameRequestToGameEntity(pGameRequest);
        GameEntity savedGameEntity = this.gameRepository.save(gameEntity);

        LOGGER.info("Game updated successfully. Game : {}", savedGameEntity);
        return IGameConverter.convertGameEntityToGameResponse(savedGameEntity);
    }

    @Override
    public void deleteGame(UUID pGameId) {
        LOGGER.info("Deleting game with ID: {}", pGameId);
        this.gameRepository.findById(pGameId)
                .orElseThrow(() -> new EntityNotFoundException("Game not found for ID: " + pGameId));

        this.gameRepository.deleteById(pGameId);
    }

    @Override
    public void deleteGames(List<UUID> pGamesIds) {
        LOGGER.info("Deleting games with IDs: {}", pGamesIds);
        this.gameRepository.deleteAllByIdIn(pGamesIds);
        LOGGER.info("Games deleted successfully");
    }
}