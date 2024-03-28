package fr.sqli.formation.gamelife.controller;

import fr.sqli.formation.gamelife.dto.in.ProduitDtoIn;
import fr.sqli.formation.gamelife.dto.out.ProduitDtoOut;
import fr.sqli.formation.gamelife.service.IProduitService;
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
@RequestMapping("/api")
public class ProduitRestController extends AbstractRestController {
    private static final Logger LOG = LoggerFactory.getLogger(ProduitRestController.class);

    private final IProduitService service;

    @Autowired
    public ProduitRestController(IProduitService pService) {
        service = pService;
    }

    @GetMapping("/produits/{produitId}")
    public ResponseEntity<ProduitDtoOut> getProduit(@PathVariable("produitId") UUID pProduitId) {
        try {
            var result = this.service.getProduit(pProduitId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception pException) {
            this.LOG.error("le produit {} est introuvable, %s");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/produits")
    public ResponseEntity<List<ProduitDtoOut>> getProduits() {
        try {
            var result = this.service.getProduits();
            if(result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception pException) {
            this.LOG.error("");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/produits")
    public ResponseEntity<ProduitDtoOut> addProduit(@Valid @RequestBody ProduitDtoIn pProduitDtoIn) {
        try {
            var result = this.service.addProduit(pProduitDtoIn);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception pException) {
            this.LOG.error("");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/produits/{produitId}")
    public ResponseEntity<ProduitDtoOut> updateProduit(@Valid @PathVariable("produitId") UUID pProduitDtoInId, @RequestBody ProduitDtoIn pProduitDtoIn) {
        try {
            var result = this.service.updateProduit(pProduitDtoInId, pProduitDtoIn);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception pException) {
            this.LOG.error("le produit {} est introuvable, %s");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/produits/disable/{produitId}")
    public ResponseEntity<ProduitDtoOut> disableProduit(@PathVariable("produitId") UUID pProduitDtoInId) {
        try {
            var result = this.service.disableProduit(pProduitDtoInId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception pException) {
            this.LOG.error("le produit {} est introuvable, %s");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/produits/{produitId}")
    public ResponseEntity<Void> deleteProduit(@PathVariable UUID produitId) {
        try {
            this.service.deleteProduit(produitId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(Exception pException) {
            this.LOG.error("");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}