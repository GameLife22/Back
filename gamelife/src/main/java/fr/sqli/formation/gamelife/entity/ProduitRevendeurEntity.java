package fr.sqli.formation.gamelife.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produit")
    private ProduitEntity produit;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur")
    private UtilisateurEntity utilisateur;

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

    public ProduitEntity getProduit() {
        return produit;
    }

    public void setProduit(ProduitEntity produit) {
        this.produit = produit;
    }

    public UtilisateurEntity getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(UtilisateurEntity utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProduitRevendeurEntity that = (ProduitRevendeurEntity) o;
        return id == that.id && stock == that.stock && prix == that.prix && produit == that.produit && utilisateur == that.utilisateur;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stock, prix, produit, utilisateur);
    }
}
