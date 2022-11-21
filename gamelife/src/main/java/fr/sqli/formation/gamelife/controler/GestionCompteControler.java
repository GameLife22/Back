package fr.sqli.formation.gamelife.controler;

import fr.sqli.formation.gamelife.dto.GestionCompteDto;
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
@RequestMapping("/gc")
public class GestionCompteControler {
    @Autowired
    private GestionCompteService service;

    private static final Logger LOG = LogManager.getLogger();

    @PostMapping("/env1")
    public ResponseEntity inscr01(@RequestBody GestionCompteDto monbody) throws Exception{
        LOG.info("GestionCompteControler : IN {}", monbody);
        UtilisateurEntity res;
        res = service.modificationCompte(monbody.getNom(), monbody.getPrenom(), monbody.getNewEmail(), monbody.getOldEmail(), monbody.getNumRue(), monbody.getRue(),monbody.getVille(),monbody.getCodePostal(), monbody.getRole(), monbody.getNumSiren(), monbody.getEtat());
        LOG.info("GestionCompteControler : OUT {}", res);
        return new ResponseEntity<Integer>(res.getId(), HttpStatus.OK);
    }

}