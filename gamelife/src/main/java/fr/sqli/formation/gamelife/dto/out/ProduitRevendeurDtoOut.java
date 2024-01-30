package fr.sqli.formation.gamelife.dto.out;

import java.math.BigDecimal;

public class ProduitRevendeurDtoOut {

    private Integer id;
    private Integer stock;
    private BigDecimal prix;
    private String etat;
    private ProduitDtoOut produit; // la représentation du produit lier

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

    public ProduitDtoOut getProduit() {
        return produit;
    }

    public void setProduit(ProduitDtoOut produit) {
        this.produit = produit;
    }
}
