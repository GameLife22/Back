package fr.sqli.formation.gamelife.repository;

import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Rollback()
@Transactional
@ActiveProfiles("test")
class UtilisateurRepositoryTest {

    @Autowired
    private UtilisateurRepository dao;

    @Test
    void testInsert01(){
        Optional<UtilisateurEntity> entiteFound = this.dao.findByEmail("admin@gamelife.fr");
        String unNom = entiteFound.get().getNom();
        Assertions.assertEquals("admin", unNom);
    }
    @Test
    void trouverEmailTest(){
        var utilisateurInserted = this.dao.findByEmail("admin@gamelife.fr");
        Assertions.assertNotNull(utilisateurInserted);
        Assertions.assertEquals("admin@gamelife.fr",utilisateurInserted.get().getEmail());
    }

    @Test
    void testSelect01(){
        Optional<UtilisateurEntity> opUtilisateurFound = this.dao.findById(UUID.fromString("ede28d8b-9170-4e8e-83b3-3c2c16c39ae8"));
        Assertions.assertTrue(opUtilisateurFound.isPresent());
        Assertions.assertEquals("admin@gamelife.fr",opUtilisateurFound.get().getEmail());
    }
}