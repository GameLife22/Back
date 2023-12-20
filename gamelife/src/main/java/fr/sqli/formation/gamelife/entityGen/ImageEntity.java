package fr.sqli.formation.gamelife.entityGen;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "image", schema = "public", catalog = "gamelife")
public class ImageEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "id_produit")
    private int idProduit;
    @Basic
    @Column(name = "image")
    private String image;
    @Basic
    @Column(name = "titre")
    private String titre;
    @Basic
    @Column(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "id_produit", referencedColumnName = "id", nullable = false)
    private ProduitEntity produitByIdProduit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageEntity that = (ImageEntity) o;
        return id == that.id && idProduit == that.idProduit && Objects.equals(image, that.image) && Objects.equals(titre, that.titre) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idProduit, image, titre, description);
    }

    public ProduitEntity getProduitByIdProduit() {
        return produitByIdProduit;
    }

    public void setProduitByIdProduit(ProduitEntity produitByIdProduit) {
        this.produitByIdProduit = produitByIdProduit;
    }
}
