
package fr.sqli.formation.gamelife.controler;

import fr.sqli.formation.gamelife.dto.in.ProduitDtoIn;
import fr.sqli.formation.gamelife.dto.out.ProduitDtoOut;
import fr.sqli.formation.gamelife.service.IProduitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO: add logs
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
    public ResponseEntity<ProduitDtoOut> addProduit(@RequestBody ProduitDtoIn pProduitDtoIn) {
        var result = this.service.addProduit(pProduitDtoIn);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/update/{idProduit}")
    public ResponseEntity<ProduitDtoOut> updateProduit(@PathVariable Integer pIdProduit, @RequestBody ProduitDtoIn pProduitDtoIn) {
        var result = this.service.updateProduit(pIdProduit, pProduitDtoIn);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/disable/{idProduit}")
    public ResponseEntity<ProduitDtoOut> disableProduit(@PathVariable Integer pIdProduit) {
        var result = this.service.disableProduit(pIdProduit);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{idProduit}")
    public ResponseEntity<Boolean> deleteProduit(@PathVariable Integer pIdProduit) {
        var result = this.service.deleteProduit(pIdProduit);
        return ResponseEntity.ok(result);
    }
}