package fr.sqli.formation.gamelife.utility.converter;

import fr.sqli.formation.gamelife.dto.response.GameResponse;
import fr.sqli.formation.gamelife.entity.GameEntity;

import java.util.List;
import java.util.stream.Collectors;

public interface ISellerConverter {

    public static GameEntity entityFromDtoOut(GameResponse pProduitReponse) {
        var produitEntity = new GameEntity();
        produitEntity.setId(pProduitReponse.getId());
        produitEntity.setTitle(pProduitReponse.getTitle());
        produitEntity.setDescription(pProduitReponse.getDescription());
        return produitEntity;
    }

    public static List<GameEntity> entitiesFromDtoOut(List<GameResponse> pProduitReponses) {
        return pProduitReponses.stream()
                .map(ISellerConverter::entityFromDtoOut)
                .collect(Collectors.toList());
    }
}
