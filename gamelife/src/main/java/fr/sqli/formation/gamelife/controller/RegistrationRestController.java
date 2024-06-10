package fr.sqli.formation.gamelife.controller;

import fr.sqli.formation.gamelife.dto.Registration;
import fr.sqli.formation.gamelife.dto.response.EmailResponse;
import fr.sqli.formation.gamelife.entity.UserEntity;
import fr.sqli.formation.gamelife.service.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inscription")
public class RegistrationRestController {
    @Autowired
    private RegistrationService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationRestController.class);

    @PostMapping("/inscription")
    public String inscription(@RequestBody Registration monbody) throws Exception{
        LOGGER.info("InscriptionControler : IN {}", monbody);
        UserEntity res;
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
    public void emailValidation(@RequestBody EmailResponse monbody) throws Exception {
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
