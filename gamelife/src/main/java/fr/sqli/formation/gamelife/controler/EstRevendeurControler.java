package fr.sqli.formation.gamelife.controler;

import fr.sqli.formation.gamelife.dto.GestionEtatDto;
import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import fr.sqli.formation.gamelife.service.GestionCompteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gestioncomtpe/estrevendeur")
public class EstRevendeurControler {
    @Autowired
    private GestionCompteService service;

    private static final Logger LOG = LogManager.getLogger();

    @PostMapping("")
    public ResponseEntity<Boolean> inscr01(@RequestBody GestionEtatDto monbody) throws Exception{
        LOG.info("EstRevendeurControler : IN {}", monbody);
        Boolean res;
        res = service.estRevendeur(monbody.getId());
        LOG.info("EstRevendeurControler : OUT {}", res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}

