package fr.sqli.formation.gamelife.dto.in;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ImageDtoIn extends AbstractDtoIn {
    @NotEmpty
    private String image;
    @NotEmpty
    private String titre;
    private Integer idProduit;

    public ImageDtoIn() {
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

    public Integer getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Integer pIdProduit) {
        idProduit = pIdProduit;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ImageDtoIn{");
        sb.append("image='").append(image).append('\'');
        sb.append(", titre='").append(titre).append('\'');
        sb.append(", idProduit=").append(idProduit);
        sb.append('}');
        return sb.toString();
    }
}
