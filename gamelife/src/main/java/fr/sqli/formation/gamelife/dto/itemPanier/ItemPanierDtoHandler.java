package fr.sqli.formation.gamelife.dto.itemPanier;


import fr.sqli.formation.gamelife.entity.ItemPanierEntity;
import fr.sqli.formation.gamelife.entity.ItemPanierPK;


public class ItemPanierDtoHandler {
    public static ItemPanierEntity toEntity(ItemPanierDtoIn pDto) {
        var result = new ItemPanierEntity();
        ItemPanierPK id = new ItemPanierPK();
        id.setIdProduit(pDto.getId_produit());
        id.setIdPanier(pDto.getId_panier());
        result.setId(id);
        result.setQuantite(pDto.getQuantite());
        return result;
    }

    public static ItemPanierDtoOut fromEntity(ItemPanierEntity pEntity) {
        var resu = new ItemPanierDtoOut();
        resu.setId_panier(pEntity.getPanier().getId());
        resu.setId_produit(pEntity.getProduit().getId());
        resu.setQuantite(pEntity.getQuantite());
        return resu;
    }



}

