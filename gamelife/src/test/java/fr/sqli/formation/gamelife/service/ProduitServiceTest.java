package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.entity.ProduitEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProduitServiceTest {

    @Autowired
    private ProduitService service;

    @Test
    void testRechercherJeuxVideo01() throws Exception {
        String jeuvideo = "call";
        var jeux = service.getProductsByName(jeuvideo);
        Assertions.assertNotNull(jeux);
        Assertions.assertEquals(1, jeux.size());
        for (ProduitEntity j : jeux) {
            Assertions.assertTrue(j.getNom().startsWith(jeuvideo));
        }
    }

    /**
     * Attention: La base de données est bien case insensitive
     * @throws Exception
     */
    @Test
    void testRechercherJeuxVideo02() throws Exception {
        String jeuvideo = "Call";
        var jeux = service.getProductsByName(jeuvideo);
        Assertions.assertNotNull(jeux);
        Assertions.assertEquals(1, jeux.size());
        for (ProduitEntity j : jeux) {
            Assertions.assertTrue(j.getNom().toLowerCase().startsWith(jeuvideo.toLowerCase()));
        }
    }
}