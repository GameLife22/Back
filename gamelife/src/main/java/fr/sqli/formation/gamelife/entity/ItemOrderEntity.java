package fr.sqli.formation.gamelife.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "glitem_commande", schema = "gamelife")
public class ItemOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "commande_id", nullable = false)
    private OrderEntity commande;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "produit_revendeur_id", nullable = false)
    private SellerGameEntity produitRevendeur;

    @Column(name = "quantite", nullable = false)
    private Integer quantite;

    public ItemOrderEntity() {
    }

    public ItemOrderEntity(UUID pId) {
        id = pId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public OrderEntity getCommande() {
        return commande;
    }

    public void setCommande(OrderEntity pCommande) {
        commande = pCommande;
    }

    public SellerGameEntity recupererProduitRevendeur() {
        return produitRevendeur;
    }

    public void setProduitRevendeur(SellerGameEntity pProduitRevendeur) {
        produitRevendeur = pProduitRevendeur;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer pQuantite) {
        quantite = pQuantite;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ItemCommandeEntity{");
        sb.append("id=").append(id);
        sb.append(", quantite=").append(quantite);
        sb.append('}');
        return sb.toString();
    }
}