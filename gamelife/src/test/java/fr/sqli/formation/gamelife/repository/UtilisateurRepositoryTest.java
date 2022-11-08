package fr.sqli.formation.gamelife.repository;

import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback()
@Transactional
class UtilisateurRepositoryTest {

    @Autowired
    private UtilisateurRepository dao;

    @Test
    void testInsert01(){
        Optional<UtilisateurEntity> entiteFound = this.dao.findUtilisateurEntityByEmail("Test-email");
        String unNom = entiteFound.get().getNom();
        Assertions.assertEquals("Test-nom", unNom);
    }
}