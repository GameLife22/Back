package fr.sqli.formation.gamelife.repository;

import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.Optional;

@SpringBootTest
@Rollback()
@Transactional
class UtilisateurRepositoryTest {

    @Autowired
    private UtilisateurRepository dao;

    @Test
    void testInsert01(){
        Optional<UtilisateurEntity> entiteFound = this.dao.findByEmail("test@test.com");
        String unNom = entiteFound.get().getNom();
        Assertions.assertEquals("Test", unNom);
    }
    @Test
    void trouverEmailTest(){
        var utilisateurInserted = this.dao.findByEmail("test@test.com");
        Assertions.assertNotNull(utilisateurInserted);
        Assertions.assertEquals("test@test.com",utilisateurInserted.get().getEmail());
    }

    @Test
    void testSelect01(){
        Optional<UtilisateurEntity> opUtilisateurFound = this.dao.findById(1);
        Assertions.assertTrue(opUtilisateurFound.isPresent());
        Assertions.assertEquals("test@test.com",opUtilisateurFound.get().getEmail());
    }
}