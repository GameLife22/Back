package fr.sqli.formation.gamelife.dto.handler;

import fr.sqli.formation.gamelife.dto.in.ProduitDtoIn;
import fr.sqli.formation.gamelife.dto.out.ProduitDtoOut;
import fr.sqli.formation.gamelife.entity.ImageEntity;
import fr.sqli.formation.gamelife.entity.ProduitEntity;
import fr.sqli.formation.gamelife.enums.Categorie;
import fr.sqli.formation.gamelife.enums.Plateforme;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public interface ProduitDtoHandler {
    public static ProduitEntity toEntity(ProduitDtoIn pProduitDtoIn) {
        var produitEntity = new ProduitEntity();

        if(pProduitDtoIn.getId() != null) {
            produitEntity.setId(pProduitDtoIn.getId());
        }

        produitEntity.setNom(pProduitDtoIn.getNom());
        produitEntity.setDescription(pProduitDtoIn.getDescription());
        produitEntity.setCategorie(Categorie.fromString(pProduitDtoIn.getCategorie()));
        produitEntity.setPlateforme(Plateforme.fromString(pProduitDtoIn.getPlateforme()));
        produitEntity.setEtat(pProduitDtoIn.getEtat());

        if(pProduitDtoIn.getImages() != null) {
            produitEntity.setImages(ImageDtoHandler.toEntities(pProduitDtoIn.getImages()));
        }

        if(pProduitDtoIn.getIdImages() != null) {
            List<ImageEntity> imagesEntity = pProduitDtoIn.getIdImages().stream()
                    .map(idImage -> new ImageEntity(idImage))
                    .collect(Collectors.toList());
            produitEntity.setImages(imagesEntity);
        }

        return produitEntity;
    }

    public static ProduitDtoOut dtoOutFromEntity(ProduitEntity pProduitEntity) {
        var produitDtoOut = new ProduitDtoOut();
        produitDtoOut.setId(pProduitEntity.getId());
        produitDtoOut.setNom(pProduitEntity.getNom());
        produitDtoOut.setDescription(pProduitEntity.getDescription());
        produitDtoOut.setCategorie(pProduitEntity.getCategorie());
        produitDtoOut.setPlateforme(pProduitEntity.getPlateforme());
        produitDtoOut.setEtat(pProduitEntity.getEtat());
        produitDtoOut.setImages(ImageDtoHandler.dtoOutFromEntities(pProduitEntity, pProduitEntity.getImages()));

        List<Integer> idImages = pProduitEntity.getImages().stream()
                .map(ImageEntity::getId)
                .collect(Collectors.toList());

        produitDtoOut.setIdImages(idImages);
        return produitDtoOut;
    }

    public static List<ProduitDtoOut> dtoOutFromEntities(List<ProduitEntity> pProduitEntities) {
        return pProduitEntities.stream()
                .map(ProduitDtoHandler::dtoOutFromEntity)
                .collect(Collectors.toList());
    }
}