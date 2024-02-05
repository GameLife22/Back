package fr.sqli.formation.gamelife.dto.out;

import fr.sqli.formation.gamelife.entity.Categorie;
import fr.sqli.formation.gamelife.entity.Plateforme;

import java.io.Serial;
import java.util.Objects;

public class ProduitDtoOut extends AbstractDtoOut {
    @Serial
    private static final long serialVersionUID = 1L;

    private String nom;
    private String description;
    private String categorie;
    private String plateforme;
    private Boolean etat;

    public String getNom() {
        return nom;
    }

    public void setNom(String pNom) {
        nom = pNom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String pDescription) {
        description = pDescription;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String pCategorie) {
        categorie = pCategorie;
    }

    public String getPlateforme() {
        return plateforme;
    }

    public void setPlateforme(String pPlateforme) {
        plateforme = pPlateforme;
    }

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean pEtat) {
        etat = pEtat;
    }

    @Override
    public boolean equals(Object pO) {
        if (this == pO) return true;
        if (pO == null || getClass() != pO.getClass()) return false;
        ProduitDtoOut that = (ProduitDtoOut) pO;
        return Objects.equals(nom, that.nom) && Objects.equals(description, that.description) && Objects.equals(categorie, that.categorie) && Objects.equals(plateforme, that.plateforme) && Objects.equals(etat, that.etat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom, description, categorie, plateforme, etat);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ProduitDtoOut{");
        sb.append("id='").append(super.getId()).append('\'');
        sb.append(", nom='").append(nom).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", categorie='").append(categorie).append('\'');
        sb.append(", plateforme='").append(plateforme).append('\'');
        sb.append(", etat=").append(etat);
        sb.append('}');
        return sb.toString();
    }
}
