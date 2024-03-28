package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.in.ProduitDtoIn;
import fr.sqli.formation.gamelife.dto.out.ProduitDtoOut;

import java.util.List;
import java.util.UUID;

public interface IProduitService {
    public ProduitDtoOut getProduit(UUID pProduitDtoInId);
    public List<ProduitDtoOut> getProduits();
    public ProduitDtoOut addProduit(ProduitDtoIn pProduitDtoIn);
    public ProduitDtoOut updateProduit(UUID pProduitDtoInId, ProduitDtoIn pProduitDtoIn);
    public ProduitDtoOut disableProduit(UUID pProduitDtoInId);
    public void deleteProduit(UUID pProduitDtoInId);
}