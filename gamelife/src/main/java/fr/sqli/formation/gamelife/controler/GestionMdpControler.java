package fr.sqli.formation.gamelife.controler;

import fr.sqli.formation.gamelife.dto.GestionMdpDto;
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
@RequestMapping("/gestioncomtpe/mdp")
public class GestionMdpControler {
    @Autowired
    private GestionCompteService service;

    private static final Logger LOG = LogManager.getLogger();

    @PostMapping("/env1")
    public ResponseEntity<Integer> inscr01(@RequestBody GestionMdpDto monbody) throws Exception{
        LOG.info("GestionMdpControler : IN {}", monbody);
        UtilisateurEntity res;
        res = service.modificationMdp(monbody);
        LOG.info("GestionMdpControler : OUT {}", res);
        return ResponseEntity.ok(res.getId());
    }

}