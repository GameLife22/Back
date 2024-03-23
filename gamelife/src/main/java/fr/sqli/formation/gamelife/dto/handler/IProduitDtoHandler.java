package fr.sqli.formation.gamelife.dto.handler;

import fr.sqli.formation.gamelife.dto.in.ProduitDtoIn;
import fr.sqli.formation.gamelife.dto.out.ProduitDtoOut;
import fr.sqli.formation.gamelife.entity.ProduitEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public interface IProduitDtoHandler {
    public static ProduitEntity toEntity(ProduitDtoIn pProduitDtoIn) {;
        return null;
    }

    public static ProduitDtoOut dtoOutFromEntity(ProduitEntity pProduitEntity) {
        return null;
    }

    public static List<ProduitDtoOut> dtoOutFromEntities(List<ProduitEntity> pProduitEntities) {
        return pProduitEntities.stream()
                .map(IProduitDtoHandler::dtoOutFromEntity)
                .collect(Collectors.toList());
    }
}