package fr.sqli.formation.gamelife.entityGen;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "produit", schema = "public", catalog = "gamelife")
public class ProduitEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "nom")
    private String nom;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "categorie")
    private String categorie;
    @Basic
    @Column(name = "plateforme")
    private String plateforme;
    @Basic
    @Column(name = "etat")
    private boolean etat;
    @OneToMany(mappedBy = "produitByIdProduit")
    private Collection<ImageEntity> imagesById;
    @OneToMany(mappedBy = "produitByIdProduit")
    private Collection<ProduitRevendeurEntity> produitRevendeursById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getPlateforme() {
        return plateforme;
    }

    public void setPlateforme(String plateforme) {
        this.plateforme = plateforme;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProduitEntity that = (ProduitEntity) o;
        return id == that.id && etat == that.etat && Objects.equals(nom, that.nom) && Objects.equals(description, that.description) && Objects.equals(categorie, that.categorie) && Objects.equals(plateforme, that.plateforme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, description, categorie, plateforme, etat);
    }

    public Collection<ImageEntity> getImagesById() {
        return imagesById;
    }

    public void setImagesById(Collection<ImageEntity> imagesById) {
        this.imagesById = imagesById;
    }

    public Collection<ProduitRevendeurEntity> getProduitRevendeursById() {
        return produitRevendeursById;
    }

    public void setProduitRevendeursById(Collection<ProduitRevendeurEntity> produitRevendeursById) {
        this.produitRevendeursById = produitRevendeursById;
    }
}
