package fr.sqli.formation.gamelife.dto.in;

import fr.sqli.formation.gamelife.entity.ImageEntity;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.util.List;

@Component
public class ProduitDtoIn extends AbstractDtoIn {
    @Serial
    private static final long serialVersionUID = 1L; // TODO: check

    private String nom;
    private String description;
    private String categorie;
    private String plateforme;
    private Boolean etat;
    private List<ImageEntity> images;

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

    public List<ImageEntity> getImages() {
        return images;
    }

    public void setImages(List<ImageEntity> pImages) {
        images = pImages;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ProduitDtoIn{");
        sb.append("nom='").append(nom).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", categorie='").append(categorie).append('\'');
        sb.append(", plateforme='").append(plateforme).append('\'');
        sb.append(", etat=").append(etat);
        sb.append(", images=").append(images);
        sb.append('}');
        return sb.toString();
    }
}