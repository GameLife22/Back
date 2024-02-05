package fr.sqli.formation.gamelife.controler;

import fr.sqli.formation.gamelife.dto.in.ProduitRevendeurDtoIn;
import fr.sqli.formation.gamelife.dto.out.ProduitRevendeurDtoOut;
import fr.sqli.formation.gamelife.service.IProduitRevendeurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// TODO : rename file
@RestController
@RequestMapping("/profil/{idUtilisateur}/product")
public class ProduitRevendeurRestController {
    private static final Logger LOG = LoggerFactory.getLogger(ProduitRevendeurRestController.class);

    private final IProduitRevendeurService service;

    @Autowired
    public ProduitRevendeurRestController(IProduitRevendeurService pService) {
        service = pService;
    }

    @GetMapping("{idProduitRevendeur}")
    public ResponseEntity<ProduitRevendeurDtoOut> getProduitRevendeur(@PathVariable Integer pIdUtilisateur, @PathVariable Integer pIdProduitRevendeur) {
        var result = this.service.getProduitRevendeur(pIdUtilisateur, pIdProduitRevendeur);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProduitRevendeurDtoOut>> getProduitsRevendeur(@PathVariable Integer pIdUtilisateur) {
        var result = this.service.getProduitsRevendeur(pIdUtilisateur);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    public ResponseEntity<ProduitRevendeurDtoOut> addProduitRevendeur(@PathVariable Integer pIdUtilisateur, @RequestBody ProduitRevendeurDtoIn pProduitRevendeurDtoIn) {
        var result = this.service.addProduitRevendeur(pIdUtilisateur, pProduitRevendeurDtoIn);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/update/{idProduitRevendeur}")
    public ResponseEntity<ProduitRevendeurDtoOut> updateProduitRevendeur(@PathVariable Integer pIdUtilisateur, @PathVariable Integer pIdProduitRevendeur, @RequestBody ProduitRevendeurDtoIn pProduitRevendeurDtoIn) {
        var result = this.service.updateProduitRevendeur(pIdUtilisateur, pProduitRevendeurDtoIn);
        return ResponseEntity.ok(result);
    }
}