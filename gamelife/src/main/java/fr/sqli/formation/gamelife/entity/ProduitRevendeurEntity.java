package fr.sqli.formation.gamelife.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "glproduit_revendeur", schema = "gamelife")
public class ProduitRevendeurEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "prix", nullable = false, precision = 10)
    private BigDecimal prix;

    @Column(name = "etat", nullable = false, length = 20)
    private String etat;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_produit", nullable = false)
    private ProduitEntity idProduit;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private UtilisateurEntity idUtilisateur;

    public ProduitRevendeurEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public ProduitEntity getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(ProduitEntity idProduit) {
        this.idProduit = idProduit;
    }

    public UtilisateurEntity getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(UtilisateurEntity idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProduitRevendeurEntity that = (ProduitRevendeurEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(stock, that.stock) && Objects.equals(prix, that.prix) && Objects.equals(etat, that.etat) && Objects.equals(idProduit, that.idProduit) && Objects.equals(idUtilisateur, that.idUtilisateur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stock, prix, etat, idProduit, idUtilisateur);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ProduitRevendeurEntity{");
        sb.append("id=").append(id);
        sb.append(", stock=").append(stock);
        sb.append(", prix=").append(prix);
        sb.append(", etat='").append(etat).append('\'');
        sb.append(", idProduit=").append(idProduit);
        sb.append(", idUtilisateur=").append(idUtilisateur);
        sb.append('}');
        return sb.toString();
    }
}