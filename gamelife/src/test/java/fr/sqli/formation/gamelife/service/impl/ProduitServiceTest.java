package fr.sqli.formation.gamelife.service.impl;

import fr.sqli.formation.gamelife.dto.in.ProduitDtoIn;
import fr.sqli.formation.gamelife.entity.ImageEntity;
import fr.sqli.formation.gamelife.ex.EntityExistException;
import fr.sqli.formation.gamelife.ex.EntityNotFoundException;
import fr.sqli.formation.gamelife.ex.ParameterException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ProduitServiceTest {
    @Autowired
    private ProduitService service;

    private ProduitDtoIn produitDtoIn;

    @BeforeEach
    private void initProduitDtoIn() {
        ImageEntity image = new ImageEntity();
        image.setImage("test");
        image.setTitre("test");

        List<ImageEntity> images = new ArrayList<>();
        images.add(image);

        this.produitDtoIn = new ProduitDtoIn();
        this.produitDtoIn.setId(1);
        this.produitDtoIn.setNom("test");
        this.produitDtoIn.setDescription("test");
        this.produitDtoIn.setCategorie("sport");
        this.produitDtoIn.setPlateforme("playstation 5");
        this.produitDtoIn.setEtat(true);
        this.produitDtoIn.setImages(images);
    }

    @Test
    public void testGetProduitShouldReturnProduit() throws ParameterException, EntityNotFoundException {
        Integer idProduit = this.produitDtoIn.getId();
        var produitDtoOut = this.service.getProduit(idProduit);
        Assertions.assertNotNull(produitDtoOut);
    }

    @Test
    public void testGetProduitShouldThrowEntityNotFoundException() {
        Integer idProduit = 0;
        Assertions.assertThrows(EntityNotFoundException.class, () -> this.service.getProduit(idProduit));
    }

    @Test
    public void testGetProduitsShouldReturnProduits() {
        var produitsDtosOut = this.service.getProduits();
        Assertions.assertNotNull(produitsDtosOut);
        Assertions.assertFalse(produitsDtosOut.isEmpty(), () -> "Liste des produits vide");
    }

    @Test
    public void testAddProduitShouldReturnProduitDtoOut() throws ParameterException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, EntityExistException {
        var produitDtoOut = this.service.addProduit(this.produitDtoIn);

        Assertions.assertNotNull(produitDtoOut);
        Assertions.assertEquals(this.produitDtoIn.getNom(), produitDtoOut.getNom());
        Assertions.assertEquals(this.produitDtoIn.getDescription(), produitDtoOut.getDescription());
        Assertions.assertEquals(this.produitDtoIn.getCategorie(), produitDtoOut.getCategorie());
        Assertions.assertEquals(this.produitDtoIn.getDescription(), produitDtoOut.getDescription());
        Assertions.assertEquals(this.produitDtoIn.getEtat(), produitDtoOut.getEtat());
        Assertions.assertEquals(this.produitDtoIn.getImages(), produitDtoOut.getImages());
    }

    @Test
    public void testAddProduitShouldThrowParameterException() {
        ProduitDtoIn produitDtoIn = null;
        Assertions.assertThrows(ParameterException.class, () -> this.service.addProduit(produitDtoIn));
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testAddProduitWithNomNullAndEmptyShouldThrowParameterException(String pNom) {
        this.produitDtoIn.setNom(pNom);
        Assertions.assertThrows(ParameterException.class, () -> this.service.addProduit(this.produitDtoIn));
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testAddProduitWithDescriptionNullAndEmptyDescriptionShouldThrowParameterException(String pDescription) {
        this.produitDtoIn.setDescription(pDescription);
        Assertions.assertThrows(ParameterException.class, () -> this.service.addProduit(this.produitDtoIn));
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testAddProduitWithCategorieNullAndEmptyDescriptionShouldThrowParameterException(String pCategorie) {
        this.produitDtoIn.setCategorie(pCategorie);
        Assertions.assertThrows(ParameterException.class, () -> this.service.addProduit(this.produitDtoIn));
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testAddProduitWithPlateformeNullAndEmptyDescriptionShouldThrowParameterException(String pPlateforme) {
        this.produitDtoIn.setPlateforme(pPlateforme);
        Assertions.assertThrows(ParameterException.class, () -> this.service.addProduit(this.produitDtoIn));
    }

    @ParameterizedTest
    @NullSource
    public void testAddProduitWithBooleanNullDescriptionShouldThrowParameterException(Boolean pEtat) {
        this.produitDtoIn.setEtat(pEtat);
        Assertions.assertThrows(ParameterException.class, () -> this.service.addProduit(this.produitDtoIn));
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testAddProduitWithImageNullAndEmptyDescriptionShouldThrowParameterException(List<ImageEntity> pImages) {
        this.produitDtoIn.setImages(pImages);
        Assertions.assertThrows(ParameterException.class, () -> this.service.addProduit(this.produitDtoIn));
    }

    @Test
    public void testAddProduitWithIncorrectCategorieShouldThrowParameterException() {
        this.produitDtoIn.setCategorie("test");
        Assertions.assertThrows(ParameterException.class, () -> this.service.addProduit(this.produitDtoIn));
    }

    @Test
    public void testAddProduitWithIncorrectPlateformeShouldThrowParameterException() {
        this.produitDtoIn.setPlateforme("test");
        Assertions.assertThrows(ParameterException.class, () -> this.service.addProduit(this.produitDtoIn));
    }

    @Test
    public void testAddProduitWithSameProduitShouldThrowParameterException() throws EntityExistException, ParameterException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        this.service.addProduit(this.produitDtoIn);
        Assertions.assertThrows(EntityExistException.class, () -> this.service.addProduit(this.produitDtoIn));
    }

    @Test
    public void testUpdateProduitShouldReturnUpdatedProduit() throws ParameterException, EntityNotFoundException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, EntityExistException {
        ImageEntity image = new ImageEntity();
        image.setImage("test update");
        image.setTitre("test update");

        List<ImageEntity> images = new ArrayList<>();
        images.add(image);

        this.produitDtoIn.setId(1);
        this.produitDtoIn.setNom("test update");
        this.produitDtoIn.setDescription("description update");
        this.produitDtoIn.setCategorie("casual");
        this.produitDtoIn.setPlateforme("xbox series s");
        this.produitDtoIn.setEtat(false);
        this.produitDtoIn.setImages(images);

        var produitDtoOut = this.service.updateProduit(this.produitDtoIn);

        Assertions.assertNotNull(produitDtoOut);
        Assertions.assertEquals(this.produitDtoIn.getNom(), produitDtoOut.getNom());
        Assertions.assertEquals(this.produitDtoIn.getDescription(), produitDtoOut.getDescription());
        Assertions.assertEquals(this.produitDtoIn.getCategorie(), produitDtoOut.getCategorie());
        Assertions.assertEquals(this.produitDtoIn.getDescription(), produitDtoOut.getDescription());
        Assertions.assertEquals(this.produitDtoIn.getEtat(), produitDtoOut.getEtat());
        Assertions.assertEquals(this.produitDtoIn.getImages(), produitDtoOut.getImages());
    }

    @Test
    public void testUpdateProduitShouldThrowParameterException() {
        ProduitDtoIn produitDtoIn = null;
        Assertions.assertThrows(ParameterException.class, () -> this.service.updateProduit(produitDtoIn));
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testUpdateProduitWithNomNullAndEmptyShouldThrowParameterException(String pNom) {
        this.produitDtoIn.setNom(pNom);
        Assertions.assertThrows(ParameterException.class, () -> this.service.updateProduit(this.produitDtoIn));
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testUpdateProduitWithDescriptionNullAndEmptyDescriptionShouldThrowParameterException(String pDescription) {
        this.produitDtoIn.setDescription(pDescription);
        Assertions.assertThrows(ParameterException.class, () -> this.service.updateProduit(this.produitDtoIn));
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testUpdateProduitWithCategorieNullAndEmptyCategorieShouldThrowParameterException(String pCategorie) {
        this.produitDtoIn.setCategorie(pCategorie);
        Assertions.assertThrows(ParameterException.class, () -> this.service.updateProduit(this.produitDtoIn));
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testUpdateProduitWithPlateformeNullAndEmptyPlateformeShouldThrowParameterException(String pPlateforme) {
        this.produitDtoIn.setPlateforme(pPlateforme);
        Assertions.assertThrows(ParameterException.class, () -> this.service.updateProduit(this.produitDtoIn));
    }

    @Test
    public void testUpdateProduitWithBooleanNullEtatShouldThrowParameterException() {
        this.produitDtoIn.setEtat(null);
        Assertions.assertThrows(ParameterException.class, () -> this.service.updateProduit(this.produitDtoIn));
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testUpdateProduitWithImageNullAndEmptyDescriptionShouldThrowParameterException(List<ImageEntity> pImages) {
        this.produitDtoIn.setImages(pImages);
        Assertions.assertThrows(ParameterException.class, () -> this.service.updateProduit(this.produitDtoIn));
    }

    @Test
    public void testUpdateProduitWithIncorrectCategorieShouldThrowParameterException() {
        this.produitDtoIn.setCategorie("test");
        Assertions.assertThrows(ParameterException.class, () -> this.service.updateProduit(this.produitDtoIn));
    }

    @Test
    public void testUpdateProduitWithIncorrectPlateformeShouldThrowParameterException() {
        this.produitDtoIn.setPlateforme("test");
        Assertions.assertThrows(ParameterException.class, () -> this.service.updateProduit(this.produitDtoIn));
    }

    @Test
    public void testUpdateProduitWithSameProduitShouldThrowParameterException() throws EntityExistException, ParameterException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, EntityNotFoundException {
        this.service.updateProduit(this.produitDtoIn);
        Assertions.assertThrows(EntityExistException.class, () -> this.service.updateProduit(this.produitDtoIn));
    }

    @Test
    public void testDeleteProduitShouldReturnTrue() throws ParameterException, EntityNotFoundException {
        Integer idProduit = 1;
        Assertions.assertTrue(this.service.deleteProduit(idProduit));
    }
}