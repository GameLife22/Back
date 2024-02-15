package fr.sqli.formation.gamelife.service.commande;

import fr.sqli.formation.gamelife.dto.in.CommandeDtoIn;
import fr.sqli.formation.gamelife.dto.in.ItemCommandeDtoIn;
import fr.sqli.formation.gamelife.dto.in.ProduitRevendeurDtoIn;
import fr.sqli.formation.gamelife.dto.out.CommandeDtoOut;
import fr.sqli.formation.gamelife.ex.ProduitRevendeutException;
import fr.sqli.formation.gamelife.ex.UtilisateurNonExistantException;
import fr.sqli.formation.gamelife.ex.commande.ItemCommandeNotFoundException;
import fr.sqli.formation.gamelife.ex.commande.CommandeNotFoundException;

import java.util.List;

public interface CommandeService {
    List<CommandeDtoOut> getAllCommandes();

    // Recuperer une seule commande
    CommandeDtoOut getCommande(int id) throws CommandeNotFoundException;

    CommandeDtoIn createCommande(CommandeDtoIn commandeDto) throws UtilisateurNonExistantException;

    CommandeDtoIn updateCommande(int id, CommandeDtoIn commandeDto) throws CommandeNotFoundException;

    void deleteCommande(int id) throws CommandeNotFoundException;

    double getPrixTotalCommande(int id) throws CommandeNotFoundException;


    CommandeDtoOut modifierQuantite(int id, ItemCommandeDtoIn itemCommandeDto) throws CommandeNotFoundException, ItemCommandeNotFoundException;


    CommandeDtoOut ajoutArticle(int id, ProduitRevendeurDtoIn produitRevendeurDto) throws ProduitRevendeutException, CommandeNotFoundException;
/*
    CommandeDtoIn validerCommande(int id) throws CommandeNotFoundException;

    CommandeDtoIn supprimerArticle(int idCommande, int idProduit) throws CommandeNotFoundException, ProduitRevendeutException, ItemCommandeNotFoundException;

 */
}