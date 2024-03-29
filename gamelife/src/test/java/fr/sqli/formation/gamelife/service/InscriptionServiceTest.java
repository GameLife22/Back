package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.inscription.InscriptionDto;
import fr.sqli.formation.gamelife.dto.inscription.InscriptionDtoHandler;
import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import fr.sqli.formation.gamelife.ex.UtilisateurExistantException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Rollback
@Transactional
@ActiveProfiles("test")
class InscriptionServiceTest {
    @Autowired
    InscriptionService service;
    private static final Logger LOG = LogManager.getLogger();

    @Test
    void testInscription01() throws Exception {
        LOG.debug("TEST : Cas normal");
        InscriptionDto dto = InscriptionDtoHandler.fromEntity(new UtilisateurEntity("SolaireAstora@gmail.com",true,"Teeest1999@","Astora",1,null,"Solaire","ROLE_ACHETEUR","dragon","Landrake",95150,null));
        UtilisateurEntity u =service.inscription(dto);
        Assertions.assertNotNull(u);
        Assertions.assertEquals(u.getNom(),"Astora");
    }
    @Test
    void testInscription02() throws Exception {
        LOG.debug("TEST : Cas utilisateur existant");
        InscriptionDto dto = InscriptionDtoHandler.fromEntity(new UtilisateurEntity("admin1@gamelife.fr",true,"Paz6!!133","admin1",3,null,"admin1","ROLE_ADMIN","rue de capucine","paris",75000,null));
        Assertions.assertThrows(UtilisateurExistantException.class,()-> service.inscription(dto));
    }
    @Test
    void testInscription03() throws Exception {
        LOG.debug("TEST : Cas champs vide");
        InscriptionDto dto = InscriptionDtoHandler.fromEntity(new UtilisateurEntity("SolaireAstora@gmail.com",true,"","Astora",1,null,"Solaire","ROLE_ACHETEUR","dragon","Landrake",95150,null));
        Assertions.assertThrows(IllegalArgumentException.class,()-> service.inscription(dto));
    }

}