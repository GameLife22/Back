package fr.sqli.formation.gamelife.dto;

import java.util.UUID;

public class UserRegistration {
    private UUID id;
    private String nom;
    private String prenom;

    public UUID getId() {
        return id;
    }

    public void setId(UUID pId) {
        id = pId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String pNom) {
        nom = pNom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String pPrenom) {
        prenom = pPrenom;
    }
}
