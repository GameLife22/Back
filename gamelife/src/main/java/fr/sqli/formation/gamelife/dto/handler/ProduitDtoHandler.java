package fr.sqli.formation.gamelife.dto.handler;

import fr.sqli.formation.gamelife.dto.in.ProduitDtoIn;
import fr.sqli.formation.gamelife.dto.out.ProduitDtoOut;
import fr.sqli.formation.gamelife.entity.ProduitEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public interface ProduitDtoHandler {
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
        result.setId(pProduitEntity.getId());
        result.setNom(pProduitEntity.getNom());
        result.setDescription(pProduitEntity.getDescription());
        result.setCategorie(pProduitEntity.getCategorie());
        result.setPlateforme(pProduitEntity.getPlateforme());
        result.setEtat(pProduitEntity.getEtat());
        return result;
    }

    public static List<ProduitDtoOut> dtosOutFromEntity(List<ProduitEntity> pProduitEntities) {
        List<ProduitDtoOut> produits = new ArrayList<>();
        if (pProduitEntities != null && !pProduitEntities.isEmpty()) {
            pProduitEntities.forEach(produit -> produits.add(dtoOutFromEntity(produit)));
        }
        return produits;
    }
}