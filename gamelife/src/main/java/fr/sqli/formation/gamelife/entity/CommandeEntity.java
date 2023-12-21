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
@Table(name="commande")
public class CommandeEntity implements Serializable {
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
	@OneToMany(mappedBy= "commande")
	private List<ItemCommandeEntity> ItemPaniers;

	public CommandeEntity() {
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

	public List<ItemCommandeEntity> getItemPaniers() {
		return this.ItemPaniers;
	}

	public void setItemPaniers(List<ItemCommandeEntity> ItemPaniers) {
		this.ItemPaniers = ItemPaniers;
	}

	public ItemCommandeEntity addItemPanier(ItemCommandeEntity ItemPanier) {
		getItemPaniers().add(ItemPanier);
		ItemPanier.setCommande(this);

		return ItemPanier;
	}

	public ItemCommandeEntity removeItemPanier(ItemCommandeEntity ItemPanier) {
		getItemPaniers().remove(ItemPanier);
		ItemPanier.setCommande(null);

		return ItemPanier;
	}


}