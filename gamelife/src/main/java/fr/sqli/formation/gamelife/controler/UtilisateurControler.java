package fr.sqli.formation.gamelife.controler;

import fr.sqli.formation.gamelife.dto.IdDto;
import fr.sqli.formation.gamelife.dto.UtilisateurDto;
import fr.sqli.formation.gamelife.dto.UtilisateurDtoHandler;
import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import fr.sqli.formation.gamelife.service.UtilisateurService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/utilisateur")
public class UtilisateurControler {
    @Autowired
    private UtilisateurService service;
    private UtilisateurDtoHandler dao;

    private static final Logger LOG = LogManager.getLogger();

    @PostMapping("/infos")
    public UtilisateurDto getInfos(@RequestBody IdDto id){
        LOG.info("InscriptionControler : IN {}", id);
        UtilisateurDto res;
        try {
            res =UtilisateurDtoHandler.fromEntity(service.getUtilisateurById(id.getId()));
            LOG.info("InscriptionControler : OUT {}", res);
            return res;
        } catch (Exception err){
            LOG.warn("ERROR : {}",err);
        }
        //res = UtilisateurDtoHandler.fromEntity(service.getUtilisateurById(id));

        return null;

    }
}
