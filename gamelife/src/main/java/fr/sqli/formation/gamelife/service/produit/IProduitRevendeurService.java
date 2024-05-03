package fr.sqli.formation.gamelife.service.produit;

import fr.sqli.formation.gamelife.dto.produit.ProduitRevendeurReponse;

import java.util.UUID;

public interface IProduitRevendeurService {
    public ProduitRevendeurReponse recupererProduitRevendeur(UUID pIdProduitRevendeur);
}
