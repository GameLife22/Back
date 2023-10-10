package fr.sqli.formation.gamelife.controler;

import fr.sqli.formation.gamelife.dto.gestionCompte.*;
import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import fr.sqli.formation.gamelife.service.GestionCompteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gestioncompte")
public class GestionCompteControler {
    @Autowired
    private GestionCompteService service;

    private static final Logger LOG = LogManager.getLogger();

    @PostMapping("/infos")
    public ResponseEntity<UtilisateurEntity> gestionCompte(@RequestBody GestionCompteDto monbody) throws Exception{
        LOG.info("GestionCompteControler : IN {}", monbody);
        UtilisateurEntity res;
        res = service.modificationCompte(monbody);
        LOG.info("GestionCompteControler : OUT {}", res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    @PostMapping("/etat")
    public ResponseEntity<Integer> gestionEtat(@RequestBody GestionEtatDto monbody) throws Exception{
        LOG.info("GestionEtatControler : IN {}", monbody);
        UtilisateurEntity res;
        res = service.modificationEtat(monbody);
        LOG.info("GestionEtatControler : OUT {}", res);
        return new ResponseEntity<>(res.getId(), HttpStatus.OK);
    }


    @PostMapping("/mdp")
    public ResponseEntity<Integer> gestionMdp(@RequestBody GestionMdpDto monbody) throws Exception{
        LOG.info("GestionMdpControler : IN {}", monbody);
        UtilisateurEntity res;
        res = service.modificationMdp(monbody);
        LOG.info("GestionMdpControler : OUT {}", res);
        return ResponseEntity.ok(res.getId());
    }

    @PostMapping("/estrevendeur")
    public ResponseEntity<Boolean> estRevendeur(@RequestBody GestionEtatDto monbody) throws Exception{
        LOG.info("EstRevendeurControler : IN {}", monbody);
        Boolean res;
        res = service.estRevendeur(monbody.getId());
        LOG.info("EstRevendeurControler : OUT {}", res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/getuser")
    public ResponseEntity<UserDtoOut> getUser(@RequestBody GestionCompteDto monbody) throws Exception{
        LOG.info("GestionCompteControler : IN {}", monbody);
        UserDtoOut res;
        res = service.getUser(monbody.getId());
        LOG.info("GestionCompteControler : OUT {}", res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}