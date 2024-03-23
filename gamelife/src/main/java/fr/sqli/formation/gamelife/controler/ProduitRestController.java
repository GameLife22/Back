package fr.sqli.formation.gamelife.controler;

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
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

// TODO: add logs + swagger + status code + test controller
@RestController
@RequestMapping("/produit")
public class ProduitRestController extends AbstractRestController {
    private static final Logger LOG = LoggerFactory.getLogger(ProduitRestController.class);

    private final IProduitService service;

    @Autowired
    public ProduitRestController(IProduitService pService) {
        service = pService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProduitDtoOut> getProduit(@PathVariable UUID id) {
        var result = this.service.getProduit(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProduitDtoOut>> getProduits() {
        var result = this.service.getProduits();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ProduitDtoOut> addProduit(@Valid @RequestBody ProduitDtoIn pProduitDtoIn) {
        var result = this.service.addProduit(pProduitDtoIn);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PatchMapping("/update")
    public ResponseEntity<ProduitDtoOut> updateProduit(@Valid @RequestBody ProduitDtoIn pProduitDtoIn) {
        var result = this.service.updateProduit(pProduitDtoIn);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //todo: disable produit

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable UUID id) {
        this.service.deleteProduit(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}