package fr.sqli.formation.gamelife.controler;

import fr.sqli.formation.gamelife.dto.InscriptionDto;
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

    @PostMapping("/env1")
    public String inscr01(@RequestBody InscriptionDto monbody) throws Exception{
        LOG.info("InscriptionControler : IN {}", monbody);
        UtilisateurEntity res;
        res = service.inscription(monbody.getNom(), monbody.getPrenom(), monbody.getMdp(), monbody.getEmail(), monbody.getEmail(), monbody.getNum_rue(), monbody.getRue(), monbody.getRole(), monbody.getNum_siren(), monbody.getEtat());
        LOG.info("InscriptionControler : OUT {}", res);
        return res.getNom();
    }

}
