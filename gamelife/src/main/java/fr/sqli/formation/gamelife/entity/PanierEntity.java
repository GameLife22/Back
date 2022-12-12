package fr.sqli.formation.gamelife.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the panier database table.
 * 
 */
@Entity
@Table(name="panier")
@NamedQuery(name="PanierEntity.findAll", query="SELECT p FROM PanierEntity p")
public class PanierEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PanierPK id;

	@Column(nullable=false)
	private int quantite;

	//bi-directional many-to-one association to Commande
	@ManyToOne
	@JoinColumn(name="id_commande", nullable=false, insertable=false, updatable=false)
	private CommandeEntity commande;

	//bi-directional many-to-one association to Produit
	@ManyToOne
	@JoinColumn(name="id_produit", nullable=false, insertable=false, updatable=false)
	private ProduitEntity produit;

	public PanierEntity() {
	}

	public PanierPK getId() {
		return this.id;
	}

	public void setId(PanierPK id) {
		this.id = id;
	}

	public int getQuantite() {
		return this.quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public CommandeEntity getCommande() {
		return this.commande;
	}

	public void setCommande(CommandeEntity commande) {
		this.commande = commande;
	}

	public ProduitEntity getProduit() {
		return this.produit;
	}

	public void setProduit(ProduitEntity produit) {
		this.produit = produit;
	}

	/**
	 * Cette Fonction Permet de verifier si les parametres sont suprieur à 0
	 * puisque l'id et la quantitée commence de 1
	 * @param id
	 * @param quantite
	 * @throws IllegalArgumentException
	 */
	public static void validateAll(PanierPK id ,int quantite) throws Exception{
		if(id.getIdProduit() < 0  && id.getIdCommande() <0  && quantite < 0){
			throw new IllegalArgumentException("Erreur Id_commande || Id_Prroduit || Quantite");
		}
	}

	/**
	 * Cette Fonction Permet de verifier si les parametres sont suprieur à 0
	 * @param id
	 * @throws IllegalArgumentException
	 */
	public static void validateId(PanierPK id) throws Exception{
		if(id.getIdProduit() < 0  && id.getIdCommande() <0 ){
			throw new IllegalArgumentException("Erreur Id_commande || Id_Prroduit ");
		}
	}
}