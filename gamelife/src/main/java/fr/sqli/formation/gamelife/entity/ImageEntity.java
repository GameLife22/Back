package fr.sqli.formation.gamelife.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "glimage", schema = "gamelife")
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uuid", nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_produit", nullable = false)
    private ProduitEntity produit;

    @NotNull
    @Column(name = "url", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String url;

    @Size(max = 255)
    @NotNull
    @Column(name = "filename", nullable = false)
    private String filename;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ProduitEntity getProduit() {
        return produit;
    }

    public void setIdProduit(ProduitEntity pProduitEntity) {
        this.produit = pProduitEntity;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ImageEntity{");
        sb.append("id=").append(id);
        sb.append(", produit=").append(produit);
        sb.append(", url='").append(url).append('\'');
        sb.append(", filename='").append(filename).append('\'');
        sb.append('}');
        return sb.toString();
    }
}