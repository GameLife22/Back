package fr.sqli.formation.gamelife.entite;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "glcategorie", schema = "gamelife")
public class CategorieEntite {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @Column(name = "libelle", nullable = false, length = 25)
    private String libelle;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private Set<ProduitEntite> produits = new HashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID pId) {
        id = pId;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String pLibelle) {
        libelle = pLibelle;
    }

    public Set<ProduitEntite> recupererProduits() {
        return produits;
    }

    public void setProduits(Set<ProduitEntite> pProduits) {
        produits = pProduits;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CategorieEntity{");
        sb.append("id=").append(id);
        sb.append(", libelle='").append(libelle).append('\'');
        sb.append(", produits=").append(produits);
        sb.append('}');
        return sb.toString();
    }
}