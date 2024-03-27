package fr.sqli.formation.gamelife.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "glimage", schema = "gamelife")
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "produit_id", nullable = false)
    @JsonIgnore
    private ProduitEntity produit;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "titre", nullable = false)
    private String titre;

    public UUID getId() {
        return id;
    }

    public void setId(UUID pId) {
        id = pId;
    }

    public ProduitEntity getProduit() {
        return produit;
    }

    public void setProduit(ProduitEntity pProduit) {
        produit = pProduit;
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
        final StringBuffer sb = new StringBuffer("ImageEntity{");
        sb.append("id=").append(id);
        sb.append(", produit=").append(produit);
        sb.append(", image='").append(image).append('\'');
        sb.append(", titre='").append(titre).append('\'');
        sb.append('}');
        return sb.toString();
    }
}