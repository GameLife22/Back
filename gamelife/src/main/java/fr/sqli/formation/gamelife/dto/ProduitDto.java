package fr.sqli.formation.gamelife.dto;

import java.math.BigDecimal;

public class ProduitDto {

	private int id;

	private String categorie;

	private String detail;

	private String nom;

	private String plateforme;

	private BigDecimal prix;

	// private List<ImageEntity> images;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategorie() {
		return this.categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPlateforme() {
		return this.plateforme;
	}

	public void setPlateforme(String plateforme) {
		this.plateforme = plateforme;
	}

	public BigDecimal getPrix() {
		return this.prix;
	}

	public void setPrix(BigDecimal prix) {
		this.prix = prix;
	}

//	public List<ImageEntity> getImages() {
//		return this.images;
//	}
//
//	public void setImages(List<ImageEntity> images) {
//		this.images = images;
//	}

}
