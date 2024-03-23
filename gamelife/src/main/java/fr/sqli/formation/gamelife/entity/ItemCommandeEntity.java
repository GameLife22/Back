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
    @Column(name = "uuid", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_commande", nullable = false)
    private CommandeEntity idCommande;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_produit_revendeur", nullable = false)
    private ProduitRevendeurEntity idProduitRevendeur;

    @Column(name = "quantite", nullable = false)
    private Integer quantite;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CommandeEntity getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(CommandeEntity idCommande) {
        this.idCommande = idCommande;
    }

    public ProduitRevendeurEntity getIdProduitRevendeur() {
        return idProduitRevendeur;
    }

    public void setIdProduitRevendeur(ProduitRevendeurEntity idProduitRevendeur) {
        this.idProduitRevendeur = idProduitRevendeur;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ItemCommandeEntity{");
        sb.append("id=").append(id);
        sb.append(", idCommande=").append(idCommande);
        sb.append(", idProduitRevendeur=").append(idProduitRevendeur);
        sb.append(", quantite=").append(quantite);
        sb.append('}');
        return sb.toString();
    }
}