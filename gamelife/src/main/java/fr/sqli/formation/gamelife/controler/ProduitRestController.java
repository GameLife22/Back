
package fr.sqli.formation.gamelife.controler;

import fr.sqli.formation.gamelife.dto.in.ProduitDtoIn;
import fr.sqli.formation.gamelife.dto.out.ProduitDtoOut;
import fr.sqli.formation.gamelife.service.IProduitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

// TODO: add logs + swagger + status code + test controller
@RestController
@RequestMapping("/produit")
public class ProduitRestController {
    private static final Logger LOG = LoggerFactory.getLogger(ProduitRestController.class);

    private final IProduitService service;

    @Autowired
    public ProduitRestController(IProduitService pService) {
        service = pService;
    }

    @GetMapping("/{idProduit}")
    public ResponseEntity<ProduitDtoOut> getProduit(@PathVariable Integer pIdProduit) {
        var result = this.service.getProduit(pIdProduit);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProduitDtoOut>> getProduits() {
        var result = this.service.getProduits();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    public ResponseEntity<ProduitDtoOut> addProduit(@Valid @RequestBody ProduitDtoIn pProduitDtoIn) {
        var result = this.service.addProduit(pProduitDtoIn);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/update/{idProduit}")
    public ResponseEntity<ProduitDtoOut> updateProduit(@Valid @PathVariable Integer pIdProduit, @RequestBody ProduitDtoIn pProduitDtoIn) {
        var result = this.service.updateProduit(pProduitDtoIn);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{idProduit}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Integer pIdProduit) {
        service.deleteProduit(pIdProduit);
        return ResponseEntity.ok().build();
    }
}