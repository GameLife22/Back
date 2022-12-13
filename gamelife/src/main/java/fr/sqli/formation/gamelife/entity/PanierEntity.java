package fr.sqli.formation.gamelife.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the commande database table.
 * 
 */
@Entity
@Table(name="panier")
public class PanierEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name="date")
	private Date date;

	private byte etat;

	//bi-directional many-to-one association to Utilisateur
	@ManyToOne
	@JoinColumn(name="id_utilisateur")
	private UtilisateurEntity utilisateur;

	//bi-directional many-to-one association to ItemPanier
	@OneToMany(mappedBy="panier")
	private List<ItemPanierEntity> ItemPaniers;

	public PanierEntity() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public byte getEtat() {
		return this.etat;
	}

	public void setEtat(byte etat) {
		this.etat = etat;
	}

	public UtilisateurEntity getUtilisateur() {
		return this.utilisateur;
	}

	public void setUtilisateur(UtilisateurEntity utilisateur) {
		this.utilisateur = utilisateur;
	}

	public List<ItemPanierEntity> getItemPaniers() {
		return this.ItemPaniers;
	}

	public void setItemPaniers(List<ItemPanierEntity> ItemPaniers) {
		this.ItemPaniers = ItemPaniers;
	}

	public ItemPanierEntity addItemPanier(ItemPanierEntity ItemPanier) {
		getItemPaniers().add(ItemPanier);
		ItemPanier.setPanier(this);

		return ItemPanier;
	}

	public ItemPanierEntity removeItemPanier(ItemPanierEntity ItemPanier) {
		getItemPaniers().remove(ItemPanier);
		ItemPanier.setPanier(null);

		return ItemPanier;
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