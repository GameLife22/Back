package fr.sqli.formation.gamelife.dto.handler;

import fr.sqli.formation.gamelife.dto.in.ProduitDtoIn;
import fr.sqli.formation.gamelife.dto.out.ProduitDtoOut;
import fr.sqli.formation.gamelife.entity.ImageEntity;
import fr.sqli.formation.gamelife.entity.ProduitEntity;
import fr.sqli.formation.gamelife.enums.Categorie;
import fr.sqli.formation.gamelife.enums.Plateforme;
import fr.sqli.formation.gamelife.ex.ParameterException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public interface ProduitDtoHandler {
    public static ProduitEntity toEntity(ProduitDtoIn pProduitDtoIn) throws ParameterException {
        var result = new ProduitEntity();

        result.setNom(pProduitDtoIn.getNom());
        result.setDescription(pProduitDtoIn.getDescription());
        result.setCategorie(Categorie.fromString(pProduitDtoIn.getCategorie()));
        result.setPlateforme(Plateforme.fromString(pProduitDtoIn.getPlateforme()));
        result.setEtat(pProduitDtoIn.getEtat());
        result.setImages(pProduitDtoIn.getImages());

        return result;
    }

    public static ProduitDtoOut dtoOutFromEntity(ProduitEntity pProduitEntity) throws ParameterException {
        var result = new ProduitDtoOut();

        result.setId(pProduitEntity.getId());
        result.setNom(pProduitEntity.getNom());
        result.setDescription(pProduitEntity.getDescription());
        result.setCategorie(Categorie.fromString(pProduitEntity.getCategorie()));
        result.setPlateforme(Plateforme.fromString(pProduitEntity.getPlateforme()));
        result.setEtat(pProduitEntity.getEtat());
        result.setImages(pProduitEntity.getImages());

        return result;
    }

    public static List<ProduitDtoOut> dtosOutFromEntity(List<ProduitEntity> pProduitEntities) {
        List<ProduitDtoOut> produits = new ArrayList<>();

        if (pProduitEntities != null && !pProduitEntities.isEmpty()) {
            pProduitEntities.forEach(produit -> {
                try {
                    produits.add(dtoOutFromEntity(produit));
                } catch (ParameterException pE) {
                    pE.getMessage(); // TODO: modifier
                }
            });
        }

        return produits;
    }
}