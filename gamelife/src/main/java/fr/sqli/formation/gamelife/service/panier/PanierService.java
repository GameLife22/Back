package fr.sqli.formation.gamelife.service.panier;

import fr.sqli.formation.gamelife.dto.panier.ItemPanierDto;
import fr.sqli.formation.gamelife.dto.panier.PanierDto;
import fr.sqli.formation.gamelife.ex.UtilisateurNonExistantException;
import fr.sqli.formation.gamelife.ex.panier.ItemPanierNotFoundException;
import fr.sqli.formation.gamelife.ex.panier.PanierNotFoundException;

import java.util.List;

public interface PanierService {

    // Opérations liées à la gestion d'un panier il y a : la récupération de tous les paniers, la création, la mise à jour et
    // la suppression d'un panier,a modification de la quantité d'articles, le calcul du prix total du panier,
    // l'ajout, la validation et la suppression d'articles.
    List<PanierDto> getAllPaniers();


    PanierDto getPanierById(int id) throws PanierNotFoundException;

    PanierDto createPanier(PanierDto panierDto) throws UtilisateurNonExistantException;

    PanierDto updatePanier(int id, PanierDto panierDto) throws PanierNotFoundException;

    void deletePanier(int id) throws PanierNotFoundException;


    PanierDto modifierQuantite(int id, ItemPanierDto itemPanierDto) throws PanierNotFoundException, ItemPanierNotFoundException;
/*
    double getPrixTotalPanier(int id);

    PanierDto ajoutArticle(int id, ProduitDto produitDto);

    PanierDto validerPanier(int id);

    PanierDto supprimerArticle(int id, ProduitDto produitDto);

     */
}
