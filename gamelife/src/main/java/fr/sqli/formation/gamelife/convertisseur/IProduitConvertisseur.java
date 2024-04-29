package fr.sqli.formation.gamelife.convertisseur;

import fr.sqli.formation.gamelife.dto.produit.ProduitRequete;
import fr.sqli.formation.gamelife.dto.produit.ProduitReponse;
import fr.sqli.formation.gamelife.entite.ProduitEntite;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public interface IProduitConvertisseur {
    public static ProduitEntite toEntity(ProduitRequete pProduitRequete) {
        var produitEntity = new ProduitEntite();
        produitEntity.setNom(pProduitRequete.getNom());
        produitEntity.setDescription(pProduitRequete.getDescription());
        produitEntity.setCategories(ICategorieConvertisseur.convertirEnCategoriesEntite(pProduitRequete.recupererCategories()));
        produitEntity.setPlateformes(IPlateformeConvertisseur.toEntities(pProduitRequete.getPlateformes()));
        produitEntity.setImages(IImageConvertisseur.toEntities(pProduitRequete.getImages(), produitEntity));
        return produitEntity;
    }

    public static ProduitEntite entityFromDtoIn(ProduitRequete pProduitRequete, ProduitEntite pProduitEntite) {
        pProduitEntite.setNom(pProduitRequete.getNom());
        pProduitEntite.setDescription(pProduitRequete.getDescription());
        return pProduitEntite;
    }

    public static ProduitReponse dtoOutFromEntity(ProduitEntite pProduitEntite) {
        var produitDtoOut = new ProduitReponse();
        produitDtoOut.setId(pProduitEntite.getId());
        produitDtoOut.setNom(pProduitEntite.getNom());
        produitDtoOut.setDescription(pProduitEntite.getDescription());
        produitDtoOut.setCategories(ICategorieConvertisseur.convertirEnCategoriesReponse(pProduitEntite.recupererCategories()));
        produitDtoOut.setPlateformes(IPlateformeConvertisseur.dtoOutFromEntities(pProduitEntite.getPlateformes()));
        produitDtoOut.setImages(IImageConvertisseur.dtoOutFromEntities(pProduitEntite.getImages()));
        return produitDtoOut;
    }

    public static List<ProduitReponse> dtoOutFromEntities(List<ProduitEntite> pProduitEntities) {
        return pProduitEntities.stream()
                .map(IProduitConvertisseur::dtoOutFromEntity)
                .collect(Collectors.toList());
    }
}