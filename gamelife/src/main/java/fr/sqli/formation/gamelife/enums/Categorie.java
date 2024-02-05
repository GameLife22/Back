package fr.sqli.formation.gamelife.enums;

import fr.sqli.formation.gamelife.ex.ParameterException;

public enum Categorie {
    ACTION("action"),
    AVENTURE("aventure"),
    RPG("rpg"),
    STRATEGIE("strategie"),
    FPS("fps"),
    SPORT("sport"),
    CASUAL("casual"),
    SIMULATION("simulation"),
    PUZZLE("puzzle"),
    ARCADE("arcade"),
    PLATEFORME("plateforme"),
    MMO("mmo"),
    COURSE("course"),
    COMBAT("combat"),
    FAMILLE("famille"),
    SOCIETE("societe"),
    EDUCATIF("educatif"),
    CARTE("carte");

    private final String libelle;

    Categorie(String pLibelle) {
        this.libelle = pLibelle;
    }

    public String getLibelle() {
        return libelle;
    }

    public static String fromString(String pLibelle) throws ParameterException {
        if (Character.isDigit(pLibelle.charAt(0))) {
            pLibelle = "_" + pLibelle;
        }

        for (Categorie categorie : Categorie.values()) {
            if (categorie.getLibelle().equalsIgnoreCase(pLibelle)) {
                return pLibelle;
            }
        }

        throw new ParameterException("Plateforme invalide : " + pLibelle);
    }
}