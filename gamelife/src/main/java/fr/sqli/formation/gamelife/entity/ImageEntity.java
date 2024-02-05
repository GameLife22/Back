package fr.sqli.formation.gamelife.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "glimage", schema = "gamelife")
public class ImageEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_produit", nullable = false)
    @JsonIgnore
    private ProduitEntity produit;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "titre", nullable = false)
    private String titre;

    public ImageEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProduitEntity getProduit() {
        return produit;
    }

    public void setProduit(ProduitEntity idProduit) {
        this.produit = idProduit;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageEntity that = (ImageEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(produit, that.produit) && Objects.equals(image, that.image) && Objects.equals(titre, that.titre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, produit, image, titre);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ImageEntity{");
        sb.append("id=").append(id);
        sb.append(", idProduit=").append(produit.getId());
        sb.append(", image='").append(image).append('\'');
        sb.append(", titre='").append(titre).append('\'');
        sb.append('}');
        return sb.toString();
    }
}