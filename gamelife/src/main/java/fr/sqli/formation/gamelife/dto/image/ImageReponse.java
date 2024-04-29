package fr.sqli.formation.gamelife.dto.image;

import java.io.Serial;
import java.util.UUID;

public class ImageReponse {

    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;

    private String image;

    private String titre;

    public ImageReponse() {
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ImageDtoOut{");
        sb.append("id=").append(id);
        sb.append(", image='").append(image).append('\'');
        sb.append(", titre='").append(titre).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
