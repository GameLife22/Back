package fr.sqli.formation.gamelife.controler;

import fr.sqli.formation.gamelife.dto.in.CategorieDtoIn;
import fr.sqli.formation.gamelife.dto.out.CategorieDtoOut;
import fr.sqli.formation.gamelife.service.ICategorieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

// TODO: add logs + swagger +  dto est null + test controller + hasRole
@RestController
@RequestMapping("/api")
public class CategorieRestController extends AbstractRestController {
    private static final Logger LOG = LoggerFactory.getLogger(CategorieRestController.class);

    private final ICategorieService service;

    @Autowired
    public CategorieRestController(ICategorieService pService) {
        service = pService;
    }

    @GetMapping("/categories/{categorieId}")
    public ResponseEntity<CategorieDtoOut> getProduit(@PathVariable("categorieId") UUID pCategorieId) {
        try {
            var result = this.service.getCategorie(pCategorieId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception pException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategorieDtoOut>> getProduits() {
        try {
            var result = this.service.getCategories();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception pException) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/categories")
    public ResponseEntity<CategorieDtoOut> addProduit(@Valid @RequestBody CategorieDtoIn pCategorieDtoIn) {
        try {
            var result = this.service.addCategorie(pCategorieDtoIn);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch(Exception pException) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/categories/{categorieId}")
    public ResponseEntity<CategorieDtoOut> updateProduit(@Valid @PathVariable("categorieId") UUID pCategorieId, @RequestBody CategorieDtoIn pCategorieDtoIn) {
        try {
            var result = this.service.updateCategorie(pCategorieId, pCategorieDtoIn);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception pException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/categories/{categorieId}")
    public ResponseEntity<Void> deleteProduit(@PathVariable("categorieId") UUID pCategorieId) {
        try {
            service.deleteCategorie(pCategorieId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(Exception pException) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}