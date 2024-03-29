package fr.sqli.formation.gamelife.dto.out;

import java.io.Serial;
import java.util.Objects;

public class ItemCommandeDtoOut {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer idCommande;
    private Integer idProduitRevendeur;
    private Integer quantite;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(Integer idCommande) {
        this.idCommande = idCommande;
    }

    public Integer getIdProduitRevendeur() {
        return idProduitRevendeur;
    }

    public void setIdProduitRevendeur(Integer idProduitRevendeur) {
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
        ItemCommandeDtoOut that = (ItemCommandeDtoOut) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(idCommande, that.idCommande) &&
                Objects.equals(idProduitRevendeur, that.idProduitRevendeur) &&
                Objects.equals(quantite, that.quantite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idCommande, idProduitRevendeur, quantite);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ItemCommandeDtoOut{");
        sb.append("id=").append(id);
        sb.append(", idCommande=").append(idCommande);
        sb.append(", idProduitRevendeur=").append(idProduitRevendeur);
        sb.append(", quantite=").append(quantite);
        sb.append('}');
        return sb.toString();
    }
}
