package fr.sqli.formation.gamelife.repository;

import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Rollback()
@Transactional
class UtilisateurRepositoryTest {

    @Autowired
    private UtilisateurRepository dao;

    @Test
    void testInsert01(){
        Optional<UtilisateurEntity> entiteFound = this.dao.findByEmail("fabien.bidault@social.aston-ecole.com");
        String unNom = entiteFound.get().getNom();
        Assertions.assertEquals("admin", unNom);
    }
    @Test
    void trouverEmailTest(){
        var utilisateurInserted = this.dao.findByEmail("fabien.bidault@social.aston-ecole.com");
        Assertions.assertNotNull(utilisateurInserted);
        Assertions.assertEquals("fabien.bidault@social.aston-ecole.com",utilisateurInserted.get().getEmail());
    }

    @Test
    void testSelect01(){
        Optional<UtilisateurEntity> opUtilisateurFound = this.dao.findById(1);
        Assertions.assertTrue(opUtilisateurFound.isPresent());
        Assertions.assertEquals("fabien.bidault@social.aston-ecole.com",opUtilisateurFound.get().getEmail());
    }
}