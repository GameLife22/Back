package fr.sqli.formation.gamelife.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import fr.sqli.formation.gamelife.ex.ParameterException;

import java.util.Arrays;
import java.util.List;

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

    private final String nomCategorie;

    Categorie(String pNomCategorie) {
        this.nomCategorie = pNomCategorie;
    }

    @JsonValue // TODO: check
    public String getNomCategorie() {
        return nomCategorie;
    }
}