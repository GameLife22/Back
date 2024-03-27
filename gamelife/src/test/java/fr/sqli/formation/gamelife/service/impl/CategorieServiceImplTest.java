package fr.sqli.formation.gamelife.service.impl;

import fr.sqli.formation.gamelife.dto.in.CategorieDtoIn;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

//todo: add display name
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Rollback
class CategorieServiceImplTest {

    private CategorieServiceImpl service;

    @Autowired
    public CategorieServiceImplTest(CategorieServiceImpl pService) {
        this.service = pService;
    }

    @Test
     void givenValidCategorieIdDtoIn_whenGetCategorie_thenReturnCategorieDtoOut() {
        var categorieDtoOut = this.service.getCategorie(UUID.fromString("8ddee749-65db-4fa4-a5b2-74a4463cba74"));
        assertEquals(UUID.fromString("8ddee749-65db-4fa4-a5b2-74a4463cba74"), categorieDtoOut.getId(), "les identifiants ne sont pas identiques");
        assertEquals("test", categorieDtoOut.getLibelle(), "les libellés ne sont pas identiques");
    }

    @Test
    void givenInvalidCategorieIdDtoIn_whenGetCategorie_thenThrowEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> this.service.getCategorie(UUID.randomUUID()), "l'exception EntityNotFoundException n'est pas levée");
    }

    @Test
    void givenNothing_whenGetCategories_thenReturnListOfCategorieDtoOut() {
        var categoriesDtoOut = this.service.getCategories();
        assertEquals(2, categoriesDtoOut.size(), "la liste des catégories n'est pas égale à 2");
    }

    @Test
    void givenValidCategorieDtoIn_whenAddCategorie_thenReturnCategorieDtoOut() {
        var categorieDtoIn = new CategorieDtoIn();
        categorieDtoIn.setLibelle("new test");
        var categorieDtoOut = this.service.addCategorie(categorieDtoIn);
        assertEquals(categorieDtoIn.getLibelle(), categorieDtoOut.getLibelle(), "les libellés ne sont pas identiques");
    }

    @Test
    void givenValidCategorieDtoIn_whenAddCategorieExists_thenThrowEntityExistsException() {
        var categorieDtoIn = new CategorieDtoIn();
        categorieDtoIn.setLibelle("test");
        assertThrows(EntityExistsException.class, () -> this.service.addCategorie(categorieDtoIn), "la catégorie existe déjà en base de données");
    }

    @Test
    void givenValidCategorieDtoIn_whenUpdateCategorie_thenReturnCategorieDtoOut() {
        var categorieIdDtoInDtoIn = UUID.fromString("8ddee749-65db-4fa4-a5b2-74a4463cba74");
        var categorieDtoIn = new CategorieDtoIn();
        categorieDtoIn.setId(categorieIdDtoInDtoIn);
        categorieDtoIn.setLibelle("new test");

        var categorieDtoOut = this.service.updateCategorie(categorieDtoIn.getId(), categorieDtoIn);

        assertEquals(categorieDtoIn.getId(), categorieDtoOut.getId(), "les identifiants ne sont pas identiques");
        assertEquals(categorieDtoIn.getLibelle(), categorieDtoOut.getLibelle(), "les libellés ne sont pas identiques");
    }

    @Test
    void givenInvalidCategorieDtoIn_whenUpdateCategorie_thenThrowEntityNotFoundException() {
        var categorieDtoIn = new CategorieDtoIn();
        var categorieIdDtoIn = UUID.randomUUID();
        categorieDtoIn.setId(categorieIdDtoIn);
        categorieDtoIn.setLibelle("test");

        assertThrows(EntityNotFoundException.class, () -> this.service.updateCategorie(categorieIdDtoIn, categorieDtoIn), "l'exception EntityNotFoundException n'est pas levée");
    }

    @Test
    void givenValidCategorieIdDtoIn_whenDeleteCategorie_thenReturnVoid() {
        var categorieIdDtoInDtoIn = UUID.fromString("8ddee749-65db-4fa4-a5b2-74a4463cba74");
        assertDoesNotThrow(() -> this.service.deleteCategorie(categorieIdDtoInDtoIn), "une exception ne doit pas être levée");
    }

    @Test
    void givenValidCategorieIdDtoIn_whenDeleteCategorie_thenThrowEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> this.service.deleteCategorie(UUID.randomUUID()), "l'exception EntityNotFoundException n'est pas levée");
    }
}