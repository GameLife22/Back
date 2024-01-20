package fr.sqli.formation.gamelife.controler;

import fr.sqli.formation.gamelife.dto.ProduitDto;
import fr.sqli.formation.gamelife.dto.panier.ItemPanierDto;
import fr.sqli.formation.gamelife.dto.panier.PanierDto;
import fr.sqli.formation.gamelife.dto.panier.PanierDtoHandler;
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
import java.util.List;

@RestController
@RequestMapping("/panier")
public class PanierControler {

    @Autowired
    private CommandeService commandeService;
    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private PanierDtoHandler panierDtoHandler;

    // Recuperer tous les paniers
    @GetMapping("/all")
    public List<PanierDto> getAllPaniers() {
        return commandeService.getAllPaniers();
    }

    // Recuperer un seul panier
    @GetMapping("/{id}")
    public PanierDto getPanierById(@PathVariable int id) throws CommandeNotFoundException {
        PanierEntity panierEntity = commandeRepository.findByIdWithItemPaniers(id)
                .orElseThrow(() -> new CommandeNotFoundException("Panier not found with id: " + id));
        return panierDtoHandler.entityToDto(panierEntity);
    }

    // Crée un panier
    @PostMapping("/creer")
    public ResponseEntity<PanierDto> createPanier(@RequestBody PanierDto panierDto) throws UtilisateurNonExistantException {
        PanierDto createdPanier = commandeService.createPanier(panierDto);
        // Retourne un code HTTP 201 Created avec le corps contenant le panier créé
        return new ResponseEntity<>(createdPanier, HttpStatus.CREATED);
    }

    // Mise a jour du panier
    @PutMapping("/{id}")
    public ResponseEntity<PanierDto> updatePanier(@PathVariable int id, @RequestBody PanierDto panierDto) throws CommandeNotFoundException {
        PanierDto updatedPanier = commandeService.updatePanier(id, panierDto);
        // Si le panier est trouvé et mis à jour, retourne un code HTTP OK
        // Sinon, retourne un code HTTP 404 Not Found si le panier n'est pas trouvé
        return updatedPanier != null ? new ResponseEntity<>(updatedPanier, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    // Supprimer le panier
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePanier(@PathVariable int id) throws CommandeNotFoundException {
        commandeService.deletePanier(id);
        return ResponseEntity.noContent().build();
    }
    // Modifier la quantite du produit
    @PatchMapping("/{id}/modif-quantite")
    public PanierDto modifierQuantite(@PathVariable int id, @RequestBody ItemPanierDto itemPanierDto) throws CommandeNotFoundException, ItemCommandeNotFoundException {
        return commandeService.modifierQuantite(id, itemPanierDto);
    }

    // Afficher le prix total du panier
    @GetMapping("/{id}/prix-total")
    public double getPrixTotalPanier(@PathVariable int id) {
        return commandeService.getPrixTotalPanier(id);
    }

    // Ajouter un article dans le panier
    @PostMapping("/{id}/ajout-article")
    public PanierDto ajoutArticle(@PathVariable int id, @RequestBody ProduitDto produitDto) throws ProduitRevendeutException, CommandeNotFoundException {
        return commandeService.ajoutArticle(id, produitDto);
    }

    // Valider un panier
    @PostMapping("/{id}/valider-panier")
    public ResponseEntity<PanierDto> validerPanier(@PathVariable int id) throws CommandeNotFoundException {
        PanierDto panierDto = commandeService.validerPanier(id);
        return new ResponseEntity<>(panierDto, HttpStatus.OK);
    }
    // Supprimer un article dans le panier
    @DeleteMapping("/{idPanier}/supp-article/{idProduit}")
    public ResponseEntity<String> supprimerArticle(@PathVariable int idPanier, @PathVariable int idProduit) throws CommandeNotFoundException, ItemCommandeNotFoundException, ProduitRevendeutException {
        commandeService.supprimerArticle(idPanier, idProduit);
        return new ResponseEntity<>("Article supprimé avec succès", HttpStatus.OK);
    }


}

