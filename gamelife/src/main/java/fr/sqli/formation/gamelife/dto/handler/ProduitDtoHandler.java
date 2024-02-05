package fr.sqli.formation.gamelife.dto.handler;

import fr.sqli.formation.gamelife.controler.ProduitRevendeurRestController;
import fr.sqli.formation.gamelife.dto.in.ProduitDtoIn;
import fr.sqli.formation.gamelife.dto.out.ProduitDtoOut;
import fr.sqli.formation.gamelife.entity.ProduitEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public interface ProduitDtoHandler {
    public static final Logger LOG = LoggerFactory.getLogger(ProduitRevendeurRestController.class);

    public static ProduitEntity toEntity(ProduitDtoIn pProduitDtoIn) {
        var result = new ProduitEntity();
        result.setNom(pProduitDtoIn.getNom());
        result.setDescription(pProduitDtoIn.getDescription());
        result.setCategorie(pProduitDtoIn.getCategorie());
        result.setPlateforme(pProduitDtoIn.getPlateforme());
        result.setEtat(pProduitDtoIn.getEtat());
        return result;
    }

    public static ProduitDtoOut dtoOutFromEntity(ProduitEntity pProduitEntity) {
        var result = new ProduitDtoOut();
        result.setNom(pProduitEntity.getNom());
        result.setDescription(pProduitEntity.getDescription());
        result.setCategorie(pProduitEntity.getCategorie());
        result.setPlateforme(pProduitEntity.getPlateforme());
        result.setEtat(pProduitEntity.getEtat());
        return result;
    }

    public static List<ProduitDtoOut> dtosOutFromEntity(List<ProduitEntity> pProduitEntities) {
        return pProduitEntities.stream()
                .map(ProduitDtoHandler::dtoOutFromEntity)
                .collect(Collectors.toList());
    }

    public static ProduitDtoIn dtoInFromEntity(ProduitEntity pProduitEntity) {
        var result = new ProduitDtoIn();
        result.setNom(pProduitEntity.getNom());
        result.setDescription(pProduitEntity.getDescription());
        result.setCategorie(pProduitEntity.getCategorie());
        result.setPlateforme(pProduitEntity.getPlateforme());
        result.setEtat(pProduitEntity.getEtat());
        return result;
    }
}