package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.LoginDto;
import fr.sqli.formation.gamelife.dto.LoginDtoHandler;
import fr.sqli.formation.gamelife.dto.UtilisateurDto;
import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import fr.sqli.formation.gamelife.ex.AuthentificationException;
import fr.sqli.formation.gamelife.ex.UtilisateurExistantException;
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
        String login = "fabien.bidault@social.aston-ecole.com";
        String pwd = "Paz6!!3";
        LoginDto dto = LoginDtoHandler.fromEntity(new UtilisateurEntity(login,pwd));
        UtilisateurEntity u = service.authentifier(dto);
        Assertions.assertNotNull(u);
        Assertions.assertEquals(1,u.getId());
    }

    @Test
    void testAuthentification02() throws Exception {
        String login = "fabien.bidault@social.aston-ecole.com";
        String pwd = "Padsfz6!!3";
        LoginDto dto = LoginDtoHandler.fromEntity(new UtilisateurEntity(login,pwd));
        Assertions.assertThrows(AuthentificationException.class,()-> service.authentifier(dto));
    }

    @Test
    void testAuthentification03() throws Exception {
        String login = "fabien@social.aston-ecole.com";
        String pwd = "Padsfz6!!3";
        LoginDto dto = LoginDtoHandler.fromEntity(new UtilisateurEntity(login,pwd));
        Assertions.assertThrows(AuthentificationException.class,()-> service.authentifier(dto));
    }
    @Test
    void testAuthentification04() throws Exception {
        String login = "";
        String pwd = "Padsfz6!!3";
        LoginDto dto = LoginDtoHandler.fromEntity(new UtilisateurEntity(login,pwd));
        Assertions.assertThrows(IllegalArgumentException.class,()-> service.authentifier(dto));
    }


}