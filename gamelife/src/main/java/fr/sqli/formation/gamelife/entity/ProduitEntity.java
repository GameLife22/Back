package fr.sqli.formation.gamelife.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the produit database table.
 * 
 */
@Entity
@Table(name="produit")
public class ProduitEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String categorie;

	@Lob
	private String detail;

	private String nom;

	private String plateforme;

	private BigDecimal prix;

	private int stock;

	@Lob
	@Column(name="texte_descriptif")
	private String texteDescriptif;

	//bi-directional many-to-one association to Image
	@OneToMany(mappedBy="produit")
	private List<ImageEntity> images;

	//bi-directional many-to-one association to Panier
	@OneToMany(mappedBy="produit")
	private List<PanierEntity> paniers;

	//bi-directional many-to-one association to Utilisateur
	@ManyToOne
	@JoinColumn(name="id_utilisateur")
	private UtilisateurEntity utilisateur;

	public ProduitEntity() {
	}

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

	public int getStock() {
		return this.stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getTexteDescriptif() {
		return this.texteDescriptif;
	}

	public void setTexteDescriptif(String texteDescriptif) {
		this.texteDescriptif = texteDescriptif;
	}

	public List<ImageEntity> getImages() {
		return this.images;
	}

	public void setImages(List<ImageEntity> images) {
		this.images = images;
	}

	public ImageEntity addImage(ImageEntity image) {
		getImages().add(image);
		image.setProduit(this);

		return image;
	}

	public ImageEntity removeImage(ImageEntity image) {
		getImages().remove(image);
		image.setProduit(null);

		return image;
	}

	public List<PanierEntity> getPaniers() {
		return this.paniers;
	}

	public void setPaniers(List<PanierEntity> paniers) {
		this.paniers = paniers;
	}

	public PanierEntity addPanier(PanierEntity panier) {
		getPaniers().add(panier);
		panier.setProduit(this);

		return panier;
	}

	public PanierEntity removePanier(PanierEntity panier) {
		getPaniers().remove(panier);
		panier.setProduit(null);

		return panier;
	}

	public UtilisateurEntity getUtilisateur() {
		return this.utilisateur;
	}

	public void setUtilisateur(UtilisateurEntity utilisateur) {
		this.utilisateur = utilisateur;
	}

}