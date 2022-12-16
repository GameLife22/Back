package fr.sqli.formation.gamelife.dto.produit;

import fr.sqli.formation.gamelife.dto.produit.ProduitDtoIn;
import fr.sqli.formation.gamelife.dto.produit.ProduitDtoOut;
import fr.sqli.formation.gamelife.entity.ProduitEntity;
import fr.sqli.formation.gamelife.entity.UtilisateurEntity;

import java.util.Optional;

public class ProduitDtoHandler {

	public static ProduitDtoOut fromEntity(ProduitEntity entity) {
		var produitDtoOut = new ProduitDtoOut();
		produitDtoOut.setCategorie(entity.getCategorie());
		produitDtoOut.setTexteDescriptif(entity.getTexteDescriptif());
		produitDtoOut.setNom(entity.getNom());
		produitDtoOut.setDetail(entity.getDetail());
		produitDtoOut.setPlateforme(entity.getPlateforme());
		produitDtoOut.setPrix(entity.getPrix());
		produitDtoOut.setId(entity.getId());
		produitDtoOut.setImages(entity.getImages());
		return produitDtoOut;
	}

	public static ProduitEntity fromDto(ProduitDtoIn nouveauProduit, Optional<UtilisateurEntity> utilisateur) {
		ProduitEntity produitEntity = new ProduitEntity();
		produitEntity.setNom(nouveauProduit.getNom());
		produitEntity.setCategorie(nouveauProduit.getCategorie());
		produitEntity.setDetail(nouveauProduit.getDetail());
		produitEntity.setPlateforme(nouveauProduit.getPlateforme());
		produitEntity.setPrix(nouveauProduit.getPrix());
		produitEntity.setStock(nouveauProduit.getStock());
		produitEntity.setTexteDescriptif(nouveauProduit.getTexteDescriptif());
		produitEntity.setImages(nouveauProduit.getImages());
		produitEntity.setUtilisateur(utilisateur.get());
		produitEntity.setEtat(1);
		return produitEntity;
	}

	public static ProduitEntity fromDto2(ProduitDtoIn2 nouveauProduit, Optional<UtilisateurEntity> utilisateur) {
		ProduitEntity produitEntity = new ProduitEntity();
		produitEntity.setId(nouveauProduit.getId());
		produitEntity.setNom(nouveauProduit.getNom());
		produitEntity.setCategorie(nouveauProduit.getCategorie());
		produitEntity.setDetail(nouveauProduit.getDetail());
		produitEntity.setPlateforme(nouveauProduit.getPlateforme());
		produitEntity.setPrix(nouveauProduit.getPrix());
		produitEntity.setStock(nouveauProduit.getStock());
		produitEntity.setTexteDescriptif(nouveauProduit.getTexteDescriptif());
		produitEntity.setImages(nouveauProduit.getImages());
		produitEntity.setUtilisateur(utilisateur.get());
		produitEntity.setEtat(nouveauProduit.getEtat()); // attention
		return produitEntity;
	}
}
