package fr.sqli.formation.gamelife.dto.handler;

import fr.sqli.formation.gamelife.dto.in.CategorieDtoIn;
import fr.sqli.formation.gamelife.dto.out.CategorieDtoOut;
import fr.sqli.formation.gamelife.entity.CategorieEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ICategorieDtoHandler {
    public static CategorieEntity toEntity(CategorieDtoIn pCategorieDtoIn) {
        var plateformeEntity = new CategorieEntity();
        plateformeEntity.setId(UUID.randomUUID());
        plateformeEntity.setLibelle(pCategorieDtoIn.getLibelle());
        return plateformeEntity;
    }

    public static CategorieDtoOut dtoOutFromEntity(CategorieEntity pCategorieEntity) {
        var plateformeDtoOut = new CategorieDtoOut();
        plateformeDtoOut.setId(pCategorieEntity.getId());
        plateformeDtoOut.setLibelle(pCategorieEntity.getLibelle());
        return plateformeDtoOut;
    }

    public static List<CategorieDtoOut> dtoOutFromEntities(List<CategorieEntity> pCategorieEntities) {
        return pCategorieEntities.stream()
                .map(ICategorieDtoHandler::dtoOutFromEntity)
                .collect(Collectors.toList());
    }
}
