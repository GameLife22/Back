package fr.sqli.formation.gamelife.entityGen;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "produit_revendeur", schema = "public", catalog = "gamelife")
public class ProduitRevendeurEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "stock")
    private int stock;
    @Basic
    @Column(name = "prix")
    private int prix;
    @Basic
    @Column(name = "id_produit")
    private int idProduit;
    @Basic
    @Column(name = "id_utilisateur")
    private int idUtilisateur;
    @OneToMany(mappedBy = "produitRevendeurByIdProduitRevendeur")
    private Collection<ItemCommandeEntity> itemCommandesById;
    @ManyToOne
    @JoinColumn(name = "id_produit", referencedColumnName = "id", nullable = false)
    private ProduitEntity produitByIdProduit;
    @ManyToOne
    @JoinColumn(name = "id_utilisateur", referencedColumnName = "id", nullable = false)
    private UtilisateurEntity utilisateurByIdUtilisateur;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProduitRevendeurEntity that = (ProduitRevendeurEntity) o;
        return id == that.id && stock == that.stock && prix == that.prix && idProduit == that.idProduit && idUtilisateur == that.idUtilisateur;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stock, prix, idProduit, idUtilisateur);
    }

    public Collection<ItemCommandeEntity> getItemCommandesById() {
        return itemCommandesById;
    }

    public void setItemCommandesById(Collection<ItemCommandeEntity> itemCommandesById) {
        this.itemCommandesById = itemCommandesById;
    }

    public ProduitEntity getProduitByIdProduit() {
        return produitByIdProduit;
    }

    public void setProduitByIdProduit(ProduitEntity produitByIdProduit) {
        this.produitByIdProduit = produitByIdProduit;
    }

    public UtilisateurEntity getUtilisateurByIdUtilisateur() {
        return utilisateurByIdUtilisateur;
    }

    public void setUtilisateurByIdUtilisateur(UtilisateurEntity utilisateurByIdUtilisateur) {
        this.utilisateurByIdUtilisateur = utilisateurByIdUtilisateur;
    }
}
