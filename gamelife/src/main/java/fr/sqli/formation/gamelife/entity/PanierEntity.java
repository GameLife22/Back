package fr.sqli.formation.gamelife.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.ArrayList;
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
	@OneToMany(mappedBy="panier", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<ItemPanierEntity> itemPaniers;

	public ItemPanierEntity addItemPanier(ItemPanierEntity itemPaniers) {
		getItemPaniers().add(itemPaniers);
		itemPaniers.setPanier(this);

		return itemPaniers;
	}

	public ItemPanierEntity removeItemPanier(ItemPanierEntity itemPaniers) {
		getItemPaniers().remove(itemPaniers);
		itemPaniers.setPanier(null);

		return itemPaniers;
	}
	public PanierEntity() {
		this.itemPaniers = new ArrayList<>();

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
		return this.itemPaniers;
	}
	public void setItemPaniers(List<ItemPanierEntity> ItemPaniers) {
		this.itemPaniers = ItemPaniers;
	}




}