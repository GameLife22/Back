package fr.sqli.formation.gamelife.service.impl;

import fr.sqli.formation.gamelife.dto.in.ProduitDtoIn;
import fr.sqli.formation.gamelife.dto.out.ProduitDtoOut;
import fr.sqli.formation.gamelife.entity.Categorie;
import fr.sqli.formation.gamelife.entity.Plateforme;
import fr.sqli.formation.gamelife.ex.EntityExistException;
import fr.sqli.formation.gamelife.ex.EntityNotFoundException;
import fr.sqli.formation.gamelife.ex.ParameterException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
// TODO: elevate time test
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ProduitServiceImplTest {
    @Autowired
    private ProduitServiceImpl service;

    private ProduitDtoIn produitDtoIn;
    private ProduitDtoOut produitDtoOut;

    @BeforeEach
    private void init() { //TODO: check
        this.produitDtoIn = new ProduitDtoIn();
        this.produitDtoIn.setId(1);
        this.produitDtoIn.setNom("fifa2023");
        this.produitDtoIn.setDescription("description");
        this.produitDtoIn.setCategorie(Categorie.SPORT.getNomCategorie()); //TODO: check
        this.produitDtoIn.setPlateforme(Plateforme.PLAYSTATION5.getNomPlateforme()); //TODO: check
        this.produitDtoIn.setEtat(true);

        this.produitDtoOut = new ProduitDtoOut();
        this.produitDtoOut.setId(1);
        this.produitDtoOut.setNom("fifa2023");
        this.produitDtoOut.setDescription("description");
        this.produitDtoOut.setCategorie(Categorie.SPORT.getNomCategorie());
        this.produitDtoOut.setPlateforme(Plateforme.PLAYSTATION5.getNomPlateforme());
        this.produitDtoOut.setEtat(true);
    }

    @Test
    public void testGetProduitShouldReturnProduit() throws ParameterException, EntityNotFoundException {
        Integer idProduit = this.produitDtoIn.getId(); // TODO: 1
        var produit = this.service.getProduit(idProduit);

        Assertions.assertEquals(this.produitDtoIn.getId(), produit.getId()); //TODO: remove
        Assertions.assertEquals(this.produitDtoOut.getNom(), produit.getNom());
        Assertions.assertEquals(this.produitDtoOut.getDescription(), produit.getDescription());
        Assertions.assertEquals(this.produitDtoOut.getCategorie(), produit.getCategorie());
        Assertions.assertEquals(this.produitDtoOut.getDescription(), produit.getDescription());
        Assertions.assertEquals(this.produitDtoOut.getEtat(), produit.getEtat());
    }

    @Test
    public void testGetProduitWithNullIdShouldThrowParameterException() {
        Integer idProduit = null;
        Assertions.assertThrows(ParameterException.class, () -> this.service.getProduit(idProduit));
    }

    @Test
    public void testGetProduitWithNegativeIdShouldThrowParameterException() {
        Integer idProduit = -1;
        Assertions.assertThrows(ParameterException.class, () -> this.service.getProduit(idProduit));
    }

    @Test
    public void testGetProduitWithIdAboveMaxExistingIdShouldThrowEntityNotFoundException() {
        Integer idProduit = this.service.getProduits().size() + 1;
        Assertions.assertThrows(EntityNotFoundException.class, () -> this.service.getProduit(idProduit));
    }

    @Test
    public void testGetProduitsShouldReturnProduits() {
        var produitsDtosOut = this.service.getProduits();

        Assertions.assertNotNull(produitsDtosOut); // TODO: check
        Assertions.assertFalse(produitsDtosOut.isEmpty(), () -> "Liste des produits vide");
    }

    @Test
    public void testAddProduitShouldReturnProduitDtoOut() throws ParameterException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, EntityExistException {
        var produitDtoIn = new ProduitDtoIn();
        produitDtoIn.setId(this.service.getProduits().size() + 1);
        produitDtoIn.setNom("nouveau jeu");
        produitDtoIn.setDescription("description");
        produitDtoIn.setCategorie(Categorie.EDUCATIF.getNomCategorie());
        produitDtoIn.setPlateforme(Plateforme.PC.getNomPlateforme());
        produitDtoIn.setEtat(false);

        var produitDtoOut = new ProduitDtoOut();
        produitDtoOut.setId(this.service.getProduits().size() + 1);
        produitDtoOut.setNom("nouveau jeu");
        produitDtoOut.setDescription("description");
        produitDtoOut.setCategorie(Categorie.EDUCATIF.getNomCategorie());
        produitDtoOut.setPlateforme(Plateforme.PC.getNomPlateforme());
        produitDtoOut.setEtat(false);

        var produitsSizeBefore = this.service.getProduits().size();
        var produitDtoOut2 = this.service.addProduit(produitDtoIn);
        var produitsSizeAfter = this.service.getProduits().size();

        Assertions.assertEquals(produitDtoOut, produitDtoOut2);
        Assertions.assertNotEquals(produitsSizeBefore, produitsSizeAfter);
    }

    @Test
    public void testAddProduitShouldThrowParameterException() {
        ProduitDtoIn produitDtoIn = null;
        Assertions.assertThrows(ParameterException.class, () -> this.service.addProduit(produitDtoIn));
    }

    @Test
    public void testAddProduitWithNomNullShouldThrowParameterException() {
        this.produitDtoIn.setNom(null);
        Assertions.assertThrows(ParameterException.class, () -> this.service.addProduit(this.produitDtoIn));
    }

    @Test
    public void testAddProduitWithDescriptionNullDescriptionNullShouldThrowParameterException() {
        this.produitDtoIn.setDescription(null);
        Assertions.assertThrows(ParameterException.class, () -> this.service.addProduit(this.produitDtoIn));
    }

    @Test
    public void testAddProduitWithCategorieNullDescriptionNullShouldThrowParameterException() {
        this.produitDtoIn.setCategorie(null);
        Assertions.assertThrows(ParameterException.class, () -> this.service.addProduit(this.produitDtoIn));
    }

    @Test
    public void testAddProduitWithPlateformeNullDescriptionNullShouldThrowParameterException() {
        this.produitDtoIn.setPlateforme(null);
        Assertions.assertThrows(ParameterException.class, () -> this.service.addProduit(this.produitDtoIn));
    }

    @Test
    public void testAddProduitWithBooleanNullDescriptionNullShouldThrowParameterException() {
        this.produitDtoIn.setEtat(null);
        Assertions.assertThrows(ParameterException.class, () -> this.service.addProduit(this.produitDtoIn));
    }

    @Test
    public void testAddProduitWithNegativeIdShouldThrowParameterException() { // TODO: check
        Integer idProduit = -1;
        this.produitDtoIn.setId(idProduit);
        Assertions.assertThrows(ParameterException.class, () -> this.service.addProduit(this.produitDtoIn));
    }

    @Test
    public void testAddProduitWithEmptyNomShouldThrowParameterException() {
        this.produitDtoIn.setNom("");
        Assertions.assertThrows(ParameterException.class, () -> this.service.addProduit(this.produitDtoIn));
    }

    @Test
    public void testAddProduitWithEmptyDescriptionShouldThrowParameterException() {
        this.produitDtoIn.setDescription("");
        Assertions.assertThrows(ParameterException.class, () -> this.service.addProduit(this.produitDtoIn));
    }

    @Test
    public void testAddProduitWithEmptyCategorieShouldThrowParameterException() {
        this.produitDtoIn.setCategorie("");
        Assertions.assertThrows(ParameterException.class, () -> this.service.addProduit(this.produitDtoIn));
    }

    @Test
    public void testAddProduitWithEmptyPlateformeShouldThrowParameterException() {
        this.produitDtoIn.setPlateforme("");
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
    public void testAddProduitWithSameProduitShouldThrowParameterException() {
        Assertions.assertThrows(EntityExistException.class, () -> this.service.addProduit(this.produitDtoIn));
    }

    @Test
    public void testUpdateProduitShouldReturnUpdatedProduit() throws ParameterException, EntityNotFoundException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        var produitDtoIn = new ProduitDtoIn();
        produitDtoIn.setId(1);
        produitDtoIn.setNom("pes2023");
        produitDtoIn.setDescription("description du nouveau pes");
        produitDtoIn.setCategorie(Categorie.CASUAL.getNomCategorie());
        produitDtoIn.setPlateforme(Plateforme.XBOXSERIESS.getNomPlateforme());
        produitDtoIn.setEtat(false);

        var produitDtoOut = this.service.updateProduit(produitDtoIn);

        Assertions.assertEquals(produitDtoIn.getNom(), produitDtoOut.getNom());
        Assertions.assertEquals(produitDtoIn.getDescription(), produitDtoOut.getDescription());
        Assertions.assertEquals(produitDtoIn.getCategorie(), produitDtoOut.getCategorie());
        Assertions.assertEquals(produitDtoIn.getDescription(), produitDtoOut.getDescription());
        Assertions.assertEquals(produitDtoIn.getEtat(), produitDtoOut.getEtat());
    }

    @Test
    public void testUpdateProduitShouldReturnProduitDtoOut() throws ParameterException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, EntityNotFoundException {
        var produitDtoOut = this.service.updateProduit(this.produitDtoIn);
        Assertions.assertEquals(this.produitDtoOut, produitDtoOut);
    }

    @Test
    public void testUpdateProduitWithNullIdShouldThrowParameterException() { // TODO: check
        Integer idProduit = null;
        this.produitDtoIn.setId(idProduit);
        Assertions.assertThrows(ParameterException.class, () -> this.service.updateProduit(this.produitDtoIn));
    }

    @Test
    public void testUpdateProduitShouldThrowParameterException() {
        ProduitDtoIn produitDtoIn = null;
        Assertions.assertThrows(ParameterException.class, () -> this.service.updateProduit(produitDtoIn));
    }

    @Test
    public void testUpdateProduitWithNomNullShouldThrowParameterException() {
        this.produitDtoIn.setNom(null);
        Assertions.assertThrows(ParameterException.class, () -> this.service.updateProduit(this.produitDtoIn));
    }

    @Test
    public void testUpdateProduitWithDescriptionNullDescriptionNullShouldThrowParameterException() {
        this.produitDtoIn.setDescription(null);
        Assertions.assertThrows(ParameterException.class, () -> this.service.updateProduit(this.produitDtoIn));
    }

    @Test
    public void testUpdateProduitWithCategorieNullDescriptionNullShouldThrowParameterException() {
        this.produitDtoIn.setCategorie(null);
        Assertions.assertThrows(ParameterException.class, () -> this.service.updateProduit(this.produitDtoIn));
    }

    @Test
    public void testUpdateProduitWithPlateformeNullDescriptionNullShouldThrowParameterException() {
        this.produitDtoIn.setPlateforme(null);
        Assertions.assertThrows(ParameterException.class, () -> this.service.updateProduit(this.produitDtoIn));
    }

    @Test
    public void testUpdateProduitWithBooleanNullDescriptionNullShouldThrowParameterException() {
        this.produitDtoIn.setEtat(null);
        Assertions.assertThrows(ParameterException.class, () -> this.service.updateProduit(this.produitDtoIn));
    }

    @Test
    public void testUpdateProduitWithNegativeIdShouldThrowParameterException() { // TODO: check
        Integer idProduit = -1;
        this.produitDtoIn.setId(idProduit);
        Assertions.assertThrows(ParameterException.class, () -> this.service.updateProduit(this.produitDtoIn));
    }

    @Test
    public void testUpdateProduitWithEmptyNomShouldThrowParameterException() {
        this.produitDtoIn.setNom("");
        Assertions.assertThrows(ParameterException.class, () -> this.service.updateProduit(this.produitDtoIn));
    }

    @Test
    public void testUpdateProduitWithEmptyDescriptionShouldThrowParameterException() {
        this.produitDtoIn.setDescription("");
        Assertions.assertThrows(ParameterException.class, () -> this.service.updateProduit(this.produitDtoIn));
    }

    @Test
    public void testUpdateProduitWithEmptyCategorieShouldThrowParameterException() {
        this.produitDtoIn.setCategorie("");
        Assertions.assertThrows(ParameterException.class, () -> this.service.updateProduit(this.produitDtoIn));
    }

    @Test
    public void testUpdateProduitWithEmptyPlateformeShouldThrowParameterException() {
        this.produitDtoIn.setPlateforme("");
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
    public void testDeleteProduitShouldReturnTrue() throws ParameterException, EntityNotFoundException {
        Integer idProduit = this.produitDtoIn.getId(); // TODO: 1
        Assertions.assertTrue(this.service.deleteProduit(idProduit));
    }

    @Test
    public void testDeleteProduitWithNegativeIdShouldReturnTrue() {
        Integer idProduit = -1;
        Assertions.assertThrows(ParameterException.class, () -> this.service.deleteProduit(idProduit));
    }

    @Test
    public void testDeleteProduitWithNullIdShouldReturnTrue() {
        Integer idProduit = null;
        Assertions.assertThrows(ParameterException.class, () -> this.service.deleteProduit(idProduit));
    }
}