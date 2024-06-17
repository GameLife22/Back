package fr.sqli.formation.gamelife.utility.converter;

import fr.sqli.formation.gamelife.dto.request.GameRequest;
import fr.sqli.formation.gamelife.dto.response.GameResponse;
import fr.sqli.formation.gamelife.entity.GameEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interface for converting GameRequest and GameEntity objects to each other,
 * as well as converting lists of these objects.
 * Contains static methods for conversion operations.
 */
public interface IGameConverter {

    /**
     * Converts a GameRequest object to a GameEntity object.
     *
     * @param pGameRequest The GameRequest object to be converted
     * @return The converted GameEntity object
     */
    public static GameEntity convertGameRequestToGameEntity(GameRequest pGameRequest) {
        GameEntity gameEntity = new GameEntity();
        gameEntity.setId(pGameRequest.getId());
        gameEntity.setName(pGameRequest.getName());
        gameEntity.setDescription(pGameRequest.getDescription());
        gameEntity.setGenres(pGameRequest.getGenres());
        gameEntity.setPlatforms(pGameRequest.getPlatforms());
        gameEntity.setImages(pGameRequest.getImages());
        return gameEntity;
    }

    /**
     * Converts a GameEntity object to a GameResponse object.
     *
     * @param pGameEntity The GameEntity object to be converted
     * @return The converted GameResponse object
     */
    public static GameResponse convertGameEntityToGameResponse(GameEntity pGameEntity) {
        GameResponse gameResponse = new GameResponse();
        gameResponse.setId(pGameEntity.getId());
        gameResponse.setName(pGameEntity.getName());
        gameResponse.setDescription(pGameEntity.getDescription());
        gameResponse.setGenres(pGameEntity.getGenres());
        gameResponse.setPlatforms(pGameEntity.getPlatforms());
        gameResponse.setImages(pGameEntity.getImages());
        return gameResponse;
    }

    /**
     * Converts a Page of GameEntity objects to a Page of GameResponse objects.
     *
     * @param pGamesEntities The Page of GameEntity objects to be converted
     * @return The converted Page of GameResponse objects
     */
    public static Page<GameResponse> convertGamesEntitiesToPageGamesResponses(Page<GameEntity> pGamesEntities) {
        List<GameResponse> produitsReponses = pGamesEntities.stream()
                .map(IGameConverter::convertGameEntityToGameResponse)
                .toList();

        Pageable pageable = pGamesEntities.getPageable();
        return new PageImpl<>(produitsReponses, pageable, pGamesEntities.getTotalElements());
    }

    /**
     * Converts a list of GameEntity objects to a list of GameResponse objects.
     *
     * @param pGamesEntities The list of GameEntity objects to be converted
     * @return The converted list of GameResponse objects
     */
    public static List<GameResponse> convertGamesEntitiesToGamesResponse(List<GameEntity> pGamesEntities) {
        return pGamesEntities.stream()
                .map(IGameConverter::convertGameEntityToGameResponse)
                .toList();
    }
}