package fr.sqli.formation.gamelife.service.commande;

import fr.sqli.formation.gamelife.dto.ProduitRevendeurDto;
import fr.sqli.formation.gamelife.dto.commande.ItemCommandeDto;
import fr.sqli.formation.gamelife.dto.commande.CommandeDto;
import fr.sqli.formation.gamelife.ex.ProduitRevendeutException;
import fr.sqli.formation.gamelife.ex.UtilisateurNonExistantException;
import fr.sqli.formation.gamelife.ex.commande.ItemCommandeNotFoundException;
import fr.sqli.formation.gamelife.ex.commande.CommandeNotFoundException;

import java.util.List;

public interface CommandeService {
    // Opérations liées à la gestion d'un commande il y a : la récupération de tous les commandes, la création, la mise à jour et
    // la suppression d'un commande,a modification de la quantité d'articles, le calcul du prix total du commande,
    // l'ajout, la validation et la suppression d'articles.
    List<CommandeDto> getAllCommandes();

    CommandeDto getCommandeById(int id) throws CommandeNotFoundException;

    CommandeDto createCommande(CommandeDto commandeDto) throws UtilisateurNonExistantException;

    CommandeDto updateCommande(int id, CommandeDto commandeDto) throws CommandeNotFoundException;

    void deleteCommande(int id) throws CommandeNotFoundException;

    CommandeDto modifierQuantite(int id, ItemCommandeDto itemCommandeDto) throws CommandeNotFoundException, ItemCommandeNotFoundException;

    double getPrixTotalCommande(int id);

    CommandeDto ajoutArticle(int id, ProduitRevendeurDto produitRevendeurDto) throws ProduitRevendeutException, CommandeNotFoundException;

    CommandeDto validerCommande(int id) throws CommandeNotFoundException;

    CommandeDto supprimerArticle(int idCommande, int idProduit) throws CommandeNotFoundException, ProduitRevendeutException, ItemCommandeNotFoundException;
}
