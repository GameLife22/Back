package fr.sqli.formation.gamelife.dto;

import fr.sqli.formation.gamelife.entity.ProduitEntity;

public class ProduitDtoHandler {

	public static ProduitDto fromEntity(ProduitEntity entity) {
		var resu = new ProduitDto();
		resu.setCategorie(entity.getCategorie());
		resu.setNom(entity.getNom());
		resu.setDetail(entity.getDetail());
		resu.setPlateforme(entity.getPlateforme());
		resu.setPrix(entity.getPrix());
		resu.setId(entity.getId());
		return resu;
	}

	public static ProduitEntity fromDto(ProduitDto dto) {
		// Inchallah un jour faut coder

		return null;
	}

}
