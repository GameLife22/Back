package fr.sqli.formation.gamelife.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ItemPanier database table.
 * 
 */
@Embeddable
public class ItemPanierPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_panier", insertable=false, updatable=false, unique=true, nullable=false)
	private int idPanier;

	@Column(name="id_produit", insertable=false, updatable=false, unique=true, nullable=false)
	private int idProduit;

	public ItemPanierPK() {
	}

	public int getIdPanier() {
		return idPanier;
	}

	public void setIdPanier(int idPanier) {
		this.idPanier = idPanier;
	}

	public int getIdProduit() {
		return this.idProduit;
	}
	public void setIdProduit(int idProduit) {
		this.idProduit = idProduit;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ItemPanierPK)) {
			return false;
		}
		ItemPanierPK castOther = (ItemPanierPK)other;
		return 
			(this.idPanier == castOther.idPanier)
			&& (this.idProduit == castOther.idProduit);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idPanier;
		hash = hash * prime + this.idProduit;
		
		return hash;
	}
}