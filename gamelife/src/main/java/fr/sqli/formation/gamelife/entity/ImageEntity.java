package fr.sqli.formation.gamelife.entity;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the image database table.
 * 
 */
@Entity
@Table(name="image")
public class ImageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Lob
	private String description;

	@Lob
	private String image;

	private String titre;

	//bi-directional many-to-one association to Produit
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_produit")
	@JsonIgnore
	private ProduitEntity produit;

	public ImageEntity() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTitre() {
		return this.titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public ProduitEntity getProduit() {
		return this.produit;
	}

	public void setProduit(ProduitEntity produit) {
		this.produit = produit;
	}

}