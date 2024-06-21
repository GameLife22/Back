package fr.sqli.formation.gamelife.dto.commande;

import java.io.Serial;
import java.util.Objects;
import java.util.UUID;

public class ItemCommandeReponse {
        @Serial
        private static final long serialVersionUID = 1L;

        private UUID id;
        private UUID idCommande;
        private UUID idProduitRevendeur;
        private Integer quantite;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(UUID idCommande) {
        this.idCommande = idCommande;
    }

    public UUID getIdProduitRevendeur() {
        return idProduitRevendeur;
    }

    public void setIdProduitRevendeur(UUID idProduitRevendeur) {
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
        ItemCommandeReponse that = (ItemCommandeReponse) o;
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
