package fr.sqli.formation.gamelife.controleur;

import fr.sqli.formation.gamelife.dto.platforme.PlateformeRequete;
import fr.sqli.formation.gamelife.dto.platforme.PlateformeReponse;
import fr.sqli.formation.gamelife.service.plateforme.IPlateformeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class PlateformeControleur {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlateformeControleur.class);

    private final IPlateformeService service;

    @Autowired
    public PlateformeControleur(IPlateformeService pService) {
        service = pService;
    }

    @GetMapping("/plateformes/{plateformeId}")
    public ResponseEntity<PlateformeReponse> getPlateforme(@PathVariable UUID pPlateformeDtoInId) {
        try {
            var result = this.service.getPlateforme(pPlateformeDtoInId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception pException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/plateformes")
    public ResponseEntity<Set<PlateformeReponse>> getPlateformes() {
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
    public ResponseEntity<PlateformeReponse> creerPlateforme(@Valid @RequestBody PlateformeRequete pPlateformeRequete) {
        try {
            var result = this.service.creerPlateforme(pPlateformeRequete);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception pException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/plateformes")
    public ResponseEntity<PlateformeReponse> modifierPlateforme(@Valid @PathVariable("plateformeId") UUID pPlateformeDtoInId, @RequestBody PlateformeRequete pPlateformeRequete) {
        try {
            var result = this.service.modifierPlateforme(pPlateformeDtoInId, pPlateformeRequete);
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