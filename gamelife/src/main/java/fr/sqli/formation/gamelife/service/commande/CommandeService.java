package fr.sqli.formation.gamelife.service.commande;

import fr.sqli.formation.gamelife.dto.in.CommandeDtoIn;
import fr.sqli.formation.gamelife.dto.in.ItemCommandeDtoIn;
import fr.sqli.formation.gamelife.dto.in.ProduitRevendeurDtoIn;
import fr.sqli.formation.gamelife.dto.out.CommandeDtoOut;
import fr.sqli.formation.gamelife.dto.out.ItemCommandeDtoOut;
import fr.sqli.formation.gamelife.ex.EtatCommandeInvalideException;
import fr.sqli.formation.gamelife.ex.ParameterException;
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


    ItemCommandeDtoOut modifierQuantite(int id, ItemCommandeDtoIn itemCommandeDto) throws CommandeNotFoundException, ItemCommandeNotFoundException, ParameterException, IllegalAccessException;

    // ajouter un produit dans une commande existante (id) sinon cree une nouvelle commande
    ItemCommandeDtoOut ajoutProduit(int id, ItemCommandeDtoIn itemCommandeDto) throws CommandeNotFoundException, ProduitRevendeutException, ParameterException, EtatCommandeInvalideException;



    CommandeDtoOut validerCommande(int id) throws CommandeNotFoundException, ProduitRevendeutException;

    CommandeDtoIn supprimerProduit(int idCommande, int idProduit) throws CommandeNotFoundException, ProduitRevendeutException, ItemCommandeNotFoundException, EtatCommandeInvalideException;


}