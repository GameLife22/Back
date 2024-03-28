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
        InscriptionDto dto = InscriptionDtoHandler.fromEntity(new UtilisateurEntity( "nouveau acheteur", "nouveau acheteur", "$2a$12$CPjNhkXJGvh05Q2RxbatceYvVem4LVBuKfm6vgh7KVHPxp0ZvXuCi", "nouveauacheteur@test.fr", 2, "rue du marechal", "nantes", 44000, "ROLE_ACHETEUR", null, true, null));
        UtilisateurEntity u =service.inscription(dto);
        Assertions.assertNotNull(u);
        Assertions.assertEquals(u.getNom(),"nouveau acheteur");
    }
    @Test
    void testInscription02() throws Exception {
        LOG.debug("TEST : Cas utilisateur existant");
        InscriptionDto dto = InscriptionDtoHandler.fromEntity(new UtilisateurEntity("acheteur", "acheteur", "$2a$12$CPjNhkXJGvh05Q2RxbatceYvVem4LVBuKfm6vgh7KVHPxp0ZvXuCi", "acheteur@test.fr", 2, "rue du marechal", "nantes", 44000, "ROLE_ACHETEUR", null, true, null));
        Assertions.assertThrows(UtilisateurExistantException.class,()-> service.inscription(dto));
    }
    @Test
    void testInscription03() throws Exception {
        LOG.debug("TEST : Cas champs vide");
        InscriptionDto dto = InscriptionDtoHandler.fromEntity(new UtilisateurEntity("acheteur", "acheteur", "", "acheteur@test.fr", 2, "rue du marechal", "nantes", 44000, "ROLE_ACHETEUR", null, true, null));
        Assertions.assertThrows(IllegalArgumentException.class,()-> service.inscription(dto));
    }

}