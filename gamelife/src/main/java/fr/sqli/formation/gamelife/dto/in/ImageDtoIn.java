package fr.sqli.formation.gamelife.dto.in;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ImageDtoIn extends AbstractDtoIn {

    @NotEmpty //todo: size
    private String image;

    @NotEmpty //todo: size
    private String titre;

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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ImageDtoIn{");
        sb.append("image='").append(image).append('\'');
        sb.append(", titre='").append(titre).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
