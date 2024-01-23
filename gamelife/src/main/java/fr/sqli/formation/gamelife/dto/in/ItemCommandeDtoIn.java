package fr.sqli.formation.gamelife.dto.in;

import java.io.Serial;
import java.util.Objects;

public class ItemCommandeDtoIn {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer idCommande;
    private Integer idProduitRevendeur;
    private Integer quantite;

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
        ItemCommandeDtoIn that = (ItemCommandeDtoIn) o;
        return Objects.equals(idCommande, that.idCommande) &&
                Objects.equals(idProduitRevendeur, that.idProduitRevendeur) &&
                Objects.equals(quantite, that.quantite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCommande, idProduitRevendeur, quantite);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ItemCommandeDtoIn{");
        sb.append("idCommande=").append(idCommande);
        sb.append(", idProduitRevendeur=").append(idProduitRevendeur);
        sb.append(", quantite=").append(quantite);
        sb.append('}');
        return sb.toString();
    }
}
