package fr.sqli.formation.gamelife.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "glproduit_revendeur", schema = "gamelife")
public class ProduitRevendeurEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "prix", nullable = false, precision = 10)
    private BigDecimal prix;

    @Column(name = "etat", nullable = false, length = 25)
    private String etat;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "produit_id", nullable = false)
    private ProduitEntity produit;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private UtilisateurEntity utilisateur;

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

    public ProduitEntity getProduit() {
        return produit;
    }

    public void setProduit(ProduitEntity pProduit) {
        produit = pProduit;
    }

    public UtilisateurEntity getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(UtilisateurEntity pUtilisateur) {
        utilisateur = pUtilisateur;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ProduitRevendeurEntity{");
        sb.append("id=").append(id);
        sb.append(", stock=").append(stock);
        sb.append(", prix=").append(prix);
        sb.append(", etat='").append(etat).append('\'');
        sb.append(", produit=").append(produit);
        sb.append(", utilisateur=").append(utilisateur);
        sb.append('}');
        return sb.toString();
    }
}