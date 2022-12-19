package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.produit.ProduitDtoHandler;
import fr.sqli.formation.gamelife.dto.produit.ProduitDtoIn;
import fr.sqli.formation.gamelife.dto.produit.ProduitDtoOut;
import fr.sqli.formation.gamelife.entity.ProduitEntity;
import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import fr.sqli.formation.gamelife.ex.ProduitExistantException;
import fr.sqli.formation.gamelife.ex.ProduitNonExistantException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;

import java.math.BigDecimal;
import java.util.ArrayList;

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
    void testGetProductsByName04() throws Exception {
        String keyword = "%ds";
        var games = service.getProductsByName(keyword);
        Assertions.assertNotNull(games);
        Assertions.assertEquals(new ArrayList<>(), games);
    }

    @Test
    void testGetProductsById01() throws Exception {
        Integer id = 1;
        var game = this.service.getProductById(id);
        Assertions.assertNotNull(game);
        Assertions.assertEquals(1, game.getId());
    }

    @Test
    void testGetProductsById02() {
        Integer id = -1;
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.service.getProductById(id));
    }


    @Test
    void testGetProductsById03() throws Exception {
        Integer id = 1000000000;
        var game = this.service.getProductById(id);
        Assertions.assertTrue(game.getId() == 0);
    }

    @Test
    void testAjouterNouveauProduit01() throws Exception {
        ProduitDtoIn produitDtoIn = new ProduitDtoIn("test", "test", "test", "test", "test", new BigDecimal(1), 1, 1, null);
        ProduitEntity produitEntity = this.service.ajouterNouveauProduit(produitDtoIn);
        Assertions.assertNotNull(produitEntity);
        Assertions.assertEquals("test", produitEntity.getNom());
    }

    @Test
    void testDesactiverProduit01() throws Exception {
        Integer id = 1; // Problème toujours vrai aprés l'avoir tester un fois
        ProduitEntity produitEntity = this.service.desactiverProduit(id);
        Assertions.assertEquals(0, produitEntity.getEtat());
    }

    @Test
    void testDesactiverProduit02() {
        Integer id = -1;
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.service.desactiverProduit(id));
    }

    @Test
    void testDesactiverProduit03() throws Exception {
        Integer id = 1000000000;
        Assertions.assertThrows(ProduitNonExistantException.class, () -> this.service.desactiverProduit(id));
    }

}