package fr.sqli.formation.gamelife.dto.produit;

import fr.sqli.formation.gamelife.dto.categorie.CategorieRequete;
import fr.sqli.formation.gamelife.dto.image.ImageRequete;
import fr.sqli.formation.gamelife.dto.platforme.PlateformeRequete;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;
import java.util.UUID;

//todo: javadoc
@Component
public class ProduitRequete {

    private UUID id;

    @NotEmpty
    private String nom;

    @NotEmpty
    private String description;

    private Set<CategorieRequete> categories;

    private Set<PlateformeRequete> plateformes;

    private List<ImageRequete> images;

    public ProduitRequete() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID pId) {
        id = pId;
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

    public Set<CategorieRequete> recupererCategories() {
        return categories;
    }

    public void setCategories(Set<CategorieRequete> pCategories) {
        categories = pCategories;
    }

    public Set<PlateformeRequete> getPlateformes() {
        return plateformes;
    }

    public void setPlateformes(Set<PlateformeRequete> pPlateformes) {
        plateformes = pPlateformes;
    }

    public List<ImageRequete> getImages() {
        return images;
    }

    public void setImages(List<ImageRequete> pImages) {
        images = pImages;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ProduitDtoIn{");
        sb.append("id=").append(id);
        sb.append(", nom='").append(nom).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", categories=").append(categories);
        sb.append(", plateformes=").append(plateformes);
        sb.append(", images=").append(images);
        sb.append('}');
        return sb.toString();
    }
}