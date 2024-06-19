package fr.sqli.formation.gamelife.controller;

import fr.sqli.formation.gamelife.dto.request.SellerGameRequest;
import fr.sqli.formation.gamelife.entity.GameEntity;
import fr.sqli.formation.gamelife.entity.SellerGameEntity;
import fr.sqli.formation.gamelife.service.SellerService;
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
public class SellerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SellerController.class);

    @Autowired
    private  SellerService service;

    
    

    @GetMapping("/produits_revendeur/{id}")
    public ResponseEntity<List<GameEntity>> recupererProduitsRevendeur(@PathVariable("id") UUID id) {
        try {
            var result = this.service.getAllProduitByRevendeur(id);
            if(result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception pException) {
            LOGGER.error("Probleme recupererProduitsRevendeur");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/produit_revendeur/{id}")
    public ResponseEntity<SellerGameEntity> recupererProduitsRevendeurId(@PathVariable("id") UUID id ){
        try {
            var result = this.service.recupererProduitRevendeur(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception pException) {
            LOGGER.error("Probleme recupererProduitRevendeurEtat");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/produit_revendeur/utilisateur/{id}")
    public ResponseEntity<List<SellerGameEntity>> recupererProduitsRevendeurRevendeur(@PathVariable("id") UUID id) {
        try {
            var result = this.service.recupererProduitsRevendeurUtilisateur(id);
            if(result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception pException) {
            LOGGER.error("Probleme recupererProduitsRevendeurRevendeur");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/produit_revendeur")
    public ResponseEntity<SellerGameEntity> ajouterProduitRevendeur(@RequestBody SellerGameRequest pSellerGameRequest) {
        try {
            var result = this.service.ajouterProduitRevendeur(pSellerGameRequest);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception pException) {
            LOGGER.error("Probleme ajouterProduitRevendeur");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/produit_revendeur/{id}")
    public ResponseEntity<SellerGameEntity> modifierProduitRevendeur(@PathVariable("id") UUID id,@RequestBody SellerGameRequest pProduitRequete) {
        try {
            var result = this.service.modifierProduitRevendeur(id, pProduitRequete);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception pException) {
            LOGGER.error("Probleme modifierProduitRevendeur");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/produit_revendeur/{id}")
    public ResponseEntity<Void> supprimerProduitRevendeur(@PathVariable("id") UUID id) {
        try {
            this.service.supprimerProduitRevendeur(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(Exception pException) {
            LOGGER.error("Probleme supprimerProduitRevendeur");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}