package fr.sqli.formation.gamelife.dao;

import fr.sqli.formation.gamelife.configuration.SecuriteConfiguration;
import fr.sqli.formation.gamelife.configuration.TestConfiguration;
import fr.sqli.formation.gamelife.entite.CategorieEntite;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@Import(SecuriteConfiguration.class)
@ActiveProfiles("test")
class ICategorieDaoTest {

    private ICategorieDao categorieDao;

    @Autowired
    ICategorieDaoTest(ICategorieDao pCategorieDao) {
        categorieDao = pCategorieDao;
    }

    @Test
    public void ICategorieDao_Save_retourneCategorieSauvegarde() {
        CategorieEntite categorieEntite = new CategorieEntite();
        categorieEntite.setLibelle("Aventure");

        CategorieEntite categorieEntiteSauvegarde = this.categorieDao.save(categorieEntite);

        Assertions.assertNotNull(categorieEntiteSauvegarde);
        Assertions.assertNotEquals(0, categorieEntiteSauvegarde.getId());
        Assertions.assertEquals(categorieEntite.getLibelle(), categorieEntiteSauvegarde.getLibelle());
    }
}