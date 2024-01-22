package fr.sqli.formation.gamelife.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
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
    private ProduitEntity idProduit;

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

    public ProduitEntity getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(ProduitEntity idProduit) {
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ImageEntity{");
        sb.append("id=").append(id);
        sb.append(", idProduit=").append(idProduit);
        sb.append(", image='").append(image).append('\'');
        sb.append(", titre='").append(titre).append('\'');
        sb.append('}');
        return sb.toString();
    }
}