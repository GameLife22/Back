package fr.sqli.formation.gamelife.controler;

import fr.sqli.formation.gamelife.dto.ProduitDto;
import fr.sqli.formation.gamelife.dto.panier.ItemPanierDto;
import fr.sqli.formation.gamelife.dto.panier.PanierDto;
import fr.sqli.formation.gamelife.dto.panier.PanierDtoHandler;
import fr.sqli.formation.gamelife.entity.PanierEntity;
import fr.sqli.formation.gamelife.ex.ProduitException;
import fr.sqli.formation.gamelife.ex.UtilisateurNonExistantException;
import fr.sqli.formation.gamelife.ex.panier.ItemPanierNotFoundException;
import fr.sqli.formation.gamelife.ex.panier.PanierNotFoundException;
import fr.sqli.formation.gamelife.repository.PanierRepository;
import fr.sqli.formation.gamelife.service.panier.PanierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/panier")
public class PanierControler {

    @Autowired
    private PanierService panierService;
    @Autowired
    private PanierRepository panierRepository;

    @Autowired
    private PanierDtoHandler panierDtoHandler;

    @GetMapping("/all")
    public List<PanierDto> getAllPaniers() {
        return panierService.getAllPaniers();
    }

    @GetMapping("/{id}")
    public PanierDto getPanierById(@PathVariable int id) throws PanierNotFoundException {
        PanierEntity panierEntity = panierRepository.findByIdWithItemPaniers(id)
                .orElseThrow(() -> new PanierNotFoundException("Panier not found with id: " + id));
        return panierDtoHandler.entityToDto(panierEntity);
    }

    @PostMapping("/creer")
    public ResponseEntity<PanierDto> createPanier(@RequestBody PanierDto panierDto) throws UtilisateurNonExistantException {
        PanierDto createdPanier = panierService.createPanier(panierDto);
        // Retourne un code HTTP 201 Created avec le corps contenant le panier créé
        return new ResponseEntity<>(createdPanier, HttpStatus.CREATED);
    }

    //Mise a jour du panier
    @PutMapping("/{id}")
    public ResponseEntity<PanierDto> updatePanier(@PathVariable int id, @RequestBody PanierDto panierDto) throws PanierNotFoundException {
        PanierDto updatedPanier = panierService.updatePanier(id, panierDto);
        // Si le panier est trouvé et mis à jour, retourne un code HTTP OK
        // Sinon, retourne un code HTTP 404 Not Found si le panier n'est pas trouvé
        return updatedPanier != null ? new ResponseEntity<>(updatedPanier, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    //supprimer le panier
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePanier(@PathVariable int id) throws PanierNotFoundException {
        panierService.deletePanier(id);
        return ResponseEntity.noContent().build();
    }
    // modifier la quantite du produit
    @PatchMapping("/{id}/modif-quantite")
    public PanierDto modifierQuantite(@PathVariable int id, @RequestBody ItemPanierDto itemPanierDto) throws PanierNotFoundException, ItemPanierNotFoundException {
        return panierService.modifierQuantite(id, itemPanierDto);
    }

    //Affchier le prix total du panier
    @GetMapping("/{id}/prix-total")
    public double getPrixTotalPanier(@PathVariable int id) {
        return panierService.getPrixTotalPanier(id);
    }


    @PostMapping("/{id}/ajout-article")
    public PanierDto ajoutArticle(@PathVariable int id, @RequestBody ProduitDto produitDto) throws ProduitException, PanierNotFoundException {
        return panierService.ajoutArticle(id, produitDto);
    }
    

    /*
    @PostMapping("/{id}/valider-panier")
    public PanierDto validerPanier(@PathVariable int id) throws PanierNotFoundException {
        return panierService.validerPanier(id);
    }

    @PostMapping("/{id}/supprimer-article")
    public PanierDto supprimerArticle(@PathVariable int id, @RequestBody ProduitDto produitDto) throws PanierNotFoundException {
        return panierService.supprimerArticle(id, produitDto);
    }

*/

}

