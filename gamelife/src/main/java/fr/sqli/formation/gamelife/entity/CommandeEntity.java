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

public class CommandeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name="date_commande")
	private Date dateCommande;

	private byte etat;

	//bi-directional many-to-one association to Utilisateur
	@ManyToOne
	@JoinColumn(name="id_utilisateur")
	private UtilisateurEntity utilisateur;

	//bi-directional many-to-one association to Panier
	@OneToMany(mappedBy="commande")
	private List<PanierEntity> paniers;

	public CommandeEntity() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateCommande() {
		return this.dateCommande;
	}

	public void setDateCommande(Date dateCommande) {
		this.dateCommande = dateCommande;
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

	public List<PanierEntity> getPaniers() {
		return this.paniers;
	}

	public void setPaniers(List<PanierEntity> paniers) {
		this.paniers = paniers;
	}

	public PanierEntity addPanier(PanierEntity panier) {
		getPaniers().add(panier);
		panier.setCommande(this);

		return panier;
	}

	public PanierEntity removePanier(PanierEntity panier) {
		getPaniers().remove(panier);
		panier.setCommande(null);

		return panier;
	}

}