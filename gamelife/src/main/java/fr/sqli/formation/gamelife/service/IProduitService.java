package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.in.ProduitDtoIn;
import fr.sqli.formation.gamelife.dto.out.ProduitDtoOut;

import java.util.List;

//todo: javadoc
public interface IProduitService {
    public ProduitDtoOut getProduit(Integer pIdProduit);
    public List<ProduitDtoOut> getProduits();
    public ProduitDtoOut addProduit(ProduitDtoIn pProduitDtoIn);
    public ProduitDtoOut updateProduit(ProduitDtoIn pProduitDtoIn);
    public void deleteProduit(Integer pIdProduit);
}