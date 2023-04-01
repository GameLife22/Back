package fr.sqli.formation.gamelife.dto.panier;

import fr.sqli.formation.gamelife.entity.PanierPK;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class PanierDtoIn {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LogManager.getLogger();



    private Integer id_commande;
    private Integer id_produit;
    private Integer quantite;

    public PanierDtoIn() {
    }
    public PanierDtoIn(Integer id_commande , Integer id_produit , Integer quantite) {
        PanierPK id = new PanierPK();
        id.setIdProduit(id_produit);
        id.setIdCommande(id_commande);
        this.id_produit = id_produit;
        this.id_commande = id_commande;
        this.quantite = quantite;
    }

    public PanierDtoIn(Integer id_commande, Integer id_produit) {
        this.id_commande = id_commande;
        this.id_produit = id_produit;
    }

    public Integer getId_commande() {
        return id_commande;
    }

    public void setId_commande(Integer id_commande) {
        this.id_commande = id_commande;
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
