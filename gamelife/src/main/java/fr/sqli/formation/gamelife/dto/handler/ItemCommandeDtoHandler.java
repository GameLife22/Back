package fr.sqli.formation.gamelife.dto.handler;

import fr.sqli.formation.gamelife.dto.in.ItemCommandeDtoIn;
import fr.sqli.formation.gamelife.dto.out.ItemCommandeDtoOut;
import fr.sqli.formation.gamelife.entity.CommandeEntity;
import fr.sqli.formation.gamelife.entity.ItemCommandeEntity;
import fr.sqli.formation.gamelife.entity.ProduitRevendeurEntity;

public class ItemCommandeDtoHandler {

    public static ItemCommandeEntity DtoToEntity(ItemCommandeDtoIn dto) {
        ItemCommandeEntity itemCommandeEntity = new ItemCommandeEntity();

        CommandeEntity commandeEntity = new CommandeEntity();
        commandeEntity.setId(dto.getIdCommande());
        itemCommandeEntity.setIdCommande(commandeEntity);

        ProduitRevendeurEntity produitRevendeurEntity = new ProduitRevendeurEntity();
        produitRevendeurEntity.setId(dto.getIdProduitRevendeur());
        itemCommandeEntity.setIdProduitRevendeur(produitRevendeurEntity);

        itemCommandeEntity.setQuantite(dto.getQuantite());

        return itemCommandeEntity;
    }


    public static ItemCommandeDtoOut EntityToDto(ItemCommandeEntity entity) {
        ItemCommandeDtoOut itemCommandeDtoOut = new ItemCommandeDtoOut();
        itemCommandeDtoOut.setId(entity.getId());
        itemCommandeDtoOut.setIdCommande(entity.getIdCommande().getId());
        itemCommandeDtoOut.setIdProduitRevendeur(entity.getIdProduitRevendeur().getId());
        itemCommandeDtoOut.setQuantite(entity.getQuantite());
        return itemCommandeDtoOut;
    }
}
