package fr.sqli.formation.gamelife.controler;

import fr.sqli.formation.gamelife.dto.in.CommandeDtoIn;
import fr.sqli.formation.gamelife.dto.handler.CommandeDtoHandler;
import fr.sqli.formation.gamelife.dto.in.ItemCommandeDtoIn;
import fr.sqli.formation.gamelife.dto.in.ProduitRevendeurDtoIn;
import fr.sqli.formation.gamelife.dto.out.CommandeDtoOut;
import fr.sqli.formation.gamelife.dto.out.ExceptionDtoOut;
import fr.sqli.formation.gamelife.dto.out.ItemCommandeDtoOut;
import fr.sqli.formation.gamelife.entity.CommandeEntity;
import fr.sqli.formation.gamelife.ex.EtatCommandeInvalideException;
import fr.sqli.formation.gamelife.ex.ParameterException;
import fr.sqli.formation.gamelife.ex.ProduitRevendeutException;
import fr.sqli.formation.gamelife.ex.UtilisateurNonExistantException;
import fr.sqli.formation.gamelife.ex.commande.ItemCommandeNotFoundException;
import fr.sqli.formation.gamelife.ex.commande.CommandeNotFoundException;
import fr.sqli.formation.gamelife.repository.CommandeRepository;
import fr.sqli.formation.gamelife.service.commande.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
;

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
    public ResponseEntity<?> modifierQuantite(@PathVariable("id") int id,
                                              @RequestBody ItemCommandeDtoIn itemCommandeDto) {
        try {
            ItemCommandeDtoOut commandeDtoOut = commandeService.modifierQuantite(id, itemCommandeDto);
            return ResponseEntity.ok(commandeDtoOut);
        } catch (CommandeNotFoundException | ItemCommandeNotFoundException | IllegalAccessException e) {
            // Gérer les exceptions spécifiques
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDtoOut(e.getMessage()));
        } catch (ParameterException e) {
            // Capturer les exceptions de paramètre et renvoyer une réponse BadRequest
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionDtoOut(e.getMessage()));
        } catch (Exception e) {
            // Capturer les autres exceptions et renvoyer une réponse BadRequest
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionDtoOut("Une erreur s'est produite lors du traitement de la demande."));
        }
    }

    // Ajouter un produit dans une commande existante (id) sinon créer une nouvelle commande
    @PutMapping("/{id}/ajout-produit")
    public ResponseEntity<ItemCommandeDtoOut> ajoutProduit(@PathVariable("id") int idUtilisateur,
                                                           @RequestBody ItemCommandeDtoIn itemCommandeDto) {
        try {
            ItemCommandeDtoOut itemCommandeDtoOut = commandeService.ajoutProduit(idUtilisateur, itemCommandeDto);
            return ResponseEntity.ok(itemCommandeDtoOut);
        } catch (ProduitRevendeutException | ParameterException  |
                 CommandeNotFoundException | EtatCommandeInvalideException e) {
            // Gérer les exceptions
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }







    // Valider une commande (changer l'état de la commande)
    @PutMapping("/{id}/valider-commande")
    public ResponseEntity<?> validerCommande(@PathVariable("id") int id) {
        try {
            CommandeDtoOut commandeDtoOut = commandeService.validerCommande(id);

            return ResponseEntity.ok(commandeDtoOut);
        } catch (CommandeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDtoOut(e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionDtoOut(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionDtoOut("Une erreur s'est produite lors de la validation de la commande."));
        }
    }





    // Supprimer un item dans une commande
    @DeleteMapping("/{idCommande}/supp-article/{idProduit}")
    public ResponseEntity<?> supprimerProduit(@PathVariable("idCommande") int idCommande,
                                             @PathVariable("idProduit") int idProduit) {
        try {
            CommandeDtoIn commandeDtoIn = commandeService.supprimerProduit(idCommande, idProduit);
            return ResponseEntity.ok(commandeDtoIn);
        } catch (CommandeNotFoundException | ProduitRevendeutException | ItemCommandeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDtoOut(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionDtoOut("Une erreur s'est produite lors de la suppression de l'article."));
        }
    }


}
