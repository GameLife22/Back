package fr.sqli.formation.gamelife.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "glitem_commande", schema = "gamelife")
public class ItemCommandeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "commande_id", nullable = false)
    private CommandeEntity commande;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "produit_revendeur_id", nullable = false)
    private ProduitRevendeurEntity produitRevendeur;

    @Column(name = "quantite", nullable = false)
    private Integer quantite;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CommandeEntity getCommande() {
        return commande;
    }

    public void setCommande(CommandeEntity pCommande) {
        commande = pCommande;
    }

    public ProduitRevendeurEntity getProduitRevendeur() {
        return produitRevendeur;
    }

    public void setProduitRevendeur(ProduitRevendeurEntity pProduitRevendeur) {
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
        sb.append(", commande=").append(commande);
        sb.append(", produitRevendeur=").append(produitRevendeur);
        sb.append(", quantite=").append(quantite);
        sb.append('}');
        return sb.toString();
    }
}