package fr.sqli.formation.gamelife.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "glitem_commande", schema = "gamelife")
public class ItemCommandeEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_commande", nullable = false)
    private CommandeEntity commande;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_produit_revendeur", nullable = false)
    private ProduitRevendeurEntity produitRevendeur;

    @Column(name = "quantite", nullable = false)
    private Integer quantite;

    public ItemCommandeEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer pId) {
        id = pId;
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