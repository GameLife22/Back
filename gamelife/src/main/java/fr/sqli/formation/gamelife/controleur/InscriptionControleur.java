package fr.sqli.formation.gamelife.controleur;

import fr.sqli.formation.gamelife.dto.inscription.InscriptionDto;
import fr.sqli.formation.gamelife.dto.mdpOublie.EmailDtoOut;
import fr.sqli.formation.gamelife.entite.UtilisateurEntite;
import fr.sqli.formation.gamelife.service.utilisateur.InscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inscription")
public class InscriptionControleur {
    @Autowired
    private InscriptionService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(InscriptionControleur.class);

    @PostMapping("/inscription")
    public String inscr01(@RequestBody InscriptionDto monbody) throws Exception{
        LOGGER.info("InscriptionControler : IN {}", monbody);
        UtilisateurEntite res;
        res = service.inscription(monbody);
        LOGGER.info("InscriptionControler : OUT {}", res);
        return res.getResetPasswordToken();
    }
    /*
    @PostMapping("/siren")
    public boolean checkSirret(@RequestBody SirenDto monbody) throws Exception{
        LOGGER.info("InscriptionControler : IN {}", monbody);
        LOGGER.info("InscriptionControler : OUT {}", service.checkSiret(monbody));
        return service.checkSiret(monbody);
    }
    */


    @PostMapping("/validation")
    public void emailValidation(@RequestBody EmailDtoOut monbody) throws Exception {
        LOGGER.info("InscriptionControler : IN {}", monbody);
        service.validateAccount(monbody);
        LOGGER.info("InscriptionControler : OUT ");

    }

    @GetMapping ("activer")
    public void activerCompte(@RequestParam String token){
        LOGGER.info("InscriptionControler : IN {}", token);
        service.activateAccount(token);
        LOGGER.info("InscriptionControler : OUT ");
    }

}
