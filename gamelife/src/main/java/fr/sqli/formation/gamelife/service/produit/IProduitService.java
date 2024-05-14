package fr.sqli.formation.gamelife.service.produit;

import fr.sqli.formation.gamelife.dto.produit.ProduitRequete;
import fr.sqli.formation.gamelife.dto.produit.ProduitReponse;
import fr.sqli.formation.gamelife.entite.ProduitEntite;

import java.util.List;
import java.util.UUID;

public interface IProduitService {
    public ProduitEntite recupererProduit(UUID pProduitDtoInId);
    public List<ProduitReponse> recupererProduits();
    public ProduitReponse creerProduit(ProduitRequete pProduitRequete);
    public ProduitReponse modifierProduit(UUID pProduitDtoInId, ProduitRequete pProduitRequete);
    public void supprimerProduit(UUID pProduitDtoInId);
}