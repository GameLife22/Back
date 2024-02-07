package fr.sqli.formation.gamelife.controler;

import fr.sqli.formation.gamelife.dto.inscription.InscriptionDto;
import fr.sqli.formation.gamelife.dto.SiretDto;
import fr.sqli.formation.gamelife.dto.mdpOublie.EmailDtoOut;
import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import fr.sqli.formation.gamelife.service.InscriptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inscription")
public class InscriptionControler {
    @Autowired
    private InscriptionService service;

    private static final Logger LOG = LogManager.getLogger();

    @PostMapping("/inscription")
    public String inscr01(@RequestBody InscriptionDto monbody) throws Exception{
        LOG.info("InscriptionControler : IN {}", monbody);
        UtilisateurEntity res;
        res = service.inscription(monbody);
        LOG.info("InscriptionControler : OUT {}", res);
        return res.getResetPasswordToken();
    }
    @PostMapping("/siret")
    public boolean checkSirret(@RequestBody SiretDto monbody) throws Exception{
        LOG.info("InscriptionControler : IN {}", monbody);
        LOG.info("InscriptionControler : OUT {}", service.checkSiret(monbody));
        return service.checkSiret(monbody);
    }
    @PostMapping("/validation")
    public void emailValidation(@RequestBody EmailDtoOut monbody) throws Exception {
        LOG.info("InscriptionControler : IN {}", monbody);
        service.validateAccount(monbody);
        LOG.info("InscriptionControler : OUT ");

    }
    @GetMapping ("activer")
    public void activerCompte(@RequestParam String token){
        LOG.info("InscriptionControler : IN {}", token);
        service.activateAccount(token);
        LOG.info("InscriptionControler : OUT ");
    }

}
