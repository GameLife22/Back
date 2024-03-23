package fr.sqli.formation.gamelife.service.impl;

import fr.sqli.formation.gamelife.dto.in.ImageDtoIn;
import fr.sqli.formation.gamelife.dto.in.ProduitDtoIn;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

//todo: javadoc
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ProduitServiceImplTest {
    @Autowired
    private ProduitServiceImpl service;

    private ProduitDtoIn produitDtoIn;

    // JSR-380
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @BeforeEach
    private void initProduitDtoIn() {
        produitDtoIn = new ProduitDtoIn();
        ImageDtoIn image = new ImageDtoIn();
    }

    @Test
    public void testGetProduitShouldReturnProduit() {
        //Integer idProduit = produitDtoIn.getId();
        //var produitDtoOut = this.service.getProduit(idProduit);
        //todo: replace
        //Assertions.assertNotNull(produitDtoOut);
    }

    @Test
    public void testGetProduitsShouldReturnProduits() {
        var produitsDtosOut = this.service.getProduits();
        Assertions.assertNotNull(produitsDtosOut);
        Assertions.assertFalse(produitsDtosOut.isEmpty(), () -> "Liste des produits vide");
    }

    @Test
    public void testAddProduitShouldReturnProduitDtoOut() {
        var produitDtoOut = this.service.addProduit(produitDtoIn);
        Assertions.assertNotNull(produitDtoOut);
        Assertions.assertEquals(produitDtoIn.getNom(), produitDtoOut.getNom());
        Assertions.assertEquals(produitDtoIn.getDescription(), produitDtoOut.getDescription());
        Assertions.assertEquals(produitDtoIn.getCategorie(), produitDtoOut.getCategorie());
        Assertions.assertEquals(produitDtoIn.getPlateforme(), produitDtoOut.getPlateforme());
        Assertions.assertEquals(produitDtoIn.getEtat(), produitDtoOut.getEtat());
        Assertions.assertEquals(produitDtoIn.getImages(), produitDtoOut.getImages());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testAddProduitWithNullAndEmptyNomShouldThrowParameterException(String pNom) {
        produitDtoIn.setNom(pNom);
        Set<ConstraintViolation<ProduitDtoIn>> violations = validator.validate(produitDtoIn);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testAddProduitWithNullAndEmptyDescriptionShouldThrowParameterException(String pDescription) {
        produitDtoIn.setDescription(pDescription);
        Set<ConstraintViolation<ProduitDtoIn>> violations = validator.validate(produitDtoIn);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testAddProduitWithNullAndEmptyCategorieShouldThrowParameterException(String pCategorie) {
        produitDtoIn.setCategorie(pCategorie);
        Set<ConstraintViolation<ProduitDtoIn>> violations = validator.validate(produitDtoIn);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testAddProduitWithNullAndEmptyPlateformeShouldThrowParameterException(String pPlateforme) {
        produitDtoIn.setPlateforme(pPlateforme);
        Set<ConstraintViolation<ProduitDtoIn>> violations = validator.validate(produitDtoIn);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testAddProduitWithNullAndEmptyImagesShouldThrowParameterException(List<ImageDtoIn> pImages) {
        produitDtoIn.setImages(pImages);
        Set<ConstraintViolation<ProduitDtoIn>> violations = validator.validate(produitDtoIn);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    public void testAddProduitWithIncorrectCategorieShouldThrowParameterException() {
        produitDtoIn.setCategorie("test");
        Assertions.assertThrows(EnumConstantNotPresentException.class, () -> this.service.addProduit(produitDtoIn));
    }
    @Test
    public void testAddProduitWithIncorrectPlateformeShouldThrowParameterException() {
        produitDtoIn.setPlateforme("test");
        Assertions.assertThrows(EnumConstantNotPresentException.class, () -> this.service.addProduit(produitDtoIn));
    }

    @Test
    public void testAddProduitWithSameProduitShouldThrowParameterException() {
        this.service.addProduit(produitDtoIn);
        Assertions.assertThrows(EntityExistsException.class, () -> this.service.addProduit(produitDtoIn));
    }

    @Test
    public void testUpdateProduitShouldReturnUpdatedProduit() {
        var produitDtoOut = this.service.updateProduit(produitDtoIn);
        Assertions.assertNotNull(produitDtoOut);
        Assertions.assertEquals(produitDtoIn.getId(), produitDtoOut.getId());
        Assertions.assertEquals(produitDtoIn.getNom(), produitDtoOut.getNom());
        Assertions.assertEquals(produitDtoIn.getDescription(), produitDtoOut.getDescription());
        Assertions.assertEquals(produitDtoIn.getCategorie(), produitDtoOut.getCategorie());
        Assertions.assertEquals(produitDtoIn.getPlateforme(), produitDtoOut.getPlateforme());
        Assertions.assertEquals(produitDtoIn.getEtat(), produitDtoOut.getEtat());
        Assertions.assertEquals(produitDtoIn.getImages(), produitDtoOut.getImages());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testUpdateProduitWithNullAndEmptyNomShouldThrowParameterException(String pNom) {
        produitDtoIn.setNom(pNom);
        Set<ConstraintViolation<ProduitDtoIn>> violations = validator.validate(produitDtoIn);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testUpdateProduitWithNullAndEmptyDescriptionShouldThrowParameterException(String pDescription) {
        produitDtoIn.setDescription(pDescription);
        Set<ConstraintViolation<ProduitDtoIn>> violations = validator.validate(produitDtoIn);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testUpdateProduitWithNullAndEmptyCategorieShouldThrowParameterException(String pCategorie) {
        produitDtoIn.setCategorie(pCategorie);
        Set<ConstraintViolation<ProduitDtoIn>> violations = validator.validate(produitDtoIn);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testUpdateProduitWithNullAndEmptyPlateformeShouldThrowParameterException(String pPlateforme) {
        produitDtoIn.setPlateforme(pPlateforme);
        Set<ConstraintViolation<ProduitDtoIn>> violations = validator.validate(produitDtoIn);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testUpdateProduitWithNullAndEmptyImagesShouldThrowParameterException(List<ImageDtoIn> pImages) {
        produitDtoIn.setImages(pImages);
        Set<ConstraintViolation<ProduitDtoIn>> violations = validator.validate(produitDtoIn);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    public void testUpdateProduitWithIncorrectCategorieShouldThrowParameterException() {
        produitDtoIn.setCategorie("test");
        Assertions.assertThrows(EnumConstantNotPresentException.class, () -> this.service.updateProduit(produitDtoIn));
    }

    @Test
    public void testUpdateProduitWithIncorrectPlateformeShouldThrowParameterException() {
        produitDtoIn.setPlateforme("test");
        Assertions.assertThrows(EnumConstantNotPresentException.class, () -> this.service.updateProduit(produitDtoIn));
    }

    @Test
    public void testDeleteProduitShouldReturnVoid() {
        //Integer pIdProduit = 1;
        //this.service.deleteProduit(pIdProduit);
        //todo: replace
        //Assertions.assertThrows(EntityNotFoundException.class, () -> this.service.getProduit(pIdProduit));
    }
}