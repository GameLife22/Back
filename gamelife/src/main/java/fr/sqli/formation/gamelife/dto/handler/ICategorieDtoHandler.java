package fr.sqli.formation.gamelife.dto.handler;

import fr.sqli.formation.gamelife.dto.in.CategorieDtoIn;
import fr.sqli.formation.gamelife.dto.out.CategorieDtoOut;
import fr.sqli.formation.gamelife.entity.CategorieEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public interface ICategorieDtoHandler {
    public static CategorieEntity toEntity(CategorieDtoIn pCategorieDtoIn) {
        var categorieEntity = new CategorieEntity();
        categorieEntity.setLibelle(pCategorieDtoIn.getLibelle());
        return categorieEntity;
    }
    public static CategorieEntity entityFromDtoIn(CategorieDtoIn pCategorieDtoIn, CategorieEntity pCategorieEntity) {
        pCategorieEntity.setLibelle(pCategorieDtoIn.getLibelle());
        return pCategorieEntity;
    }

    public static CategorieDtoOut dtoOutFromEntity(CategorieEntity pCategorieEntity) {
        var categorieDtoOut = new CategorieDtoOut();
        categorieDtoOut.setId(pCategorieEntity.getId());
        categorieDtoOut.setLibelle(pCategorieEntity.getLibelle());
        return categorieDtoOut;
    }

    public static List<CategorieDtoOut> dtoOutFromEntities(List<CategorieEntity> pCategorieEntities) {
        return pCategorieEntities.stream()
                .map(ICategorieDtoHandler::dtoOutFromEntity)
                .collect(Collectors.toList());
    }
}