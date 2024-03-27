package fr.sqli.formation.gamelife.dto.in;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.util.List;

//todo: javadoc
@Component
public class ProduitDtoIn extends AbstractDtoIn {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String nom;

    @NotEmpty
    private String description;

    @NotEmpty
    private CategorieDtoIn categorie;

    @NotEmpty
    private PlateformeDtoIn plateforme;

    private boolean etat;

    @Size(min=1)
    @NotNull
    private List<ImageDtoIn> images;

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

    public CategorieDtoIn getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieDtoIn pCategorie) {
        categorie = pCategorie;
    }

    public PlateformeDtoIn getPlateforme() {
        return plateforme;
    }

    public void setPlateforme(PlateformeDtoIn pPlateforme) {
        plateforme = pPlateforme;
    }

    public boolean getEtat() {
        return etat;
    }

    public void setEtat(boolean pEtat) {
        etat = pEtat;
    }

    public List<ImageDtoIn> getImages() {
        return images;
    }

    public void setImages(List<ImageDtoIn> pImages) {
        images = pImages;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ProduitDtoIn{");
        sb.append("nom='").append(nom).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", categorie=").append(categorie);
        sb.append(", plateforme=").append(plateforme);
        sb.append(", etat=").append(etat);
        sb.append(", images=").append(images);
        sb.append('}');
        return sb.toString();
    }
}