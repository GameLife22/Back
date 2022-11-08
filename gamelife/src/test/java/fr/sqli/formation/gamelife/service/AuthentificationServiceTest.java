package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import fr.sqli.formation.gamelife.repository.UtilisateurRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Rollback
@Transactional
class AuthentificationServiceTest {
    @Autowired
    private AuthentificationService service;

    @Test
    void testAuthentification01() throws Exception {
        String login = "test@test.com";
        String pwd = "test";
        UtilisateurEntity u = service.authentifier(login,pwd);
        Assertions.assertNotNull(u);
        Assertions.assertEquals(1,u.getId());
    }

}