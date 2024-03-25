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

// TODO: add logs + swagger + status code si dto est null + test controller + api archi
@RestController
@RequestMapping("/categorie")
public class CategorieRestController extends AbstractRestController {
    private static final Logger LOG = LoggerFactory.getLogger(CategorieRestController.class);

    private final ICategorieService service;

    @Autowired
    public CategorieRestController(ICategorieService pService) {
        service = pService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategorieDtoOut> getProduit(@PathVariable UUID id) {
        var result = this.service.getCategorie(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategorieDtoOut>> getProduits() {
        var result = this.service.getCategories();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<CategorieDtoOut> addProduit(@Valid @RequestBody CategorieDtoIn pCategorieDtoIn) {
        var result = this.service.addCategorie(pCategorieDtoIn);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PatchMapping("/update")
    public ResponseEntity<CategorieDtoOut> updateProduit(@Valid @RequestBody CategorieDtoIn pCategorieDtoIn) {
        var result = this.service.updateCategorie(pCategorieDtoIn);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable UUID id) {
        service.deleteCategorie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}