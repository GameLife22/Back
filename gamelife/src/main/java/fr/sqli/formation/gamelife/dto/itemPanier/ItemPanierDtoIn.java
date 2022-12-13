package fr.sqli.formation.gamelife.dto.itemPanier;

import fr.sqli.formation.gamelife.entity.ItemPanierPK;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ItemPanierDtoIn {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LogManager.getLogger();



    private Integer id_panier;
    private Integer id_produit;
    private Integer quantite;

    public ItemPanierDtoIn() {
    }
    public ItemPanierDtoIn(Integer id_panier , Integer id_produit , Integer quantite) {
        ItemPanierPK id = new ItemPanierPK();
        id.setIdProduit(id_produit);
        id.setIdPanier(id_panier);
        this.id_produit = id_produit;
        this.id_panier = id_panier;
        this.quantite = quantite;
    }

    public ItemPanierDtoIn(Integer id_panier, Integer id_produit) {
        this.id_panier = id_panier;
        this.id_produit = id_produit;
    }

    public Integer getId_panier() {
        return id_panier;
    }

    public void setId_panier(Integer id_panier) {
        this.id_panier = id_panier;
    }

    public Integer getId_produit() {
        return id_produit;
    }

    public void setId_produit(Integer id_produit) {
        this.id_produit = id_produit;
    }


    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }
}
