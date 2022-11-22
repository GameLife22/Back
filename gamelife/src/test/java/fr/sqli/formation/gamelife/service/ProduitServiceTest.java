package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.entity.ProduitEntity;
import fr.sqli.formation.gamelife.ex.ProduitException;
import fr.sqli.formation.gamelife.repository.ProduitRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

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
            Assertions.assertTrue(j.getNom().contains(jeuvideo));
        }
    }

    @Test
    void testRechercherJeuxVideo02() throws Exception {
        String jeuvideo = "Call";
        var jeux = service.getProductsByName(jeuvideo);
        Assertions.assertNotNull(jeux);
        Assertions.assertEquals(1, jeux.size());
        for (ProduitEntity j : jeux) {
            Assertions.assertTrue(j.getNom().toLowerCase().contains(jeuvideo.toLowerCase()));
        }
    }

    @Test
    void testRechercheJeuxVideo03() {
        String jeuvideo = "";
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.getProductsByName(jeuvideo));
    }

    @Test
    void testRechercheJeuxVideo04() {
        String jeuvideo = "%f";
        Assertions.assertThrows(ProduitException.class, () -> service.getProductsByName(jeuvideo));
    }
}