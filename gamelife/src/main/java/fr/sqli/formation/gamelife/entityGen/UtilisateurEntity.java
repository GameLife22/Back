package fr.sqli.formation.gamelife.entityGen;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "utilisateur", schema = "public", catalog = "gamelife")
public class UtilisateurEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "nom")
    private String nom;
    @Basic
    @Column(name = "prenom")
    private String prenom;
    @Basic
    @Column(name = "mdp")
    private String mdp;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "num_rue")
    private int numRue;
    @Basic
    @Column(name = "rue")
    private String rue;
    @Basic
    @Column(name = "ville")
    private String ville;
    @Basic
    @Column(name = "code_postal")
    private Integer codePostal;
    @Basic
    @Column(name = "role")
    private String role;
    @Basic
    @Column(name = "num_siren")
    private String numSiren;
    @Basic
    @Column(name = "etat_compte")
    private boolean etatCompte;
    @Basic
    @Column(name = "reset_password_token")
    private String resetPasswordToken;
    @OneToMany(mappedBy = "utilisateurByIdUtilisateur")
    private Collection<CommandeEntity> commandesById;
    @OneToMany(mappedBy = "utilisateurByIdUtilisateur")
    private Collection<ProduitRevendeurEntity> produitRevendeursById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumRue() {
        return numRue;
    }

    public void setNumRue(int numRue) {
        this.numRue = numRue;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Integer getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(Integer codePostal) {
        this.codePostal = codePostal;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNumSiren() {
        return numSiren;
    }

    public void setNumSiren(String numSiren) {
        this.numSiren = numSiren;
    }

    public boolean isEtatCompte() {
        return etatCompte;
    }

    public void setEtatCompte(boolean etatCompte) {
        this.etatCompte = etatCompte;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UtilisateurEntity that = (UtilisateurEntity) o;
        return id == that.id && numRue == that.numRue && etatCompte == that.etatCompte && Objects.equals(nom, that.nom) && Objects.equals(prenom, that.prenom) && Objects.equals(mdp, that.mdp) && Objects.equals(email, that.email) && Objects.equals(rue, that.rue) && Objects.equals(ville, that.ville) && Objects.equals(codePostal, that.codePostal) && Objects.equals(role, that.role) && Objects.equals(numSiren, that.numSiren) && Objects.equals(resetPasswordToken, that.resetPasswordToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, prenom, mdp, email, numRue, rue, ville, codePostal, role, numSiren, etatCompte, resetPasswordToken);
    }

    public Collection<CommandeEntity> getCommandesById() {
        return commandesById;
    }

    public void setCommandesById(Collection<CommandeEntity> commandesById) {
        this.commandesById = commandesById;
    }

    public Collection<ProduitRevendeurEntity> getProduitRevendeursById() {
        return produitRevendeursById;
    }

    public void setProduitRevendeursById(Collection<ProduitRevendeurEntity> produitRevendeursById) {
        this.produitRevendeursById = produitRevendeursById;
    }
}
