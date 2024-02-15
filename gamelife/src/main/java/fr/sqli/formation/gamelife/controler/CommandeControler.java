package fr.sqli.formation.gamelife.controler;

import fr.sqli.formation.gamelife.dto.in.CommandeDtoIn;
import fr.sqli.formation.gamelife.dto.handler.CommandeDtoHandler;
import fr.sqli.formation.gamelife.dto.in.ItemCommandeDtoIn;
import fr.sqli.formation.gamelife.dto.in.ProduitRevendeurDtoIn;
import fr.sqli.formation.gamelife.dto.out.CommandeDtoOut;
import fr.sqli.formation.gamelife.entity.CommandeEntity;
import fr.sqli.formation.gamelife.ex.ProduitRevendeutException;
import fr.sqli.formation.gamelife.ex.UtilisateurNonExistantException;
import fr.sqli.formation.gamelife.ex.commande.ItemCommandeNotFoundException;
import fr.sqli.formation.gamelife.ex.commande.CommandeNotFoundException;
import fr.sqli.formation.gamelife.repository.CommandeRepository;
import fr.sqli.formation.gamelife.service.commande.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/commande")
public class CommandeControler {

    @Autowired
    private CommandeService commandeService;
    @Autowired
    private CommandeRepository commandeRepository;
    private CommandeDtoHandler commandeDtoHandler;

    // Recuperer une seule commande
    @GetMapping("/{id}")
    public ResponseEntity<CommandeDtoOut> getCommande(@PathVariable int id) throws CommandeNotFoundException {
        CommandeDtoOut commandeDto = commandeService.getCommande(id);
        return ResponseEntity.status(commandeDto != null ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(commandeDto);
    }

    // Afficher le prix total du commande
    @GetMapping("/{id}/prix-total")
    public double getPrixTotalPanier(@PathVariable int id) throws CommandeNotFoundException {
        return commandeService.getPrixTotalCommande(id);
    }


    // Crée une commande pour un utilisateur
    @PostMapping("/creer")
    public ResponseEntity<CommandeDtoIn> createCommande(@RequestBody CommandeDtoIn commandeDto) {
        try {
            CommandeDtoIn createdCommande = commandeService.createCommande(commandeDto);
            return new ResponseEntity<>(createdCommande, HttpStatus.CREATED);
        } catch (UtilisateurNonExistantException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non existant", e);
        }
    }

    // Modifier une commande
    @PutMapping("/{id}")
    public ResponseEntity<CommandeDtoIn> updateCommande(@PathVariable int id, @RequestBody CommandeDtoIn commandeDto) throws CommandeNotFoundException {

        CommandeDtoIn updatedCommandeDto = commandeService.updateCommande(id, commandeDto);
        return new ResponseEntity<>(updatedCommandeDto, HttpStatus.OK);
    }

    // Supprimer une commande
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCommande(@PathVariable("id") int id) {
        try {
            commandeService.deleteCommande(id);
            return new ResponseEntity<>("La commande a été supprimée avec succès", HttpStatus.OK);
        } catch (CommandeNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Modifier la quantité d'un item dans une commande
    @PutMapping("/{id}/modif-quantite")
    public ResponseEntity<CommandeDtoOut> modifierQuantite(@PathVariable int id, @RequestBody ItemCommandeDtoIn itemCommandeDto) throws CommandeNotFoundException, ItemCommandeNotFoundException {
        CommandeDtoOut commandeDto = commandeService.modifierQuantite(id, itemCommandeDto);

        return new ResponseEntity<>( commandeDto, HttpStatus.OK);
    }

    @PostMapping("/{id}/ajout-article")
    public ResponseEntity<CommandeDtoOut> ajoutArticle(@PathVariable("id") int id, @RequestBody ProduitRevendeurDtoIn
            produitRevendeurDto) {
        try {
            CommandeDtoOut commandeDtoOut = commandeService.ajoutArticle(id, produitRevendeurDto);
            return new ResponseEntity<>(commandeDtoOut, HttpStatus.OK);
        } catch (ProduitRevendeutException e) {
            // Gérer l'exception ProduitRevendeutException
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (CommandeNotFoundException e) {
            // Gérer l'exception CommandeNotFoundException
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*


    // Ajouter un item dans une commande
    @PostMapping("/{id}/ajout-article")
    public CommandeDtoOut ajoutArticle(@PathVariable int id, @RequestBody ProduitDto produitDto) throws ProduitRevendeutException, CommandeNotFoundException {
        return commandeService.ajoutArticle(id, produitDto);
    }

    // Valider une commande
    @PostMapping("/{id}/valider-commande")
    public ResponseEntity<CommandeDtoIn> validerPanier(@PathVariable int id) throws CommandeNotFoundException {
        CommandeDtoIn commandeDto = commandeService.validerPanier(id);
        return new ResponseEntity<>(commandeDto, HttpStatus.OK);
    }
    // Supprimer un item dans une commande
    @DeleteMapping("/{idPanier}/supp-article/{idProduit}")
    public ResponseEntity<String> supprimerArticle(@PathVariable int idPanier, @PathVariable int idProduit) throws CommandeNotFoundException, ItemCommandeNotFoundException, ProduitRevendeutException {
        commandeService.supprimerArticle(idPanier, idProduit);
        return new ResponseEntity<>("Article supprimé avec succès", HttpStatus.OK);
    }

     */

}
