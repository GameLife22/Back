package fr.sqli.formation.gamelife.service.commande;

import fr.sqli.formation.gamelife.dto.commande.CommandeRequete;
import fr.sqli.formation.gamelife.dto.commande.ItemCommandeRequete;
import fr.sqli.formation.gamelife.dto.commande.CommandeReponse;
import fr.sqli.formation.gamelife.dto.commande.ItemCommandeReponse;
import fr.sqli.formation.gamelife.exception.commande.EtatCommandeInvalideException;
import fr.sqli.formation.gamelife.exception.ParameterException;
import fr.sqli.formation.gamelife.exception.ProduitRevendeurException;
import fr.sqli.formation.gamelife.exception.utilisateur.UtilisateurNonExistantException;
import fr.sqli.formation.gamelife.exception.commande.ItemCommandeNotFoundException;
import fr.sqli.formation.gamelife.exception.commande.CommandeNotFoundException;

import java.util.List;
import java.util.UUID;


public interface ICommandeService {
    List<CommandeReponse> getAllCommandes();

    // Recuperer une seule commande
    CommandeReponse getCommande(UUID id) throws CommandeNotFoundException;


   CommandeRequete creerCommande(CommandeRequete commandeDto) throws UtilisateurNonExistantException;

    CommandeRequete modifierCommande(UUID id, CommandeRequete commandeDto) throws CommandeNotFoundException;

    void deleteCommande(UUID id) throws CommandeNotFoundException;

    double getPrixTotalCommande(UUID id) throws CommandeNotFoundException;


    ItemCommandeReponse modifierQuantite(UUID id, ItemCommandeRequete itemCommandeDto) throws CommandeNotFoundException, ItemCommandeNotFoundException, ParameterException, IllegalAccessException;

    // ajouter un produit dans une commande existante (id) sinon cree une nouvelle commande
    ItemCommandeReponse ajoutProduit(UUID id, ItemCommandeRequete itemCommandeDto) throws CommandeNotFoundException, ProduitRevendeurException, ParameterException, EtatCommandeInvalideException;



    CommandeReponse validerCommande(UUID id) throws CommandeNotFoundException, ProduitRevendeurException;

    CommandeRequete supprimerProduit(UUID idCommande, UUID idProduit) throws CommandeNotFoundException, ProduitRevendeurException, ItemCommandeNotFoundException, EtatCommandeInvalideException;


}