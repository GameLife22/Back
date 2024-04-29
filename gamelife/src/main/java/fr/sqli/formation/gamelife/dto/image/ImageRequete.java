package fr.sqli.formation.gamelife.dto.image;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class ImageRequete {

    private UUID id;

    @NotEmpty //todo: size
    private String image;

    @NotEmpty //todo: size
    private String titre;

    @NotNull
    private UUID produitId;

    public ImageRequete() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID pId) {
        id = pId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String pImage) {
        image = pImage;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String pTitre) {
        titre = pTitre;
    }

    public UUID recupererProduitId() {
        return produitId;
    }

    public void setProduitId(UUID pProduitId) {
        produitId = pProduitId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ImageDtoIn{");
        sb.append("id=").append(id);
        sb.append(", image='").append(image).append('\'');
        sb.append(", titre='").append(titre).append('\'');
        sb.append(", produitId=").append(produitId);
        sb.append('}');
        return sb.toString();
    }
}
