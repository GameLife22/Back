package fr.sqli.formation.gamelife.dto.categorie;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.UUID;

/**
 * Classe représentant une demande de catégorie.
 *
 * Cette classe est utilisée pour créer une nouvelle catégorie en utilisant un identifiant unique (UUID) et un libellé non vide.
 *
 * Les instances de cette classe sont immuables, ce qui signifie qu'une fois créées, elles ne peuvent pas être modifiées.
 *
 * @param id L'identifiant unique de la catégorie.
 * @param libelle Le libellé de la catégorie, qui ne peut pas être vide.
 */
public record CategorieRequete(UUID id, @Size(max=25) @NotEmpty String libelle) {
}