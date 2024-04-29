package fr.sqli.formation.gamelife.service.categorie;

import fr.sqli.formation.gamelife.dto.categorie.CategorieRequete;
import fr.sqli.formation.gamelife.dto.categorie.CategorieReponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

/**
 * Interface pour le service de catégorie.
 */
@Transactional
public interface ICategorieService {
    public CategorieReponse recupererCategorie(UUID pCategorieId);
    public Set<CategorieReponse> recupererCategories();
    public CategorieReponse creerCategorie(CategorieRequete pCategorieRequete);
    public CategorieReponse modifierCategorie(UUID pCategorieId, CategorieRequete pCategorieRequete);
    public void supprimerCategorie(UUID pCategorieId);
}
