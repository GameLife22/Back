package fr.sqli.formation.gamelife.dto;

import javax.persistence.Column;

public class InscriptionDto {
    private String email;

    private String etat;

    private String mdp;

    private String nom;

    private int num_rue;

    private String num_siren;

    private String prenom;

    private String role;

    private String rue;

    private String ville;

    private  int code_postal;
    public InscriptionDto() {
    }
    public InscriptionDto(String email, String etat, String mdp, String nom, int num_rue, String num_siren, String prenom, String role, String rue, String ville, int code_postal) {
        this.email = email;
        this.etat = etat;
        this.mdp = mdp;
        this.nom = nom;
        this.num_rue = num_rue;
        this.num_siren = num_siren;
        this.prenom = prenom;
        this.role = role;
        this.rue = rue;
        this.ville = ville;
        this.code_postal = code_postal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNum_rue() {
        return num_rue;
    }

    public void setNum_rue(int num_rue) {
        this.num_rue = num_rue;
    }

    public String getNum_siren() {
        return num_siren;
    }

    public void setNum_siren(String num_siren) {
        this.num_siren = num_siren;
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

    public int getCode_postal() {
        return code_postal;
    }

    public void setCode_postal(int code_postal) {
        this.code_postal = code_postal;
    }
}
