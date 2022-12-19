package fr.sqli.formation.gamelife.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ItemPanier database table.
 * 
 */
@Entity
@Table(name="item_panier")
@NamedQuery(name="ItemPanierEntity.findAll", query="SELECT p FROM ItemPanierEntity p")
public class ItemPanierEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ItemPanierPK id;

	@Column(nullable=false)
	private int quantite;

	//bi-directional many-to-one association to Commande
	@ManyToOne
	@JoinColumn(name="id_panier", nullable=false, insertable=false, updatable=false)
	private PanierEntity panier;

	//bi-directional many-to-one association to Produit
	@ManyToOne
	@JoinColumn(name="id_produit", nullable=false, insertable=false, updatable=false)
	private ProduitEntity produit;

	public ItemPanierEntity() {
	}

	public ItemPanierPK getId() {
		return this.id;
	}

	public void setId(ItemPanierPK id) {
		this.id = id;
	}

	public int getQuantite() {
		return this.quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public PanierEntity getPanier() {
		return panier;
	}

	public void setPanier(PanierEntity panier) {
		this.panier = panier;
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
	public static void validateAll(ItemPanierPK id ,int quantite) throws Exception{
		if(id.getIdProduit() < 0  && id.getIdPanier() <0  && quantite < 0){
			throw new IllegalArgumentException("Erreur Id_commande || Id_Prroduit || Quantite");
		}
	}

	/**
	 * Cette Fonction Permet de verifier si les parametres sont suprieur à 0
	 * @param id
	 * @throws IllegalArgumentException
	 */
	public static void validateId(ItemPanierPK id) throws Exception{
		if(id.getIdProduit() < 0  && id.getIdPanier() <0 ){
			throw new IllegalArgumentException("Erreur Id_commande || Id_Prroduit ");
		}
	}
}