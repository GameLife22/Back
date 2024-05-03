package fr.sqli.formation.gamelife.service.produit;

import fr.sqli.formation.gamelife.dto.produit.ProduitRevendeurReponse;

import java.util.List;
import java.util.UUID;

public interface IProduitRevendeurService {
    public List<ProduitRevendeurReponse> recupererProduitRevendeursParProduit(UUID pProduitId);
}
