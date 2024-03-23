package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.in.ProduitDtoIn;
import fr.sqli.formation.gamelife.dto.out.ProduitDtoOut;

import java.util.List;
import java.util.UUID;

//todo: javadoc
public interface IProduitService {
    public ProduitDtoOut getProduit(UUID pIdProduit);
    public List<ProduitDtoOut> getProduits();
    public ProduitDtoOut addProduit(ProduitDtoIn pProduitDtoIn);
    public ProduitDtoOut updateProduit(ProduitDtoIn pProduitDtoIn);
    //todo: disable produit
    public void deleteProduit(UUID pIdProduit);
}