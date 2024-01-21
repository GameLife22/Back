package fr.sqli.formation.gamelife.controler;

import fr.sqli.formation.gamelife.dto.in.CommandeDtoIn;
import fr.sqli.formation.gamelife.dto.handler.CommandeDtoHandler;
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

@RestController
@RequestMapping("/commande")
public class CommandeControler {

    @Autowired
    private CommandeService commandeService;
    @Autowired
    private CommandeRepository commandeRepository;
    @Autowired
    private CommandeDtoHandler commandeDtoHandler;

    // Recuperer une seule commande
    @GetMapping("/{id}")
    public CommandeDtoOut getPanierById(@PathVariable int id) throws CommandeNotFoundException {
        CommandeEntity commandeEntity = commandeRepository.findByIdWithItemPaniers(id)
                .orElseThrow(() -> new CommandeNotFoundException("Commande not found with id: " + id));
        return commandeDtoHandler.entityToDto(commandeEntity);
    }

    // Crée une commande
    @PostMapping("/creer")
    public ResponseEntity<CommandeDtoIn> createPanier(@RequestBody CommandeDtoIn commandeDto) throws UtilisateurNonExistantException {
        CommandeDtoIn createdPanier = commandeService.createPanier(commandeDto);
        // Retourne un code HTTP 201 Created avec le corps contenant le commande créé
        return new ResponseEntity<>(createdPanier, HttpStatus.CREATED);
    }

    // Mise a jour d'une commande
    @PutMapping("/{id}")
    public ResponseEntity<CommandeDtoIn> updatePanier(@PathVariable int id, @RequestBody CommandeDtoIn commandeDto) throws CommandeNotFoundException {
        CommandeDtoIn updatedPanier = commandeService.updatePanier(id, commandeDto);
        // Si le commande est trouvé et mis à jour, retourne un code HTTP OK
        // Sinon, retourne un code HTTP 404 Not Found si le commande n'est pas trouvé
        return updatedPanier != null ? new ResponseEntity<>(updatedPanier, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    // Supprimer une commande
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePanier(@PathVariable int id) throws CommandeNotFoundException {
        commandeService.deletePanier(id);
        return ResponseEntity.noContent().build();
    }
    // Modifier la quantite du produit
    @PatchMapping("/{id}/modif-quantite")
    public CommandeDtoOut modifierQuantite(@PathVariable int id, @RequestBody fr.sqli.formation.gamelife.dto.commande.ItemCommandeDto itemCommandeDto) throws CommandeNotFoundException, ItemCommandeNotFoundException {
        return commandeService.modifierQuantite(id, itemCommandeDto);
    }

    // Afficher le prix total du commande
    @GetMapping("/{id}/prix-total")
    public double getPrixTotalPanier(@PathVariable int id) {
        return commandeService.getPrixTotalPanier(id);
    }

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
}

