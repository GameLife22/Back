package fr.sqli.formation.gamelife.dto;

import java.util.UUID;

public class Account {

    public Account() {
    }

    public Account(UUID id, String nom, String prenom, String email, Integer num_rue, String rue, String ville, Integer codePostal, String numSiren) {
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

    public Integer getZipCode() {
        return codePostal;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return nom;
    }

    public void setLastName(String nom) {
        this.nom = nom;
    }

    public Integer getNum_rue() {
        return num_rue;
    }

    public void setNum_rue(Integer num_rue) {
        this.num_rue = num_rue;
    }

    public String getSirenNumber() {
        return numSiren;
    }

    public void setSirenNumber(String numSiren) {
        this.numSiren = numSiren;
    }

    public String getFirstName() {
        return prenom;
    }

    public void setFirstName(String prenom) {
        this.prenom = prenom;
    }

    public String getStreet() {
        return rue;
    }

    public void setStreet(String rue) {
        this.rue = rue;
    }

    public String getCity() {
        return ville;
    }

    public void setCity(String ville) {
        this.ville = ville;
    }
}