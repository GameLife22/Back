package fr.sqli.formation.gamelife.controleur;

import fr.sqli.formation.gamelife.dto.produit.ProduitReponse;
import fr.sqli.formation.gamelife.dto.produit.ProduitRevendeurRequete;
import fr.sqli.formation.gamelife.entite.ProduitRevendeurEntite;
import fr.sqli.formation.gamelife.service.produitRevendeur.ProduitRevendeurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class ProduitRevendeurControleur {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProduitControleur.class);

    private final ProduitRevendeurService service;

    @Autowired
    public ProduitRevendeurControleur(ProduitRevendeurService pPRService) {
        service = pPRService;
    }

    @GetMapping("/produits_revendeur")
    public ResponseEntity<List<ProduitRevendeurEntite>> recupererProduitsRevendeur() {
        try {
            var result = this.service.recupererProduitsRevendeur();
            if(result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception pException) {
            this.LOGGER.error("Probleme recupererProduitsRevendeur");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/produit_revendeur")
    public ResponseEntity<ProduitRevendeurEntite> recupererProduitsRevendeurEtat(String etat) {
        try {
            var result = this.service.recupererProduitRevendeur();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception pException) {
            this.LOGGER.error("Probleme recupererProduitRevendeurEtat");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/produit_revendeur")
    public ResponseEntity<ProduitRevendeurEntite> ajouterProduitRevendeur(@RequestBody ProduitRevendeurRequete pProduitRevendeurRequete) {
        try {
            var result = this.service.ajouterProduitRevendeur(pProduitRevendeurRequete);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception pException) {
            this.LOGGER.error("Probleme ajouterProduitRevendeur");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/produit_revendeur/{id}")
    public ResponseEntity<ProduitRevendeurEntite> modifierProduitRevendeur(@PathVariable("id") UUID id, ProduitRevendeurRequete pProduitRequete) {
        try {
            var result = this.service.modifierProduitRevendeur(id, pProduitRequete);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception pException) {
            this.LOGGER.error("Probleme modifierProduitRevendeur");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}