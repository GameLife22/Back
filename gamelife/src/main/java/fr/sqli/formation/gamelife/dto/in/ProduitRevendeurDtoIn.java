package fr.sqli.formation.gamelife.dto.in;

import java.math.BigDecimal;

public class ProduitRevendeurDtoIn {

    private Integer stock;
    private BigDecimal prix;
    private String etat;
    private Integer idProduit; // L'IDENTIFIANT DU PRODUIT LIER

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

    public Integer getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Integer idProduit) {
        this.idProduit = idProduit;
    }

}
