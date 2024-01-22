package fr.sqli.formation.gamelife.entity;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "glutilisateur", schema = "gamelife")
public class UtilisateurEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nom", nullable = false, length = 50)
    private String nom;

    @Column(name = "prenom", nullable = false, length = 50)
    private String prenom;

    @Column(name = "mdp", nullable = false, length = 80)
    private String mdp;

    @Column(name = "email", nullable = false, length = 80)
    private String email;

    @Column(name = "num_rue", nullable = false)
    private Integer numRue;

    @Column(name = "rue", nullable = false)
    private String rue;

    @Column(name = "ville", nullable = false, length = 80)
    private String ville;

    @Column(name = "code_postal")
    private Integer codePostal;

    @Column(name = "role", nullable = false, length = 50)
    private String role;

    @Column(name = "num_siren", length = 9)
    private String numSiren;

    @Column(name = "etat_compte", nullable = false)
    private Boolean etatCompte = false;

    @Column(name = "reset_password_token", length = 30)
    private String resetPasswordToken;

    public UtilisateurEntity() {
    }

    public UtilisateurEntity(String email, Boolean etatCompte, String mdp, String nom, int numRue, String numSiren, String prenom, String role, String rue, String ville, int codePostal, String resetPasswordToken) {
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
        this.resetPasswordToken = resetPasswordToken;
    }

    public UtilisateurEntity(String email, String mdp) {
        this.email = email;
        this.mdp = mdp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getNumRue() {
        return numRue;
    }

    public void setNumRue(Integer numRue) {
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

    public Boolean getEtatCompte() {
        return etatCompte;
    }

    public void setEtatCompte(Boolean etatCompte) {
        this.etatCompte = etatCompte;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public static void validate(String nom, String prenom, String pwd, String email, String ville, Integer num_rue, String rue, String num_Siren, Integer code_postal) throws Exception{
        if(!(nom != null && !nom.trim().isEmpty() &&
                prenom != null && !prenom.trim().isEmpty() &&
                email != null && !email.trim().isEmpty() &&
                email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$") &&
                pwd != null && !pwd.trim().isEmpty() &&
                pwd.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$") &&
                num_rue != null && num_rue >= 0 &&
                rue != null && !rue.trim().isEmpty() &&
                ville != null && !ville.trim().isEmpty()) &&
                code_postal != null && code_postal > 0){
            throw new IllegalArgumentException("Champs invalides");

        }
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UtilisateurEntity{");
        sb.append("id=").append(id);
        sb.append(", nom='").append(nom).append('\'');
        sb.append(", prenom='").append(prenom).append('\'');
        sb.append(", mdp='").append(mdp).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", numRue=").append(numRue);
        sb.append(", rue='").append(rue).append('\'');
        sb.append(", ville='").append(ville).append('\'');
        sb.append(", codePostal=").append(codePostal);
        sb.append(", role='").append(role).append('\'');
        sb.append(", numSiren='").append(numSiren).append('\'');
        sb.append(", etatCompte=").append(etatCompte);
        sb.append(", resetPasswordToken='").append(resetPasswordToken).append('\'');
        sb.append('}');
        return sb.toString();
    }
}