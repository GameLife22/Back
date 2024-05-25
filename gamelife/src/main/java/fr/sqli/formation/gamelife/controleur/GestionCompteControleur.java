package fr.sqli.formation.gamelife.controleur;

import fr.sqli.formation.gamelife.dto.gestionCompte.*;
import fr.sqli.formation.gamelife.entite.UtilisateurEntite;
import fr.sqli.formation.gamelife.service.utilisateur.GestionCompteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/gestioncompte")
public class GestionCompteControleur {
    @Autowired
    private GestionCompteService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(GestionCompteControleur.class);

    @PostMapping("/infos")
    public ResponseEntity<UtilisateurEntite> gestionCompte(@RequestBody GestionCompteDto monbody) throws Exception{
        LOGGER.info("GestionCompteControler : IN {}", monbody);
        UtilisateurEntite res;
        res = service.modificationCompte(monbody);
        LOGGER.info("GestionCompteControler : OUT {}", res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    @PostMapping("/etat")
    public ResponseEntity<UUID> gestionEtat(@RequestBody GestionEtatDto monbody) throws Exception{
        LOGGER.info("GestionEtatControler : IN {}", monbody);
        UtilisateurEntite res;
        res = service.modificationEtat(monbody);
        LOGGER.info("GestionEtatControler : OUT {}", res);
        return new ResponseEntity<>(res.getId(), HttpStatus.OK);
    }


    @PostMapping("/mdp")
    public ResponseEntity<UUID> gestionMdp(@RequestBody GestionMdpDto monbody) throws Exception{
        LOGGER.info("GestionMdpControler : IN {}", monbody);
        UtilisateurEntite res;
        res = service.modificationMdp(monbody);
        LOGGER.info("GestionMdpControler : OUT {}", res);
        return ResponseEntity.ok(res.getId());
    }

    @PostMapping("/estrevendeur")
    public ResponseEntity<Boolean> estRevendeur(@RequestBody GestionEtatDto monbody) throws Exception{
        LOGGER.info("EstRevendeurControler : IN {}", monbody);
        Boolean res;
        res = service.estRevendeur(monbody.getId());
        LOGGER.info("EstRevendeurControler : OUT {}", res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/getuser")
    public ResponseEntity<UtilisateurEntite> getUser(@RequestBody GestionCompteDto monbody) throws Exception{
        LOGGER.info("GestionCompteControler : IN {}", monbody);
        UtilisateurEntite res;
        res = service.getUser(monbody.getId());
        LOGGER.info("GestionCompteControler : OUT {}", res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}