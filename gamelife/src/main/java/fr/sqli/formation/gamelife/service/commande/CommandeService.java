package fr.sqli.formation.gamelife.service.commande;

import fr.sqli.formation.gamelife.dto.in.CommandeDtoIn;
import fr.sqli.formation.gamelife.dto.in.ItemCommandeDtoIn;
import fr.sqli.formation.gamelife.dto.in.ProduitRevendeurDtoIn;
import fr.sqli.formation.gamelife.ex.ProduitRevendeutException;
import fr.sqli.formation.gamelife.ex.UtilisateurNonExistantException;
import fr.sqli.formation.gamelife.ex.commande.ItemCommandeNotFoundException;
import fr.sqli.formation.gamelife.ex.commande.CommandeNotFoundException;

import java.util.List;

public interface CommandeService {
    List<CommandeDtoIn> getAllCommandes();

    CommandeDtoIn getCommandeById(int id) throws CommandeNotFoundException;

    CommandeDtoIn createCommande(CommandeDtoIn commandeDto) throws UtilisateurNonExistantException;

    CommandeDtoIn updateCommande(int id, CommandeDtoIn commandeDto) throws CommandeNotFoundException;

    void deleteCommande(int id) throws CommandeNotFoundException;

    CommandeDtoIn modifierQuantite(int id, ItemCommandeDtoIn itemCommandeDto) throws CommandeNotFoundException, ItemCommandeNotFoundException;

    double getPrixTotalCommande(int id);

    CommandeDtoIn ajoutArticle(int id, ProduitRevendeurDtoIn produitRevendeurDto) throws ProduitRevendeutException, CommandeNotFoundException;

    CommandeDtoIn validerCommande(int id) throws CommandeNotFoundException;

    CommandeDtoIn supprimerArticle(int idCommande, int idProduit) throws CommandeNotFoundException, ProduitRevendeutException, ItemCommandeNotFoundException;
}
