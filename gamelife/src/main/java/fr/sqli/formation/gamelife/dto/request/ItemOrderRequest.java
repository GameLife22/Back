package fr.sqli.formation.gamelife.dto.request;

import java.util.Objects;
import java.util.UUID;

public class ItemOrderRequest {

    private UUID id;
    private UUID idCommande;
    private UUID idProduitRevendeur;
    private Integer quantite;

    // Ajout d'un constructeur avec arguments
    public ItemOrderRequest(UUID idProduitRevendeur, Integer quantite) {
        this.idProduitRevendeur = idProduitRevendeur;
        this.quantite = quantite;
    }
    public UUID getId() {
        return id;
    }

    public void setId(UUID pId) {
        id = pId;
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
        ItemOrderRequest that = (ItemOrderRequest) o;
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
        final StringBuffer sb = new StringBuffer("ItemCommandeDtoIn{");
        sb.append("id=").append(id);
        sb.append(", idCommande=").append(idCommande);
        sb.append(", idProduitRevendeur=").append(idProduitRevendeur);
        sb.append(", quantite=").append(quantite);
        sb.append('}');
        return sb.toString();
    }
}
