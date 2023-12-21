package fr.sqli.formation.gamelife.dto;

import fr.sqli.formation.gamelife.entity.ProduitEntity;
import fr.sqli.formation.gamelife.entity.ProduitRevendeurEntity;

public class ProduitDtoHandler {

	public static ProduitDto fromEntity(ProduitEntity entity) {
		var resu = new ProduitDto();
		resu.setCategorie(entity.getCategorie());
		resu.setTexteDescriptif(entity.getTexteDescriptif());
		resu.setNom(entity.getNom());
		resu.setDetail(entity.getDetail());
		resu.setPlateforme(entity.getPlateforme());
		resu.setId(entity.getId());
		resu.setImages(entity.getImages());
		return resu;
	}

	public static ProduitEntity fromDto(ProduitDto dto) {
		// Inchallah un jour faut coder

		return null;
	}

}
