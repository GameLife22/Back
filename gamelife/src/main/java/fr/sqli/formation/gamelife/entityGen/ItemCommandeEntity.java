package fr.sqli.formation.gamelife.entityGen;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "item_commande", schema = "public", catalog = "gamelife")
public class ItemCommandeEntity {
    @Basic
    @Column(name = "id_commande")
    private int idCommande;
    @Basic
    @Column(name = "id_produit_revendeur")
    private int idProduitRevendeur;
    @Basic
    @Column(name = "quantite")
    private int quantite;
    @ManyToOne
    @JoinColumn(name = "id_commande", referencedColumnName = "id", nullable = false)
    private CommandeEntity commandeByIdCommande;
    @ManyToOne
    @JoinColumn(name = "id_produit_revendeur", referencedColumnName = "id", nullable = false)
    private ProduitRevendeurEntity produitRevendeurByIdProduitRevendeur;

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public int getIdProduitRevendeur() {
        return idProduitRevendeur;
    }

    public void setIdProduitRevendeur(int idProduitRevendeur) {
        this.idProduitRevendeur = idProduitRevendeur;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemCommandeEntity that = (ItemCommandeEntity) o;
        return idCommande == that.idCommande && idProduitRevendeur == that.idProduitRevendeur && quantite == that.quantite;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCommande, idProduitRevendeur, quantite);
    }

    public CommandeEntity getCommandeByIdCommande() {
        return commandeByIdCommande;
    }

    public void setCommandeByIdCommande(CommandeEntity commandeByIdCommande) {
        this.commandeByIdCommande = commandeByIdCommande;
    }

    public ProduitRevendeurEntity getProduitRevendeurByIdProduitRevendeur() {
        return produitRevendeurByIdProduitRevendeur;
    }

    public void setProduitRevendeurByIdProduitRevendeur(ProduitRevendeurEntity produitRevendeurByIdProduitRevendeur) {
        this.produitRevendeurByIdProduitRevendeur = produitRevendeurByIdProduitRevendeur;
    }
}
