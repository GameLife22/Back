package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.in.ProduitDtoIn;
import fr.sqli.formation.gamelife.dto.in.ProduitRevendeurDtoIn;
import fr.sqli.formation.gamelife.dto.out.ProduitDtoOut;
import fr.sqli.formation.gamelife.dto.out.ProduitRevendeurDtoOut;

import java.util.List;

public interface IProduitRevendeurService {
    public ProduitRevendeurDtoOut getProduitRevendeur(Integer pIdUtilisateur, Integer pIdProduitRevendeur);
    public List<ProduitRevendeurDtoOut> getProduitsRevendeur(Integer pIdUtilisateur);
    public ProduitRevendeurDtoOut addProduitRevendeur(Integer pIdUtilisateur, ProduitRevendeurDtoIn pProduitRevendeurDtoIn);
    public ProduitRevendeurDtoOut updateProduitRevendeur(Integer pIdUtilisateur, ProduitRevendeurDtoIn pProduitRevendeurDtoIn);
}