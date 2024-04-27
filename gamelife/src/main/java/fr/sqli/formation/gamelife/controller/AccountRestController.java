package fr.sqli.formation.gamelife.controller;

import fr.sqli.formation.gamelife.dto.Account;
import fr.sqli.formation.gamelife.dto.AccountStatus;
import fr.sqli.formation.gamelife.dto.AccountPassword;
import fr.sqli.formation.gamelife.dto.response.UserResponse;
import fr.sqli.formation.gamelife.entity.UserEntity;
import fr.sqli.formation.gamelife.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/gestioncompte")
public class AccountRestController {
    @Autowired
    private AccountService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountRestController.class);

    @PostMapping("/infos")
    public ResponseEntity<UserEntity> gestionCompte(@RequestBody Account monbody) throws Exception{
        LOGGER.info("GestionCompteControler : IN {}", monbody);
        UserEntity res;
        res = service.modificationCompte(monbody);
        LOGGER.info("GestionCompteControler : OUT {}", res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    @PostMapping("/etat")
    public ResponseEntity<UUID> gestionEtat(@RequestBody AccountStatus monbody) throws Exception{
        LOGGER.info("GestionEtatControler : IN {}", monbody);
        UserEntity res;
        res = service.modificationEtat(monbody);
        LOGGER.info("GestionEtatControler : OUT {}", res);
        return new ResponseEntity<>(res.getId(), HttpStatus.OK);
    }


    @PostMapping("/mdp")
    public ResponseEntity<UUID> gestionMdp(@RequestBody AccountPassword monbody) throws Exception{
        LOGGER.info("GestionMdpControler : IN {}", monbody);
        UserEntity res;
        res = service.modificationMdp(monbody);
        LOGGER.info("GestionMdpControler : OUT {}", res);
        return ResponseEntity.ok(res.getId());
    }

    @PostMapping("/estrevendeur")
    public ResponseEntity<Boolean> estRevendeur(@RequestBody AccountStatus monbody) throws Exception{
        LOGGER.info("EstRevendeurControler : IN {}", monbody);
        Boolean res;
        res = service.estRevendeur(monbody.getId());
        LOGGER.info("EstRevendeurControler : OUT {}", res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/getuser")
    public ResponseEntity<UserResponse> getUser(@RequestBody Account monbody) throws Exception{
        LOGGER.info("GestionCompteControler : IN {}", monbody);
        UserResponse res;
        res = service.getUser(monbody.getId());
        LOGGER.info("GestionCompteControler : OUT {}", res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}