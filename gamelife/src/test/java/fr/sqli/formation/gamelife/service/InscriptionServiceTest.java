package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.inscription.InscriptionDto;
import fr.sqli.formation.gamelife.dto.inscription.InscriptionDtoHandler;
import fr.sqli.formation.gamelife.entite.UtilisateurEntite;
import fr.sqli.formation.gamelife.exception.utilisateur.UtilisateurExistantException;
import fr.sqli.formation.gamelife.service.utilisateur.InscriptionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(InscriptionServiceTest.class);

    @Test
    void testInscription01() throws Exception {
        LOGGER.debug("TEST : Cas normal");
        InscriptionDto dto = InscriptionDtoHandler.fromEntity(new UtilisateurEntite( "nouveau acheteur", "nouveau acheteur", "$2a$12$1AYx5quZIiiuevfs9Hk1MelQDA/z9ktpIsVfk471xJTNwou7MgBL.", "nouveauacheteur@gamelife.fr", 2, "rue du marechal", "nantes", 44000, "ROLE_ACHETEUR", null, true, null));
        UtilisateurEntite u =service.inscription(dto);
        Assertions.assertNotNull(u);
        Assertions.assertEquals(u.getNom(),"nouveau acheteur");
    }
    @Test
    void testInscription02() throws Exception {
        LOGGER.debug("TEST : Cas utilisateur existant");
        InscriptionDto dto = InscriptionDtoHandler.fromEntity(new UtilisateurEntite("acheteur", "acheteur", "$2a$12$1AYx5quZIiiuevfs9Hk1MelQDA/z9ktpIsVfk471xJTNwou7MgBL.", "acheteur@gamelife.fr", 2, "rue du marechal", "nantes", 44000, "ROLE_ACHETEUR", null, true, null));
        Assertions.assertThrows(UtilisateurExistantException.class,()-> service.inscription(dto));
    }
    @Test
    void testInscription03() throws Exception {
        LOGGER.debug("TEST : Cas champs vide");
        InscriptionDto dto = InscriptionDtoHandler.fromEntity(new UtilisateurEntite("acheteur", "acheteur", "", "acheteur@gamelife.fr", 2, "rue du marechal", "nantes", 44000, "ROLE_ACHETEUR", null, true, null));
        Assertions.assertThrows(IllegalArgumentException.class,()-> service.inscription(dto));
    }

}