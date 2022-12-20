package fr.sqli.formation.gamelife.dto.panier;


import fr.sqli.formation.gamelife.entity.ItemPanierPK;
import fr.sqli.formation.gamelife.entity.PanierEntity;


public class PanierDtoHandler {
    public static PanierEntity toEntity(PanierDtoIn pDto) {
        var result = new PanierEntity();
        ItemPanierPK id = new ItemPanierPK();
        id.setIdProduit(pDto.getId_produit());
        id.setIdPanier(pDto.getId_panier());
        result.setId(id.getIdPanier());
        return result;
    }



}

