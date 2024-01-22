package fr.sqli.formation.gamelife.dto.out;

import fr.sqli.formation.gamelife.entity.Categorie;
import fr.sqli.formation.gamelife.entity.Plateforme;

import java.io.Serial;

public class ProduitDtoOut extends AbstractDtoOut {
    @Serial
    private static final long serialVersionUID = 1L;

    private String nom;
    private String description;
    private Categorie categorie;
    private Plateforme plateforme;
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

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie pCategorie) {
        categorie = pCategorie;
    }

    public Plateforme getPlateforme() {
        return plateforme;
    }

    public void setPlateforme(Plateforme pPlateforme) {
        plateforme = pPlateforme;
    }

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean pEtat) {
        etat = pEtat;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ProduitDtoOut{");
        sb.append("nom='").append(nom).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", categorie=").append(categorie);
        sb.append(", plateforme=").append(plateforme);
        sb.append(", etat=").append(etat);
        sb.append('}');
        return sb.toString();
    }
}
