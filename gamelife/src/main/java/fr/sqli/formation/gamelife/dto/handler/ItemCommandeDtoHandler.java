package fr.sqli.formation.gamelife.dto.handler;

import fr.sqli.formation.gamelife.dto.in.ItemCommandeDtoIn;
import fr.sqli.formation.gamelife.dto.out.ItemCommandeDtoOut;
import fr.sqli.formation.gamelife.entity.CommandeEntity;
import fr.sqli.formation.gamelife.entity.ItemCommandeEntity;
import fr.sqli.formation.gamelife.entity.ProduitRevendeurEntity;

public class ItemCommandeDtoHandler {

    public static ItemCommandeEntity DtoToEntity(ItemCommandeDtoIn dto) {
        ItemCommandeEntity entity = new ItemCommandeEntity();

        CommandeEntity commandeEntity = new CommandeEntity();
        commandeEntity.setId(dto.getIdCommande());
        entity.setIdCommande(commandeEntity);

        ProduitRevendeurEntity produitRevendeurEntity = new ProduitRevendeurEntity();
        produitRevendeurEntity.setId(dto.getIdProduitRevendeur());
        entity.setIdProduitRevendeur(produitRevendeurEntity);

        entity.setQuantite(dto.getQuantite());

        return entity;
    }


    public static ItemCommandeDtoOut EntityToDto(ItemCommandeEntity entity) {
        ItemCommandeDtoOut dto = new ItemCommandeDtoOut();
        dto.setId(entity.getId());
        dto.setIdCommande(entity.getIdCommande().getId());
        dto.setIdProduitRevendeur(entity.getIdProduitRevendeur().getId());
        dto.setQuantite(entity.getQuantite());
        return dto;
    }
}
