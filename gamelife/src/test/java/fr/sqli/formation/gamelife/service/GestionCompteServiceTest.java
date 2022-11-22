package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.GestionCompteDto;
import fr.sqli.formation.gamelife.dto.GestionEtatDto;
import fr.sqli.formation.gamelife.dto.GestionMdpDto;
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
class GestionCompteServiceTest {
    @Autowired
    GestionCompteService service;
//    UtilisateurRepository uDao;

    @Test
    void testModificationCompte01() throws Exception {
        UtilisateurEntity test = service.modificationCompte("dubois","henry","acheteur002@outlook.fr","acheteur002@outlook.fr",13,"rue de la papeterie", "Ballancourt",91610,"acheteur",null,"active");
        Assertions.assertEquals("Ballancourt",test.getVille());
    }
    @Test
    void testModificationCompte02() throws Exception {
        Assertions.assertThrows(UtilisateurExistantException.class,()-> service.modificationCompte("dubois","henry","acheteur0aze02@outlook.fr","acheteur0aze02@outlook.fr",13,"rue de la papeterie", "ballancourt",91610, "acheteur",null,"active"));

    }



    @Test
    void testModificationMdp01() throws Exception {
//        UtilisateurEntity u = uDao.findByEmail("acheteur002@outlook.fr").get();
//        UtilisateurEntity test = service.modificationMdp("acheteur002@outlook.fr",u.getMdp(),"021aze155");
//        Assertions.assertNotEquals(u.getMdp(),test.getMdp());
    }
    @Test
    void testModificationMdp02() throws Exception {
//        UtilisateurEntity u = uDao.findByEmail("acheteur002@outlook.fr").get();
//        Assertions.assertThrows(OldPasswordException.class,()->  service.modificationMdp("acheteur002@outlook.fr",u.getMdp(),"021aze155"));
    }
    @Test
    void testModificationMdp03() throws Exception {
//        UtilisateurEntity u = uDao.findByEmail("acheteur002@outlook.fr").get();
//       Assertions.assertThrows(UtilisateurExistantException.class,()-> service.modificationMdp("acheteur002@oeeutlook.fr",u.getMdp(),"021aze155"));
    }



@Test
    void testModificationEtat01() throws Exception {
    UtilisateurEntity test = service.modificationEtat("acheteur002@outlook.fr","desactive");
    Assertions.assertEquals("desactive",test.getEtatCompte());
    }
    @Test
    void testModificationEtat02() throws Exception {
        Assertions.assertThrows(UtilisateurExistantException.class,()-> service.modificationEtat("acheteur002@outloeeok.fr","desactive"));
    }

}