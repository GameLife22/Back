package fr.sqli.formation.gamelife.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the panier database table.
 * 
 */
@Embeddable
public class PanierPKEntity implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_commande", insertable=false, updatable=false, unique=true, nullable=false)
	private int idCommande;

	@Column(name="id_produit", insertable=false, updatable=false, unique=true, nullable=false)
	private int idProduit;

	public PanierPKEntity() {
	}
	public int getIdCommande() {
		return this.idCommande;
	}
	public void setIdCommande(int idCommande) {
		this.idCommande = idCommande;
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
		if (!(other instanceof PanierPKEntity)) {
			return false;
		}
		PanierPKEntity castOther = (PanierPKEntity)other;
		return 
			(this.idCommande == castOther.idCommande)
			&& (this.idProduit == castOther.idProduit);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idCommande;
		hash = hash * prime + this.idProduit;
		
		return hash;
	}
}