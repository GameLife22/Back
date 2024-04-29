package fr.sqli.formation.gamelife.dto.gestionCompte;

import java.util.UUID;

public class GestionCompteDto {

    public GestionCompteDto() {
    }

    public GestionCompteDto(UUID id, String nom, String prenom, String email, Integer num_rue, String rue, String ville, Integer codePostal, String numSiren) {
        this.id = id;
        this.email = email;
        this.nom = nom;
        this.num_rue = num_rue;
        this.codePostal = codePostal;
        this.numSiren = numSiren;
        this.prenom = prenom;
        this.rue = rue;
        this.ville = ville;
    }

    private UUID id;
    private String email;

    private String nom;

    private Integer num_rue;

    private Integer codePostal;

    private String numSiren;

    private String prenom;

    private String rue;

    private String ville;

    public UUID getId() {
        return id;
    }

    public void setId(UUID pId) {
        id = pId;
    }

    public Integer getCodePostal() {
        return codePostal;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getNumSiren() {
        return numSiren;
    }

    public void setNumSiren(String numSiren) {
        this.numSiren = numSiren;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
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