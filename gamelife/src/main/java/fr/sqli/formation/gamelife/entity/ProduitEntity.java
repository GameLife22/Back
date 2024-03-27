package fr.sqli.formation.gamelife.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "glproduit", schema = "gamelife")
public class ProduitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "description", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "categorie_id", nullable = false)
    private CategorieEntity categorie;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "plateforme_id", nullable = false)
    private PlateformeEntity plateforme;

    @OneToMany(mappedBy = "produit")
    @Cascade(org.hibernate.annotations.CascadeType.ALL) //todo: change
    private List<ImageEntity> images;

    @Column(name = "etat_produit")
    private boolean etat;

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

    public CategorieEntity getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieEntity pCategorie) {
        categorie = pCategorie;
    }

    public PlateformeEntity getPlateforme() {
        return plateforme;
    }

    public void setPlateforme(PlateformeEntity pPlateforme) {
        plateforme = pPlateforme;
    }

    public List<ImageEntity> getImages() {
        return images;
    }

    public void setImages(List<ImageEntity> pImages) {
        images = pImages;
    }

    public boolean getEtat() {
        return etat;
    }

    public void setEtat(boolean pEtat) {
        etat = pEtat;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ProduitEntity{");
        sb.append("id=").append(id);
        sb.append(", nom='").append(nom).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", categorie=").append(categorie);
        sb.append(", plateforme=").append(plateforme);
        sb.append(", images=").append(images);
        sb.append(", etat=").append(etat);
        sb.append('}');
        return sb.toString();
    }
}