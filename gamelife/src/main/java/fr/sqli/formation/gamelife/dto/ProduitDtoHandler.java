package fr.sqli.formation.gamelife.dto;

import fr.sqli.formation.gamelife.entity.ProduitEntity;

public class ProduitDtoHandler {

	public static ProduitDto fromEntity(ProduitEntity entity) {
		var resu = new ProduitDto();
		resu.setCategorie(entity.getCategorie());
		resu.setNom(entity.getNom());
		resu.setPlateforme(entity.getPlateforme());
		resu.setId(entity.getId());
		return resu;
	}
}
