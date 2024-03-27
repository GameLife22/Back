package fr.sqli.formation.gamelife.dto.handler;

import fr.sqli.formation.gamelife.dto.in.ProduitDtoIn;
import fr.sqli.formation.gamelife.dto.out.ProduitDtoOut;
import fr.sqli.formation.gamelife.entity.ProduitEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public interface IProduitDtoHandler {
    public static ProduitEntity toEntity(ProduitDtoIn pProduitDtoIn) {
        var produitEntity = new ProduitEntity();
        produitEntity.setNom(pProduitDtoIn.getNom());
        produitEntity.setDescription(pProduitDtoIn.getDescription());
        produitEntity.setCategorie(ICategorieDtoHandler.toEntity(pProduitDtoIn.getCategorie()));
        produitEntity.setPlateforme(IPlateformeDtoHandler.toEntity(pProduitDtoIn.getPlateforme()));
        produitEntity.setImages(IImageDtoHandler.toEntities(pProduitDtoIn.getImages()));
        produitEntity.setEtat(pProduitDtoIn.getEtat());
        return produitEntity;
    }

    public static ProduitEntity entityFromDtoIn(ProduitDtoIn pProduitDtoIn, ProduitEntity pProduitEntity) {
        pProduitEntity.setNom(pProduitDtoIn.getNom());
        pProduitEntity.setDescription(pProduitDtoIn.getDescription());
        pProduitEntity.setEtat(pProduitDtoIn.getEtat());
        return pProduitEntity;
    }

    public static ProduitDtoOut dtoOutFromEntity(ProduitEntity pProduitEntity) {
        var produitDtoOut = new ProduitDtoOut();
        produitDtoOut.setId(pProduitEntity.getId());
        produitDtoOut.setNom(pProduitEntity.getNom());
        produitDtoOut.setDescription(pProduitEntity.getDescription());
        produitDtoOut.setCategorie(ICategorieDtoHandler.dtoOutFromEntity(pProduitEntity.getCategorie()));
        produitDtoOut.setPlateforme(IPlateformeDtoHandler.dtoOutFromEntity(pProduitEntity.getPlateforme()));
        produitDtoOut.setImages(IImageDtoHandler.dtoOutFromEntities(pProduitEntity.getImages()));
        produitDtoOut.setEtat(pProduitEntity.getEtat());
        return produitDtoOut;
    }

    public static List<ProduitDtoOut> dtoOutFromEntities(List<ProduitEntity> pProduitEntities) {
        return pProduitEntities.stream()
                .map(IProduitDtoHandler::dtoOutFromEntity)
                .collect(Collectors.toList());
    }
}