package fr.sqli.formation.gamelife.dto.handler;

import fr.sqli.formation.gamelife.dto.in.ProduitDtoIn;
import fr.sqli.formation.gamelife.dto.out.ProduitDtoOut;
import fr.sqli.formation.gamelife.entity.ProduitEntity;

public class ProduitDtoHandler {

    public ProduitEntity DtoToEntity(ProduitDtoIn produitDtoIn) {
        ProduitEntity produitEntity = new ProduitEntity();
        produitEntity.setNom(produitDtoIn.getNom());
        produitEntity.setDescription(produitDtoIn.getDescription());
        produitEntity.setCategorie(produitDtoIn.getCategorie());
        produitEntity.setPlateforme(produitDtoIn.getPlateforme());
        produitEntity.setEtat(produitDtoIn.getEtat());
        return produitEntity;
    }

    public ProduitDtoOut EntityToDto(ProduitEntity produitEntity) {
        ProduitDtoOut produitDtoOut = new ProduitDtoOut();
        produitDtoOut.setId(produitEntity.getId());
        produitDtoOut.setNom(produitEntity.getNom());
        produitDtoOut.setDescription(produitEntity.getDescription());
        produitDtoOut.setCategorie(produitEntity.getCategorie());
        produitDtoOut.setPlateforme(produitEntity.getPlateforme());
        produitDtoOut.setEtat(produitEntity.getEtat());
        return produitDtoOut;
    }
}
