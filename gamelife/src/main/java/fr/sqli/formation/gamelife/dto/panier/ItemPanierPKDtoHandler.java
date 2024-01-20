package fr.sqli.formation.gamelife.dto.panier;

import fr.sqli.formation.gamelife.entity.ItemPanierPK;

public class ItemPanierPKDtoHandler {

    public static ItemPanierPKDto fromEntity(ItemPanierPK entity) {
        ItemPanierPKDto dto = new ItemPanierPKDto();
        dto.setIdPanier(entity.getIdPanier());
        dto.setIdProduit(entity.getIdProduit());
        return dto;
    }

    public static ItemPanierPK toEntity(ItemPanierPKDto dto) {
        ItemPanierPK entity = new ItemPanierPK();
        entity.setIdPanier(dto.getIdPanier());
        entity.setIdProduit(dto.getIdProduit());
        return entity;
    }
}
