package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.in.ProduitDtoIn;
import fr.sqli.formation.gamelife.dto.out.ProduitDtoOut;

import java.util.List;

public interface IProduitService {
    public ProduitDtoOut getProduit(Integer pIdProduit);
    public List<ProduitDtoOut> getProduits();
    public ProduitDtoOut addProduit(ProduitDtoIn pProduitDtoIn);
    public ProduitDtoOut updateProduit(Integer pIdProduit, ProduitDtoIn pProduitDtoIn);
    public ProduitDtoOut disableProduit(Integer pIdProduit);
    public Boolean deleteProduit(Integer pIdProduit);
}