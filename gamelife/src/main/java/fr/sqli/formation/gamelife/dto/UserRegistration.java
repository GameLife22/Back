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

    public String getLastName() {
        return nom;
    }

    public void setLastName(String pNom) {
        nom = pNom;
    }

    public String getFirstName() {
        return prenom;
    }

    public void setFirstName(String pPrenom) {
        prenom = pPrenom;
    }
}
