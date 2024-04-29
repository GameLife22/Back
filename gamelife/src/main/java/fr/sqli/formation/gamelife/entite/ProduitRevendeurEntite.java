package fr.sqli.formation.gamelife.entite;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "glproduit_revendeur", schema = "gamelife")
public class ProduitRevendeurEntite {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "prix", nullable = false, precision = 10)
    private BigDecimal prix;

    @Column(name = "etat", nullable = false, length = 25)
    private String etat;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "produit_id", nullable = false)
    private ProduitEntite produit;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private UtilisateurEntite utilisateur;

    public UUID getId() {
        return id;
    }

    public void setId(UUID pId) {
        id = pId;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer pStock) {
        stock = pStock;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal pPrix) {
        prix = pPrix;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String pEtat) {
        etat = pEtat;
    }

    public ProduitEntite recupererProduit() {
        return produit;
    }

    public void setProduit(ProduitEntite pProduit) {
        produit = pProduit;
    }

    public UtilisateurEntite getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(UtilisateurEntite pUtilisateur) {
        utilisateur = pUtilisateur;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ProduitRevendeurEntity{");
        sb.append("id=").append(id);
        sb.append(", stock=").append(stock);
        sb.append(", prix=").append(prix);
        sb.append(", etat='").append(etat).append('\'');
        sb.append('}');
        return sb.toString();
    }
}