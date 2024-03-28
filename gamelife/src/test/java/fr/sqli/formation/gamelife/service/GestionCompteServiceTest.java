package fr.sqli.formation.gamelife.service;
import fr.sqli.formation.gamelife.dto.gestionCompte.GestionCompteDto;
import fr.sqli.formation.gamelife.dto.gestionCompte.GestionEtatDto;
import fr.sqli.formation.gamelife.dto.gestionCompte.GestionMdpDto;
import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import fr.sqli.formation.gamelife.ex.UtilisateurExistantException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@Rollback
@Transactional
@ActiveProfiles("test")
class GestionCompteServiceTest {
    GestionCompteService service;

    BCryptPasswordEncoder encoder;

    @Autowired
    public GestionCompteServiceTest(GestionCompteService pService, BCryptPasswordEncoder pEncoder) {
        this.service = pService;
        this.encoder = pEncoder;
    }

    @Test
    void testModificationCompte01() throws Exception {
        GestionCompteDto dto = new GestionCompteDto(UUID.fromString("c81f4beb-d17d-4b68-8a10-195745ddb894"), "dubois","henry","acheteur2@gamelife.fr",13,"rue de la papeterie", "Ballancourt",91610,null);
        UtilisateurEntity test = service.modificationCompte(dto);
        Assertions.assertEquals("Ballancourt",test.getVille());
    }
    @Test
    void testModificationCompte02() throws Exception {
        GestionCompteDto dto = new GestionCompteDto(UUID.randomUUID(), "acheteur", "acheteur", "acheteur@test.fr", 2, "rue du marechal", "nantes", 44000, null);
        Assertions.assertThrows(UtilisateurExistantException.class,()-> service.modificationCompte(dto));
    }

    @Test
    void testModificationMdp01() throws Exception {
        GestionMdpDto dto = new GestionMdpDto(UUID.fromString("c81f4beb-d17d-4b68-8a10-195745ddb894"),"Test1234?!", "Test123?!");
        UtilisateurEntity test = service.modificationMdp(dto);
        Assertions.assertTrue(encoder.matches("Test1234?!", test.getMdp()));
    }

    @Test
    void testModificationMdp02() throws Exception {
        GestionMdpDto dto = new GestionMdpDto(UUID.fromString("c81f4beb-d17d-4b68-8a10-195745ddb894"),"Test1234?!", "Test123?!");
        UtilisateurEntity test = service.modificationMdp(dto);
        Assertions.assertFalse(encoder.matches("Test123?!", test.getMdp()));
    }

    @Test
    void testModificationMdp03() throws Exception {
        GestionMdpDto dto = new GestionMdpDto(UUID.randomUUID(),"021aze155", "021aze155");
        Assertions.assertThrows(UtilisateurExistantException.class,()-> service.modificationMdp(dto));
    }

@Test
    void testModificationEtat01() throws Exception {
    GestionEtatDto dto = new GestionEtatDto(UUID.fromString("c81f4beb-d17d-4b68-8a10-195745ddb894"),true);
    UtilisateurEntity test = service.modificationEtat(dto);
    Assertions.assertTrue(test.getEtatCompte().booleanValue());
    }

    @Test
    void testModificationEtat02() throws Exception {
        GestionEtatDto dto = new GestionEtatDto(UUID.randomUUID(),true);
        Assertions.assertThrows(UtilisateurExistantException.class,()-> service.modificationEtat(dto));
    }

    @Test
    void testEstRevendeur01() throws Exception{
        Assertions.assertTrue(service.estRevendeur(UUID.fromString("906d837f-c451-4aa1-9bc1-e92038e93f1d")));
    }

    @Test
    void testEstRevendeur02() throws Exception{
        Assertions.assertFalse(service.estRevendeur(UUID.fromString("c81f4beb-d17d-4b68-8a10-195745ddb894")));
    }

    @Test
    void testEstRevendeur03() throws Exception{
        Assertions.assertThrows(UtilisateurExistantException.class, ()-> service.estRevendeur(UUID.randomUUID()));
    }
}
