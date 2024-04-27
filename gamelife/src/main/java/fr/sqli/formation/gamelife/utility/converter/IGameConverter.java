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
        gameEntity.setId(pGameRequest.id());
        gameEntity.setName(pGameRequest.name());
        gameEntity.setDescription(pGameRequest.description());
        gameEntity.setGenres(pGameRequest.genres());
        gameEntity.setPlatforms(pGameRequest.platforms());
        gameEntity.setImages(pGameRequest.images());
        return gameEntity;
    }

    /**
     * Converts a GameEntity object to a GameResponse object.
     *
     * @param pGameEntity The GameEntity object to be converted
     * @return The converted GameResponse object
     */
    public static GameResponse convertGameEntityToGameResponse(GameEntity pGameEntity) {
        return new GameResponse(pGameEntity.getId(), pGameEntity.getName(), pGameEntity.getDescription(),
                pGameEntity.getGenres(),
                pGameEntity.getPlatforms(),
                pGameEntity.getImages());
    }

    /**
     * Converts a Page of GameEntity objects to a Page of GameResponse objects.
     *
     * @param pGamesEntities The Page of GameEntity objects to be converted
     * @return The converted Page of GameResponse objects
     */
    public static Page<GameResponse> convertGamesEntitiesToPageGameResponse(Page<GameEntity> pGamesEntities) {
        List<GameResponse> produitReponse = pGamesEntities.stream()
                .map(IGameConverter::convertGameEntityToGameResponse)
                .toList();

        Pageable pageable = pGamesEntities.getPageable();
        return new PageImpl<>(produitReponse, pageable, pGamesEntities.getTotalElements());
    }
}