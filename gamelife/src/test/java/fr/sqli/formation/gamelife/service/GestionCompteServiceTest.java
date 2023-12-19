package fr.sqli.formation.gamelife.service;
import fr.sqli.formation.gamelife.dto.gestionCompte.GestionCompteDto;
import fr.sqli.formation.gamelife.dto.gestionCompte.GestionEtatDto;
import fr.sqli.formation.gamelife.dto.gestionCompte.GestionMdpDto;
import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import fr.sqli.formation.gamelife.ex.UtilisateurExistantException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Rollback
@Transactional
@ActiveProfiles("application-test")
class GestionCompteServiceTest {
    @Autowired
    GestionCompteService service;
    @Autowired
    BCryptPasswordEncoder encoder;

    // La différence entre un test unitaire et un test d'intégration est que le test unitaire est autonome, il ne fait appel à aucune BDD
    // Le tes d'intégration lui utilise l'environement complet pour s'effectuer.
    // Dans notre cas nous avons trouvé plus pertinant d'utiliser des test qui s'effectue dans un environnement similaire à la prod

    // Methode modificationCompte
    @Test
    void testModificationCompte01() throws Exception {
        GestionCompteDto dto = new GestionCompteDto(4, "dubois","henry","acheteur002@outlook.fr",13,"rue de la papeterie", "Ballancourt",91610,null);
        UtilisateurEntity test = service.modificationCompte(dto);
        Assertions.assertEquals("Ballancourt",test.getVille());
    }
    @Test
    void testModificationCompte02() throws Exception {
        GestionCompteDto dto = new GestionCompteDto(24, "dubois","henry","acheteur002@outlook.fr",13,"rue de la papeterie", "Ballancourt",91610,null);
        Assertions.assertThrows(UtilisateurExistantException.class,()-> service.modificationCompte(dto));

    }


    // Methode modificationMdp
    @Test
    void testModificationMdp01() throws Exception {
        GestionMdpDto dto = new GestionMdpDto(1,"Paz6!!1333", "Paz6!!3");
        UtilisateurEntity test = service.modificationMdp(dto);
        System.out.println(encoder.matches("Paz6!!3", test.getMdp()));
        Assertions.assertTrue(encoder.matches("Paz6!!1333", test.getMdp()));
    }
    @Test
    void testModificationMdp02() throws Exception {
        GestionMdpDto dto = new GestionMdpDto(1,"Paz6!!1333", "Paz6!!3");
        UtilisateurEntity test = service.modificationMdp(dto);
        Assertions.assertFalse(encoder.matches("Paz6!!3", test.getMdp()));
    }
    @Test
    void testModificationMdp03() throws Exception {
        GestionMdpDto dto = new GestionMdpDto(24,"021aze155", "021aze155");
        Assertions.assertThrows(UtilisateurExistantException.class,()-> service.modificationMdp(dto));
    }



@Test
    void testModificationEtat01() throws Exception {
    GestionEtatDto dto = new GestionEtatDto(1,0);
    UtilisateurEntity test = service.modificationEtat(dto);
    Assertions.assertEquals(0,test.getEtatCompte());
    }
    @Test
    void testModificationEtat02() throws Exception {
        GestionEtatDto dto = new GestionEtatDto(45,0);
        Assertions.assertThrows(UtilisateurExistantException.class,()-> service.modificationEtat(dto));
    }



    @Test
    void testEstRevendeur01() throws Exception{
        Assertions.assertTrue(service.estRevendeur(2));
    }

    @Test
    void testEstRevendeur02() throws Exception{
        Assertions.assertFalse(service.estRevendeur(4));
    }

    @Test
    void testEstRevendeur03() throws Exception{
        Assertions.assertThrows(UtilisateurExistantException.class, ()-> service.estRevendeur(25));
    }
}
