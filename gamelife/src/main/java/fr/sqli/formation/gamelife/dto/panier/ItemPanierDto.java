package fr.sqli.formation.gamelife.dto.panier;

import fr.sqli.formation.gamelife.dto.ProduitDto;

public class ItemPanierDto {

    //les informations liées à un élément dans un panier
    private ItemPanierPKDto id;
    private int quantite;
    private ProduitDto produit;

    public ItemPanierPKDto getId() {
        return id;
    }

    public void setId(ItemPanierPKDto id) {
        this.id = id;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public ProduitDto getProduit() {
        return produit;
    }

    public void setProduit(ProduitDto produit) {
        this.produit = produit;
    }
}
