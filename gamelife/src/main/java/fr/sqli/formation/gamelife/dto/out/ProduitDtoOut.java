package fr.sqli.formation.gamelife.dto.out;

import fr.sqli.formation.gamelife.entity.ImageEntity;

import java.io.Serial;
import java.util.List;
import java.util.Objects;

public class ProduitDtoOut extends AbstractDtoOut {
    @Serial
    private static final long serialVersionUID = 1L;
    private String nom;
    private String description;
    private String categorie;
    private String plateforme;
    private boolean etat;
    private List<ImageDtoOut> images;
    private List<Integer> idImages;

    public ProduitDtoOut() {
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

    public boolean getEtat() {
        return etat;
    }

    public void setEtat(boolean pEtat) {
        etat = pEtat;
    }

    public List<ImageDtoOut> getImages() {
        return images;
    }

    public void setImages(List<ImageDtoOut> pImages) {
        images = pImages;
    }

    public List<Integer> getIdImages() {
        return idImages;
    }

    public void setIdImages(List<Integer> pIdImages) {
        idImages = pIdImages;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ProduitDtoOut{");
        sb.append("nom='").append(nom).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", categorie='").append(categorie).append('\'');
        sb.append(", plateforme='").append(plateforme).append('\'');
        sb.append(", etat=").append(etat);
        sb.append(", images=").append(images);
        sb.append(", idImages=").append(idImages);
        sb.append('}');
        return sb.toString();
    }
}
