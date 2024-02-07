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
    private CommandeEntity idCommande;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_produit_revendeur", nullable = false)
    private ProduitRevendeurEntity idProduitRevendeur;

    @Column(name = "quantite", nullable = false)
    private Integer quantite;

    public ItemCommandeEntity() {
    }

    public ItemCommandeEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemCommandeEntity that = (ItemCommandeEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(idCommande, that.idCommande) && Objects.equals(idProduitRevendeur, that.idProduitRevendeur) && Objects.equals(quantite, that.quantite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idCommande, idProduitRevendeur, quantite);
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