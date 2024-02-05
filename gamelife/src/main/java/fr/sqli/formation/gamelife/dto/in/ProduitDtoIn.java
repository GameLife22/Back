package fr.sqli.formation.gamelife.dto.in;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.sqli.formation.gamelife.entity.Categorie;
import fr.sqli.formation.gamelife.entity.Plateforme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;

public class ProduitDtoIn extends AbstractDtoIn {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(ProduitDtoIn.class);

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
    @JsonIgnore
    public void validate() {
        if(this.getNom() == null) {
            ProduitDtoIn.LOG.error("Validate(nom est null)");
            throw new IllegalArgumentException("Le nom est null ou vide !");
        }

        if(this.getDescription() == null) {
            ProduitDtoIn.LOG.error("Validate(description est null)");
            throw new IllegalArgumentException("La description est null ou vide !");
        }

        if(this.getCategorie() == null) {
            ProduitDtoIn.LOG.error("Validate(catégorie est null)");
            throw new IllegalArgumentException("La catégorie est null ou vide !");
        }

        if(this.getPlateforme() == null) {
            ProduitDtoIn.LOG.error("Validate(plateforme est null)");
            throw new IllegalArgumentException("La plateforme est null ou vide !");
        }

        if(this.getEtat() == null) { //TODO: check
            ProduitDtoIn.LOG.error("Validate(etat est null)");
            throw new IllegalArgumentException("L'état est null ou vide !");
        }
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