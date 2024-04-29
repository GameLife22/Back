package fr.sqli.formation.gamelife.convertisseur;

import fr.sqli.formation.gamelife.dto.categorie.CategorieRequete;
import fr.sqli.formation.gamelife.dto.categorie.CategorieReponse;
import fr.sqli.formation.gamelife.entite.CategorieEntite;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public interface ICategorieConvertisseur {
    public static CategorieEntite convertirEnCategorieEntite(CategorieRequete pCategorieRequete) {
        var categorieEntity = new CategorieEntite();
        categorieEntity.setLibelle(pCategorieRequete.libelle());
        return categorieEntity;
    }

    public static Set<CategorieEntite> convertirEnCategoriesEntite(Set<CategorieRequete> pCategorieRequete) {
        return pCategorieRequete.stream()
                .map(ICategorieConvertisseur::convertirEnCategorieEntite)
                .collect(Collectors.toSet());
    }

    public static CategorieReponse convertirEnCategorieReponse(CategorieEntite pCategorieEntite) {
        return new CategorieReponse(pCategorieEntite.getId(), pCategorieEntite.getLibelle());
    }

    public static Set<CategorieReponse> convertirEnCategoriesReponse(Set<CategorieEntite> pCategorieEntities) {
        return pCategorieEntities.stream()
                .map(ICategorieConvertisseur::convertirEnCategorieReponse)
                .collect(Collectors.toSet());
    }
}