package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.request.GameRequest;
import fr.sqli.formation.gamelife.dto.response.GameResponse;
import fr.sqli.formation.gamelife.entity.GameEntity;
import fr.sqli.formation.gamelife.exception.GameExistsException;
import fr.sqli.formation.gamelife.exception.GameNotFoundException;
import fr.sqli.formation.gamelife.repository.IGameRepository;
import fr.sqli.formation.gamelife.utility.converter.IGameConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class that implements the IGameService interface.
 * Provides methods for retrieving, creating, updating, and deleting game entities.
 */
@Service
public class GameService implements IGameService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);

    private static final int TOTAL_GAMES = 100;

    private final IGameRepository gameRepository;

    @Autowired
    public GameService(IGameRepository pIGameRepository) {
        this.gameRepository = pIGameRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GameResponse> findByTitleContainingIgnoreCase(String pGameTitle) throws GameNotFoundException {
        LOGGER.info("Searching for game by name containing (case-insensitive): {}", pGameTitle);
        List<GameEntity> gamesEntities = this.gameRepository.findByTitleContainingIgnoreCase(pGameTitle);

        if (gamesEntities.isEmpty())
            throw new GameNotFoundException("No games found with the name: " + pGameTitle);

        return IGameConverter.convertGamesEntitiesToGamesResponse(gamesEntities);
    }

    @Override
    @Transactional(readOnly = true)
    public GameResponse getGameById(UUID pGameId) throws GameNotFoundException {
        LOGGER.info("Getting game by ID: {}", pGameId);
        return IGameConverter.convertGameEntityToGameResponse(this.gameRepository.findById(pGameId)
                .orElseThrow(() -> new GameNotFoundException("Game not found for ID: " + pGameId)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GameResponse> getGamesByPage(int pPage, int pTotalPages) {
        LOGGER.info("Getting games for page: {} with total pages: {}", pPage, pTotalPages);
        Pageable pageable = PageRequest.of(pPage, pTotalPages, Sort.by("title").ascending());
        return IGameConverter.convertGamesEntitiesToPageGamesResponses(this.gameRepository.findAll(pageable));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GameResponse createGame(GameRequest pGameRequest) throws GameExistsException {
        LOGGER.info("Creating a new game with name: {}", pGameRequest.getTitle());

        Optional<GameEntity> optionalGameEntity = this.gameRepository.findByTitle(pGameRequest.getTitle());

        if (optionalGameEntity.isPresent()) {
            LOGGER.error("Game with name {} already exists", pGameRequest.getTitle());
            throw new GameExistsException("Game with name already exists");
        }

        GameEntity gameEntity = IGameConverter.convertGameRequestToGameEntity(pGameRequest);
        GameEntity savedGameEntity = this.gameRepository.save(gameEntity);
        LOGGER.info("Game created successfully with ID: {}", savedGameEntity.getId());

        return IGameConverter.convertGameEntityToGameResponse(savedGameEntity);
    }

    /**
     * Method to create games from the API Rawg.
     */
    public void createGamesFromApiRawg() throws GameExistsException {
        for (int gameId = 1; gameId <= TOTAL_GAMES; gameId++) {
            LOGGER.info("Processing game with ID: {}", gameId);

            GameRequest gameRequest = IGameConverter.convertGameDetailsFromApiRawg(gameId).block();

            if (gameRequest == null)
                continue;

            List<String> imageRequest = IGameConverter.convertImagesFromApiRawg(gameId).block();

            if (imageRequest == null)
                continue;

            gameRequest.setImages(imageRequest);
            this.createGame(gameRequest);

            LOGGER.info("Game with ID {} processed successfully", gameId);
        }
    }

    /**
     * Updates the fields of an existing GameEntity object with the values from a GameRequest object.
     *
     * @param pGameRequest       The GameRequest object containing the new values for the fields.
     * @param existingGameEntity The existing GameEntity object to be updated.
     * @return The updated GameEntity object with fields modified based on the GameRequest object.
     */
    private static GameEntity updateGameEntityFromRequest(GameRequest pGameRequest, GameEntity existingGameEntity) {
        LOGGER.info("Updating game entity from request: {}", pGameRequest.getId());
        existingGameEntity.setTitle(pGameRequest.getTitle());
        existingGameEntity.setDescription(pGameRequest.getDescription());
        existingGameEntity.setGenres(pGameRequest.getGenres());
        existingGameEntity.setPlatforms(pGameRequest.getPlatforms());
        existingGameEntity.setImages(pGameRequest.getImages());
        LOGGER.info("Game entity updated successfully: {}", existingGameEntity.getId());
        return existingGameEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GameResponse updateGame(UUID pGameId, GameRequest pGameRequest) throws GameNotFoundException {
        LOGGER.info("Updating game with ID: {}", pGameRequest.getId());

        Optional<GameEntity> existingOptionalGameEntity = this.gameRepository.findById(pGameId);

        if (existingOptionalGameEntity.isEmpty())
            throw new GameNotFoundException("Game not found for ID: " + pGameId);

        GameEntity gameEntity = updateGameEntityFromRequest(pGameRequest, existingOptionalGameEntity.get());

        GameEntity updatedGameEntity = this.gameRepository.save(gameEntity);

        LOGGER.info("Game updated successfully. Game : {}", updatedGameEntity);
        return IGameConverter.convertGameEntityToGameResponse(updatedGameEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteGameById(UUID pGameId) throws GameNotFoundException {
        LOGGER.info("Deleting game with ID: {}", pGameId);
        this.gameRepository.findById(pGameId)
                .orElseThrow(() -> new GameNotFoundException("Game not found for ID: " + pGameId));

        this.gameRepository.deleteById(pGameId);
    }
}