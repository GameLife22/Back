package fr.sqli.formation.gamelife.controleur;

import fr.sqli.formation.gamelife.dto.produit.ProduitRequete;
import fr.sqli.formation.gamelife.dto.produit.ProduitReponse;
import fr.sqli.formation.gamelife.dto.produit.ProduitRevendeurReponse;
import fr.sqli.formation.gamelife.service.produit.IProduitRevendeurService;
import fr.sqli.formation.gamelife.service.produit.IProduitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class ProduitControleur {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProduitControleur.class);

    private final IProduitService service;
    private final IProduitRevendeurService produitRevendeurService;

    @Autowired
    public ProduitControleur(IProduitService pService, IProduitRevendeurService pIProduitRevendeurService) {
        service = pService;
        produitRevendeurService = pIProduitRevendeurService;
    }

    @GetMapping("/produits/{produitId}")
    public ResponseEntity<ProduitReponse> recupererProduit(@PathVariable("produitId") UUID pProduitId) {
        try {
            var result = this.service.recupererProduit(pProduitId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception pException) {
            this.LOGGER.error("le produit {} est introuvable, %s");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/produits")
    public ResponseEntity<List<ProduitReponse>> recupererProduits() {
        try {
            var result = this.service.recupererProduits();
            if(result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception pException) {
            this.LOGGER.error("");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/produits")
    public ResponseEntity<ProduitReponse> creerProduit(@Valid @RequestBody ProduitRequete pProduitRequete) {
        try {
            this.LOGGER.info("controller");
            var result = this.service.creerProduit(pProduitRequete);
            this.LOGGER.info("resultat:" + result);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch(Exception pException) {
            this.LOGGER.info(pException.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/produits/{produitId}")
    public ResponseEntity<ProduitReponse> modifierProduit(@Valid @PathVariable("produitId") UUID pProduitDtoInId, @RequestBody ProduitRequete pProduitRequete) {
        try {
            var result = this.service.modifierProduit(pProduitDtoInId, pProduitRequete);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception pException) {
            this.LOGGER.error("le produit {} est introuvable, %s");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/produits/{produitId}")
    public ResponseEntity<Void> supprimerProduit(@PathVariable UUID produitId) {
        try {
            this.service.supprimerProduit(produitId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(Exception pException) {
            this.LOGGER.error("");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //todo: à supprimer quand yassine aura terminé sa feature
    @GetMapping("/produitrevendeur/{pProduitRevendeurId}")
    public ResponseEntity<ProduitRevendeurReponse> recupererProduitRevendeur(@PathVariable UUID pProduitRevendeurId) {
        this.produitRevendeurService.recupererProduitRevendeur(pProduitRevendeurId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}