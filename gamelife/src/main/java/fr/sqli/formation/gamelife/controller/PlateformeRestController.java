package fr.sqli.formation.gamelife.controller;

import fr.sqli.formation.gamelife.dto.in.PlateformeDtoIn;
import fr.sqli.formation.gamelife.dto.out.PlateformeDtoOut;
import fr.sqli.formation.gamelife.service.IPlateformeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class PlateformeRestController extends AbstractRestController {
    private static final Logger LOG = LoggerFactory.getLogger(PlateformeRestController.class);

    private final IPlateformeService service;

    @Autowired
    public PlateformeRestController(IPlateformeService pService) {
        service = pService;
    }

    @GetMapping("/plateformes/{plateformeId}")
    public ResponseEntity<PlateformeDtoOut> getPlateforme(@PathVariable UUID pPlateformeDtoInId) {
        try {
            var result = this.service.getPlateforme(pPlateformeDtoInId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception pException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/plateformes")
    public ResponseEntity<List<PlateformeDtoOut>> getPlateformes() {
        try {
            var result = this.service.getPlateformes();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception pException) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/plateformes")
    public ResponseEntity<PlateformeDtoOut> addPlateforme(@Valid @RequestBody PlateformeDtoIn pPlateformeDtoIn) {
        try {
            var result = this.service.addPlateforme(pPlateformeDtoIn);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception pException) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/plateformes")
    public ResponseEntity<PlateformeDtoOut> updatePlateforme(@Valid @PathVariable("plateformeId") UUID pPlateformeDtoInId, @RequestBody PlateformeDtoIn pPlateformeDtoIn) {
        try {
            var result = this.service.updatePlateforme(pPlateformeDtoInId, pPlateformeDtoIn);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception pException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/plateformes/{plateformeId}")
    public ResponseEntity<Void> deletePlateforme(@PathVariable("plateformeId") UUID pPlateformeDtoInId) {
        try {
            this.service.deletePlateforme(pPlateformeDtoInId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception pException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}