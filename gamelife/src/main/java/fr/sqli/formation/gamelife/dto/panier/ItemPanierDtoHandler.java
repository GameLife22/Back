package fr.sqli.formation.gamelife.dto.panier;

import fr.sqli.formation.gamelife.dto.ProduitDtoHandler;

public class ItemPanierDtoHandler {
    public ItemPanierDto fromEntity(ItemPanierEntity entity) {
        ItemPanierDto dto = new ItemPanierDto();
        dto.setId(ItemPanierPKDtoHandler.fromEntity(entity.getId()));
        dto.setQuantite(entity.getQuantite());
        dto.setProduit(ProduitDtoHandler.fromEntity(entity.getProduit()));
        return dto;
    }

    public ItemPanierEntity toEntity(ItemPanierDto dto) {
        ItemPanierEntity entity = new ItemPanierEntity();
        entity.setId(ItemPanierPKDtoHandler.toEntity(dto.getId()));
        entity.setQuantite(dto.getQuantite());
        // Assurez-vous de configurer correctement le produit à partir du DTO
        entity.setProduit(ProduitDtoHandler.fromDto(dto.getProduit()));
        return entity;
    }
}
