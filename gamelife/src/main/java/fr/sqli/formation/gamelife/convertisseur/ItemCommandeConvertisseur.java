package fr.sqli.formation.gamelife.convertisseur;

import fr.sqli.formation.gamelife.dto.commande.ItemCommandeRequete;
import fr.sqli.formation.gamelife.dto.commande.ItemCommandeReponse;
import fr.sqli.formation.gamelife.entite.CommandeEntite;
import fr.sqli.formation.gamelife.entite.ItemCommandeEntite;
import fr.sqli.formation.gamelife.entite.ProduitRevendeurEntite;

public class ItemCommandeConvertisseur {

    public static ItemCommandeEntite DtoToEntity(ItemCommandeRequete dto) {
        ItemCommandeEntite itemCommandeEntite = new ItemCommandeEntite();

        CommandeEntite commandeEntite = new CommandeEntite();
        commandeEntite.setId(dto.getIdCommande());
        itemCommandeEntite.setCommande(commandeEntite);

        ProduitRevendeurEntite produitRevendeurEntite = new ProduitRevendeurEntite();
        produitRevendeurEntite.setId(dto.getIdProduitRevendeur());
        itemCommandeEntite.setProduitRevendeur(produitRevendeurEntite);
        itemCommandeEntite.setQuantite(dto.getQuantite());

        return itemCommandeEntite;
    }


    public static ItemCommandeReponse EntityToDto(ItemCommandeEntite entity) {
        ItemCommandeReponse itemCommandeReponse = new ItemCommandeReponse();
        itemCommandeReponse.setId(entity.getId());
        itemCommandeReponse.setIdCommande(entity.getCommande().getId());
        itemCommandeReponse.setIdProduitRevendeur(entity.recupererProduitRevendeur().getId());
        itemCommandeReponse.setQuantite(entity.getQuantite());

        return itemCommandeReponse;



    }
}
