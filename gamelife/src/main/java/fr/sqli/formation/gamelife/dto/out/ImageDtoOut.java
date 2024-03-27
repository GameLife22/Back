package fr.sqli.formation.gamelife.dto.out;

import java.io.Serial;

public class ImageDtoOut extends  AbstractDtoOut {

    @Serial
    private static final long serialVersionUID = 1L;

    private String image;

    private String titre;

    public ImageDtoOut() {
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
        final StringBuffer sb = new StringBuffer("ImageDtoOut{");
        sb.append("image='").append(image).append('\'');
        sb.append(", titre='").append(titre).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
