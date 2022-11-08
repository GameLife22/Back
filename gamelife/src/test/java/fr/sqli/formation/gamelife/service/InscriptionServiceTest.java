package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import fr.sqli.formation.gamelife.ex.AuthentificationException;
import fr.sqli.formation.gamelife.ex.UtilisateurExistantException;
import fr.sqli.formation.gamelife.repository.UtilisateurRepository;
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

    @Test
    void testInscription01() throws Exception {
        UtilisateurEntity u =service.inscription("Astora","Solaire","sa","sa@gmail.com","Landrake",1,"dragon","acheteur",null,"active");
        Assertions.assertNotNull(u);
        Assertions.assertEquals(u.getNom(),"Astora");
    }
    @Test
    void testInscription02() throws Exception {
        Assertions.assertThrows(UtilisateurExistantException.class,()-> service.inscription("Astora","Solaire","sa","acheteur001@outlook.fr","Landrake",1,"dragon","acheteur",null,"active"));
    }

}