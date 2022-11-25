package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.InscriptionDto;
import fr.sqli.formation.gamelife.dto.InscriptionDtoHandler;
import fr.sqli.formation.gamelife.dto.UtilisateurDto;
import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import fr.sqli.formation.gamelife.ex.AuthentificationException;
import fr.sqli.formation.gamelife.ex.UtilisateurExistantException;
import fr.sqli.formation.gamelife.repository.UtilisateurRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Rollback
@Transactional
class InscriptionServiceTest {
    @Autowired
    InscriptionService service;
    private static final Logger LOG = LogManager.getLogger();

    @Test
    void testInscription01() throws Exception {
        LOG.debug("TEST : Cas normal");
        InscriptionDto dto = InscriptionDtoHandler.fromEntity(new UtilisateurEntity("SolaireAstora@gmail.com","active","sa","Astora",1,95150,null,"Solaire","acheteur","dragon","Landrake"));
        UtilisateurEntity u =service.inscription(dto);
        Assertions.assertNotNull(u);
        Assertions.assertEquals(u.getNom(),"Astora");
    }
    @Test
    void testInscription02() throws Exception {
        LOG.debug("TEST : Cas utilisateur existant");
        InscriptionDto dto = InscriptionDtoHandler.fromEntity(new UtilisateurEntity("sa@gmail.com","active","sa","Astora",1,95150,null,"Solaire","acheteur","dragon","Landrake"));
        Assertions.assertThrows(UtilisateurExistantException.class,()-> service.inscription(dto));
    }
    @Test
    void testInscription03() throws Exception {
        LOG.debug("TEST : Cas champs vide");
        InscriptionDto dto = InscriptionDtoHandler.fromEntity(new UtilisateurEntity("SolaireAstora@gmail.com","active","","Astora",1,95150,null,"Solaire","acheteur","dragon","Landrake"));
        Assertions.assertThrows(IllegalArgumentException.class,()-> service.inscription(dto));
    }

}