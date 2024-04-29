package fr.sqli.formation.gamelife.dto.categorie;

import java.util.UUID;

/**
 * Cette classe représente une réponse de catégorie.
 *
 * @param pCategorieId      L'identifiant de la catégorie.
 * @param pLibelle Le libellé de la catégorie.
 */
public record CategorieReponse(UUID pCategorieId, String pLibelle) {
}