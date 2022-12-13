package fr.sqli.formation.gamelife.dto.panier;


import fr.sqli.formation.gamelife.entity.PanierEntity;
import fr.sqli.formation.gamelife.entity.PanierPK;


public class PanierDtoHandler {
    public static PanierEntity toEntity(PanierDtoIn pDto) {
        var result = new PanierEntity();
        PanierPK id = new PanierPK();
        id.setIdProduit(pDto.getId_produit());
        id.setIdCommande(pDto.getId_commande());
        result.setId(id);
        result.setQuantite(pDto.getQuantite());
        return result;
    }



}

