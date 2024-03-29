package fr.sqli.formation.gamelife.dto.handler;

import fr.sqli.formation.gamelife.dto.in.ProduitRevendeurDtoIn;
import fr.sqli.formation.gamelife.dto.out.ProduitDtoOut;
import fr.sqli.formation.gamelife.dto.out.ProduitRevendeurDtoOut;
import fr.sqli.formation.gamelife.entity.ProduitEntity;
import fr.sqli.formation.gamelife.entity.ProduitRevendeurEntity;
import fr.sqli.formation.gamelife.entity.UtilisateurEntity;

public class ProduitRevendeurDtoHandler {

    public static ProduitRevendeurEntity DtoToEntity(ProduitRevendeurDtoIn produitRevendeurDtoIn) {
        ProduitRevendeurEntity produitRevendeurEntity = new ProduitRevendeurEntity();
        produitRevendeurEntity.setStock(produitRevendeurDtoIn.getStock());
        produitRevendeurEntity.setPrix(produitRevendeurDtoIn.getPrix());
        produitRevendeurEntity.setEtat(produitRevendeurDtoIn.getEtat());

        ProduitEntity produitEntity = new ProduitEntity();
        produitEntity.setId(produitRevendeurDtoIn.getIdProduit());
        produitRevendeurEntity.setIdProduit(produitEntity);
        produitRevendeurEntity.setIdUtilisateur(new UtilisateurEntity());

        return produitRevendeurEntity;
    }


    public static ProduitRevendeurDtoOut EntityToDto(ProduitRevendeurEntity produitRevendeurEntity) {
        ProduitRevendeurDtoOut produitRevendeurDtoOut = new ProduitRevendeurDtoOut();
        produitRevendeurDtoOut.setId(produitRevendeurEntity.getId());
        produitRevendeurDtoOut.setStock(produitRevendeurEntity.getStock());
        produitRevendeurDtoOut.setPrix(produitRevendeurEntity.getPrix());
        produitRevendeurDtoOut.setEtat(produitRevendeurEntity.getEtat());

        ProduitDtoHandler produitDtoHandler = new ProduitDtoHandler();
        ProduitDtoOut produitDtoOut = produitDtoHandler.EntityToDto(produitRevendeurEntity.getIdProduit());
        produitRevendeurDtoOut.setProduit(produitDtoOut);


        return produitRevendeurDtoOut;
    }
}
