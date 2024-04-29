package fr.sqli.formation.gamelife.entite;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "glitem_commande", schema = "gamelife")
public class ItemCommandeEntite {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "commande_id", nullable = false)
    private CommandeEntite commande;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "produit_revendeur_id", nullable = false)
    private ProduitRevendeurEntite produitRevendeur;

    @Column(name = "quantite", nullable = false)
    private Integer quantite;

    public ItemCommandeEntite() {
    }

    public ItemCommandeEntite(UUID pId) {
        id = pId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CommandeEntite getCommande() {
        return commande;
    }

    public void setCommande(CommandeEntite pCommande) {
        commande = pCommande;
    }

    public ProduitRevendeurEntite recupererProduitRevendeur() {
        return produitRevendeur;
    }

    public void setProduitRevendeur(ProduitRevendeurEntite pProduitRevendeur) {
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