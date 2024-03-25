package fr.sqli.formation.gamelife.controler;

import fr.sqli.formation.gamelife.dto.in.PlateformeDtoIn;
import fr.sqli.formation.gamelife.dto.out.PlateformeDtoOut;
import fr.sqli.formation.gamelife.service.IPlateformeService;
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
@RequestMapping("/plateforme")
public class PlateformeRestController extends AbstractRestController {
    private static final Logger LOG = LoggerFactory.getLogger(PlateformeRestController.class);

    private final IPlateformeService service;

    @Autowired
    public PlateformeRestController(IPlateformeService pService) {
        service = pService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlateformeDtoOut> getProduit(@PathVariable UUID id) {
        try {
            var result = this.service.getPlateforme(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception pException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<PlateformeDtoOut>> getProduits() {
        try {
            var result = this.service.getPlateformes();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception pException) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<PlateformeDtoOut> addProduit(@Valid @RequestBody PlateformeDtoIn pPlateformeDtoIn) {
        var result = this.service.addPlateforme(pPlateformeDtoIn);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PatchMapping("/update")
    public ResponseEntity<PlateformeDtoOut> updateProduit(@Valid @RequestBody PlateformeDtoIn pPlateformeDtoIn) {
        var result = this.service.updatePlateforme(pPlateformeDtoIn);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable UUID id) {
        service.deletePlateforme(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}