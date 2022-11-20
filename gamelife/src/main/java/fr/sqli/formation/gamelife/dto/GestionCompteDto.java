package fr.sqli.formation.gamelife.dto;

public class GestionCompteDto {


    public GestionCompteDto(String new_email, String old_email, String etat, String nom, Integer num_rue, String num_siren, String prenom, String role, String rue, String ville) {
        this.new_email = new_email;
        this.old_email = old_email;
        this.etat = etat;
        this.nom = nom;
        this.num_rue = num_rue;
        this.num_siren = num_siren;
        this.prenom = prenom;
        this.role = role;
        this.rue = rue;
        this.ville = ville;
    }

    private String new_email;

    private String old_email;

    private String etat;

    private String nom;

    private Integer num_rue;

    private String num_siren;

    private String prenom;

    private String role;

    private String rue;

    private String ville;

    public String getNew_email() {
        return new_email;
    }

    public void setNew_email(String new_email) {
        this.new_email = new_email;
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

    public Integer getNum_rue() {
        return num_rue;
    }

    public void setNum_rue(Integer num_rue) {
        this.num_rue = num_rue;
    }

    public String getNum_siren() {
        return num_siren;
    }

    public void setNum_siren(String num_siren) {
        this.num_siren = num_siren;
    }

    public String getOld_email() {
        return old_email;
    }

    public void setOld_email(String old_email) {
        this.old_email = old_email;
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