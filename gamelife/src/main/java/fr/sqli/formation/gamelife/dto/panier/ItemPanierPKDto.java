package fr.sqli.formation.gamelife.dto.panier;

public class ItemPanierPKDto {

    //clé primaire d'un élément dans le panier.
    private int idProduit;
    private int idPanier;

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public int getIdPanier() {
        return idPanier;
    }

    public void setIdPanier(int idPanier) {
        this.idPanier = idPanier;
    }
}
