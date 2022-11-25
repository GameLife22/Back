package fr.sqli.formation.gamelife.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the utilisateur database table.
 * 
 */
@Entity
@Table(name="utilisateur")
public class UtilisateurEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String email;

	@Column(name="etat_compte")
	private String etatCompte;

	private String mdp;

	private String nom;

	@Column(name="num_rue")
	private int numRue;

	@Column(name="num_siren")
	private String numSiren;

	private String prenom;

	private String role;

	private String rue;

	private String ville;

	@Column(name="code_postal")
	private int codePostal;

	//bi-directional many-to-one association to Commande
	@OneToMany(mappedBy="utilisateur")
	private List<CommandeEntity> commandes;

	//bi-directional many-to-one association to Produit
	@OneToMany(mappedBy="utilisateur")
	private List<ProduitEntity> produits;

	public UtilisateurEntity() {
	}

	public UtilisateurEntity(String email, String etatCompte, String mdp, String nom, int numRue, int codePostal, String numSiren, String prenom, String role, String rue, String ville) {

		this.email = email;
		this.etatCompte = etatCompte;
		this.mdp = mdp;
		this.nom = nom;
		this.numRue = numRue;

		this.codePostal = codePostal;

		this.numSiren = numSiren;
		this.prenom = prenom;
		this.role = role;
		this.rue = rue;
		this.ville = ville;
	}

	public UtilisateurEntity(String email, String mdp) {
		this.email = email;
		this.mdp = mdp;
	}


	public int getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(int cp) {
		this.codePostal = cp;
	}


	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEtatCompte() {
		return this.etatCompte;
	}

	public void setEtatCompte(String etatCompte) {
		this.etatCompte = etatCompte;
	}

	public String getMdp() {
		return this.mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getNumRue() {
		return this.numRue;
	}

	public void setNumRue(int numRue) {
		this.numRue = numRue;
	}

	public String getNumSiren() {
		return this.numSiren;
	}

	public void setNumSiren(String numSiren) {
		this.numSiren = numSiren;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRue() {
		return this.rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getVille() {
		return this.ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public List<CommandeEntity> getCommandes() {
		return this.commandes;
	}

	public void setCommandes(List<CommandeEntity> commandes) {
		this.commandes = commandes;
	}

	public CommandeEntity addCommande(CommandeEntity commande) {
		getCommandes().add(commande);
		commande.setUtilisateur(this);

		return commande;
	}

	public CommandeEntity removeCommande(CommandeEntity commande) {
		getCommandes().remove(commande);
		commande.setUtilisateur(null);

		return commande;
	}

	public List<ProduitEntity> getProduits() {
		return this.produits;
	}

	public void setProduits(List<ProduitEntity> produits) {
		this.produits = produits;
	}

	public ProduitEntity addProduit(ProduitEntity produit) {
		getProduits().add(produit);
		produit.setUtilisateur(this);

		return produit;
	}

	public ProduitEntity removeProduit(ProduitEntity produit) {
		getProduits().remove(produit);
		produit.setUtilisateur(null);

		return produit;
	}


	public static void validate(String nom, String prenom, String pwd, String email, String ville, Integer num_rue, String rue, String role, String num_Siren, String etat, Integer code_postal) throws Exception{
		if(!(nom != null && !nom.trim().isEmpty() &&
				prenom != null && !prenom.trim().isEmpty() &&
				email != null && !email.trim().isEmpty() &&
				pwd != null && !pwd.trim().isEmpty() &&
				num_rue != null && num_rue > 0 &&
				rue != null && !rue.trim().isEmpty() &&
				role != null && !role.trim().isEmpty() &&
				etat != null && !etat.trim().isEmpty() &&
				ville != null && !ville.trim().isEmpty()) &&
				code_postal != null && code_postal > 0){
			throw new IllegalArgumentException("Champs vide ou null");

		}
	}

}