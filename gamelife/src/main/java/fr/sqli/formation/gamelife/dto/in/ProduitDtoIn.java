package fr.sqli.formation.gamelife.dto.in;

import fr.sqli.formation.gamelife.entity.Categorie;
import fr.sqli.formation.gamelife.entity.Plateforme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;

public class ProduitDtoIn extends AbstractDtoIn {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(CommandeDtoIn.class);

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
    public void validate() {

    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ProduitDtoIn{");
        sb.append("nom='").append(nom).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", categorie=").append(categorie);
        sb.append(", plateforme=").append(plateforme);
        sb.append('}');
        return sb.toString();
    }
}
