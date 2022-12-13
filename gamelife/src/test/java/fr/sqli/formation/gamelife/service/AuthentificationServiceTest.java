package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.login.LoginDto;
import fr.sqli.formation.gamelife.dto.login.LoginDtoHandler;
import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

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
        //UtilisateurEntity u = service.authentifier(dto);
        //Assertions.assertNotNull(u);
        //Assertions.assertEquals(1,u.getId());
    }

    @Test
    void testAuthentification02() throws Exception {
        String login = "fabien.bidault@social.aston-ecole.com";
        String pwd = "Padsfz6!!3";
        LoginDto dto = LoginDtoHandler.fromEntity(new UtilisateurEntity(login,pwd));
       // Assertions.assertThrows(AuthentificationException.class,()-> service.authentifier(dto));
    }
/*
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

    @Test
    void testCompteDesactive() throws Exception {
        String login = "acheteur002@outlook.fr";
        String pwd = "acheteur002";
        LoginDto dto = LoginDtoHandler.fromEntity(new UtilisateurEntity(login,pwd));
        Assertions.assertThrows(CompteDesactiveException.class,()-> service.authentifier(dto));
    }
*/

}