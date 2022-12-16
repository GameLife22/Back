package fr.sqli.formation.gamelife.dto.produit;

import fr.sqli.formation.gamelife.entity.ImageEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * Produit DTO
 */
public class ProduitDtoIn {

	private String categorie;

	private String texteDescriptif;

	private String detail;

	private String nom;

	private String plateforme;

	private BigDecimal prix;

	private int id_utilisateur;

	private int stock;
	private List<ImageEntity> images;

	public ProduitDtoIn(String categorie, String texteDescriptif, String detail, String nom, String plateforme, BigDecimal prix, int id_utilisateur, int stock, List<ImageEntity> images) {
		this.categorie = categorie;
		this.texteDescriptif = texteDescriptif;
		this.detail = detail;
		this.nom = nom;
		this.plateforme = plateforme;
		this.prix = prix;
		this.id_utilisateur = id_utilisateur;
		this.stock = stock;
		this.images = images;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public String getTexteDescriptif() {
		return texteDescriptif;
	}

	public void setTexteDescriptif(String texteDescriptif) {
		this.texteDescriptif = texteDescriptif;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPlateforme() {
		return plateforme;
	}

	public void setPlateforme(String plateforme) {
		this.plateforme = plateforme;
	}

	public BigDecimal getPrix() {
		return prix;
	}

	public void setPrix(BigDecimal prix) {
		this.prix = prix;
	}

	public int getId_utilisateur() {
		return id_utilisateur;
	}

	public void setId_utilisateur(int id_utilisateur) {
		this.id_utilisateur = id_utilisateur;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public List<ImageEntity> getImages() {
		return images;
	}

	public void setImages(List<ImageEntity> images) {
		this.images = images;
	}
}
