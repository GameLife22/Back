package fr.sqli.formation.gamelife.dto.panier;

import fr.sqli.formation.gamelife.dto.ProduitDto;

public class ItemPanierDto {

    //les informations liées à un élément dans un panier
    private ItemPanierPKDto id;
    private int quantite;
    private ProduitDto produitDto;

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

    public ProduitDto getProduitDto() {
        return produitDto;
    }

    public void setProduitDto(ProduitDto produitDto) {
        this.produitDto = produitDto;
    }
}
