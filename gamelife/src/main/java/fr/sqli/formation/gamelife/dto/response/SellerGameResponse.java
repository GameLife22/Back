package fr.sqli.formation.gamelife.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public class SellerGameResponse {

    private UUID id;
    private Integer stock;
    private BigDecimal prix;
    private String etat;
    private GameResponse produit; // la représentation du produit lier

    public UUID getId() {
        return id;
    }

    public void setId(UUID pId) {
        id = pId;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer pStock) {
        stock = pStock;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal pPrix) {
        prix = pPrix;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String pEtat) {
        etat = pEtat;
    }

    public GameResponse recupererProduit() {
        return produit;
    }

    public void setProduit(GameResponse pProduit) {
        produit = pProduit;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ProduitRevendeurDtoOut{");
        sb.append("id=").append(id);
        sb.append(", stock=").append(stock);
        sb.append(", prix=").append(prix);
        sb.append(", etat='").append(etat).append('\'');
        sb.append(", produit=").append(produit);
        sb.append('}');
        return sb.toString();
    }
}
