package fr.sqli.formation.gamelife.entite;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "glproduit", schema = "gamelife")
public class ProduitEntite {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "description", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @JoinTable(name = "glproduit_glcategorie", schema = "gamelife",
            joinColumns = @JoinColumn(name = "produit_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "categorie_id", referencedColumnName = "id"))
    private Set<CategorieEntite> categories = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @JoinTable(name = "glproduit_glplateforme", schema = "gamelife",
            joinColumns = @JoinColumn(name = "produit_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "plateforme_id", referencedColumnName = "id"))
    private Set<PlateformeEntite> plateformes = new HashSet<>();

    @OneToMany(mappedBy = "produit", fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<ImageEntite> images;

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

    public Set<CategorieEntite> recupererCategories() {
        return categories;
    }

    public void setCategories(Set<CategorieEntite> pCategories) {
        categories = pCategories;
    }

    public Set<PlateformeEntite> getPlateformes() {
        return plateformes;
    }

    public void setPlateformes(Set<PlateformeEntite> pPlateformes) {
        plateformes = pPlateformes;
    }

    public List<ImageEntite> getImages() {
        return images;
    }

    public void setImages(List<ImageEntite> pImages) {
        images = pImages;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ProduitEntity{");
        sb.append("id=").append(id);
        sb.append(", nom='").append(nom).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", images=").append(images);
        sb.append('}');
        return sb.toString();
    }
}