package fr.sqli.formation.gamelife.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

public class SellerGameRequest {

    private UUID id;
    private Integer stock;
    private BigDecimal prix;
    private String etat;
    private Integer idProduit; // L'IDENTIFIANT DU PRODUIT LIER

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

    public Integer getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Integer pId) {
        idProduit = pId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ProduitRevendeurDtoIn{");
        sb.append("id=").append(id);
        sb.append(", stock=").append(stock);
        sb.append(", prix=").append(prix);
        sb.append(", etat='").append(etat).append('\'');
        sb.append(", idProduit=").append(idProduit);
        sb.append('}');
        return sb.toString();
    }
}
