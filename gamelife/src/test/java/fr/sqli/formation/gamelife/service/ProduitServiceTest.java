package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.entity.ProduitEntity;
import fr.sqli.formation.gamelife.ex.ProduitException;
import fr.sqli.formation.gamelife.repository.ProduitRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProduitServiceTest {

    @Autowired
    private ProduitService service;


    @Test
    void testGetAllProducts01() {
        var games = this.service.getAllProduit();
        Assertions.assertNotNull(games);
    }

    @Test
    void testGetProductsByName01() throws Exception {
        String game = "call";
        var aGame = service.getProductsByName(game);
        Assertions.assertNotNull(aGame);
        Assertions.assertEquals(1, aGame.size());
        for (ProduitEntity g : aGame) {
            Assertions.assertTrue(g.getNom().contains(game));
        }
    }

    @Test
    void testGetProductsByName02() throws Exception {
        String keyword = "Call";
        var game = service.getProductsByName(keyword);
        Assertions.assertNotNull(game);
        Assertions.assertEquals(1, game.size());
        for (ProduitEntity g : game) {
            Assertions.assertTrue(g.getNom().toLowerCase().contains(keyword.toLowerCase()));
        }
    }

    @Test
    void testGetProductsByName03() {
        String keyword = "";
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.getProductsByName(keyword));
    }

    @Test
    void testGetProductsByName04() {
        String keyword = "%ds";
        var games = service.getProductsByName(keyword);
        Assertions.assertNotNull(games);
        Assertions.assertEquals(new ArrayList<>(), games);
    }

    @Test
    void testGetProductsById01() {
        String id = "1";
        var game = this.service.getProductById(id);
        Assertions.assertNotNull(game);
        Assertions.assertEquals(1, game.getId());
    }

    @Test
    void testGetProductsById02() {
        String id = "";
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.service.getProductById(id));
    }


    @Test
    void testGetProductsById03() {
        String id = "1000000000";
        var game = this.service.getProductById(id);
        Assertions.assertTrue(game.getId() == 0);
    }

}