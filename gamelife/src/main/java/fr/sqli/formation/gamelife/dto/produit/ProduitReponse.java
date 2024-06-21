package fr.sqli.formation.gamelife.dto.produit;

import fr.sqli.formation.gamelife.dto.categorie.CategorieReponse;
import fr.sqli.formation.gamelife.dto.platforme.PlateformeReponse;
import fr.sqli.formation.gamelife.dto.image.ImageReponse;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ProduitReponse {

    private UUID id;

    private String nom;

    private String description;

    private Set<CategorieReponse> categories;

    private Set<PlateformeReponse> plateformes;

    private List<ImageReponse> images;

    private BigDecimal prix;


    public ProduitReponse() {
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

    public Set<CategorieReponse> recupererCategories() {
        return categories;
    }

    public void setCategories(Set<CategorieReponse> pCategories) {
        categories = pCategories;
    }

    public Set<PlateformeReponse> getPlateformes() {
        return plateformes;
    }

    public void setPlateformes(Set<PlateformeReponse> pPlateformes) {
        plateformes = pPlateformes;
    }

    public List<ImageReponse> getImages() {
        return images;
    }

    public void setImages(List<ImageReponse> pImages) {
        images = pImages;
    }
    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal pPrix) {
        prix = pPrix;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ProduitDtoOut{");
        sb.append("id=").append(id);
        sb.append(", nom='").append(nom).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", categories=").append(categories);
        sb.append(", plateformes=").append(plateformes);
        sb.append(", images=").append(images);
        sb.append(", prix=").append(prix);
        sb.append('}');
        return sb.toString();
    }
}
