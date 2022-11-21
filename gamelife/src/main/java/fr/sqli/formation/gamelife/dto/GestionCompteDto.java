package fr.sqli.formation.gamelife.dto;

public class GestionCompteDto {


    public GestionCompteDto(String newEmail, String oldEmail, String etat, String nom, Integer numRue, String numSiren, String prenom, String role, String rue, String ville) {
        this.newEmail = newEmail;
        this.oldEmail = oldEmail;
        this.etat = etat;
        this.nom = nom;
        this.numRue = numRue;
        this.numSiren = numSiren;
        this.prenom = prenom;
        this.role = role;
        this.rue = rue;
        this.ville = ville;
    }

    private String newEmail;

    private String oldEmail;

    private String etat;

    private String nom;

    private Integer numRue;

    private Integer cp;

    private String numSiren;

    private String prenom;

    private String role;

    private String rue;

    private String ville;


    public Integer getCp() {
        return cp;
    }

    public void setCp(Integer cp) {
        this.cp = cp;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getNumRue() {
        return numRue;
    }

    public void setNumRue(Integer numRue) {
        this.numRue = numRue;
    }

    public String getNumSiren() {
        return numSiren;
    }

    public void setNumSiren(String numSiren) {
        this.numSiren = numSiren;
    }

    public String getOldEmail() {
        return oldEmail;
    }

    public void setOldEmail(String oldEmail) {
        this.oldEmail = oldEmail;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
}