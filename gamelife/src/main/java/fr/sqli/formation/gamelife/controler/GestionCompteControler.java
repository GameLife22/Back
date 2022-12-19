package fr.sqli.formation.gamelife.controler;

import fr.sqli.formation.gamelife.dto.GestionCompteDto;
import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import fr.sqli.formation.gamelife.service.GestionCompteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gestioncomtpe/infos")
public class GestionCompteControler {
    @Autowired
    private GestionCompteService service;

    private static final Logger LOG = LogManager.getLogger();

    @PostMapping("")
    public ResponseEntity<Integer> inscr01(@RequestBody GestionCompteDto monbody) throws Exception{
        LOG.info("GestionCompteControler : IN {}", monbody);
        UtilisateurEntity res;
        res = service.modificationCompte(monbody);
        LOG.info("GestionCompteControler : OUT {}", res);
        return new ResponseEntity<>(res.getId(), HttpStatus.OK);
    }

}