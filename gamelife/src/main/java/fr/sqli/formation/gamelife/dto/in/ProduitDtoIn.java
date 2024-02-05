package fr.sqli.formation.gamelife.dto.in;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.sqli.formation.gamelife.repository.IProduitDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serial;

@Component
public class ProduitDtoIn extends AbstractDtoIn {
    @Serial
    private static final long serialVersionUID = 1L;

    private String nom;

    private String description;

    private String categorie;

    private String plateforme;

    private Boolean etat;

    private IProduitDao produitDao;

    public ProduitDtoIn() {
    }

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
    public String toString() {
        final StringBuffer sb = new StringBuffer("ProduitDtoIn{");
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