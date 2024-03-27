package fr.sqli.formation.gamelife.service.impl;

import fr.sqli.formation.gamelife.dto.in.*;
import fr.sqli.formation.gamelife.dto.in.ProduitDtoIn;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

//todo: add display name
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Rollback
class ProduitServiceImplTest {

    private ProduitServiceImpl service;

    @Autowired
    public ProduitServiceImplTest(ProduitServiceImpl pService) {
        this.service = pService;
    }

    @Test
    void givenValidProduitIdDtoIn_whenGetProduit_thenReturnProduitDtoOut() {
        var produitDtoOut = this.service.getProduit(UUID.fromString("6a4a4185-cdb4-418e-9249-a160e384d877"));

        assertEquals(UUID.fromString("6a4a4185-cdb4-418e-9249-a160e384d877"), produitDtoOut.getId(), "les identifiants du produit ne sont pas identiques");
        assertEquals("test", produitDtoOut.getNom(), "les noms du produit ne sont pas identiques");
        assertEquals("test", produitDtoOut.getDescription(), "les descriptions du produit ne sont pas identiques");
        assertEquals(true, produitDtoOut.getEtat(), "les états du produit ne sont pas identiques");
        assertEquals(UUID.fromString("8ddee749-65db-4fa4-a5b2-74a4463cba74"), produitDtoOut.getCategorie().getId(), "les identifiants de la catégorie  ne sont pas identiques");
        assertEquals("test", produitDtoOut.getCategorie().getLibelle(), "les libellés de la catégorie ne sont pas identiques");
        assertEquals(UUID.fromString("4a523eed-9372-4979-89a8-2eec8d41f696"), produitDtoOut.getPlateforme().getId(), "les identifiants de la plateforme ne sont pas identiques");
        assertEquals("test", produitDtoOut.getPlateforme().getLibelle(), "les libellés de la plateforme ne sont pas identiques");
        assertEquals(UUID.fromString("d9798c40-173f-4061-bc2e-a39edece42ff"), produitDtoOut.getImages().get(0).getId(), "les identifiants de l'image ne sont pas identiques");
        assertEquals("test", produitDtoOut.getImages().get(0).getImage(), "les images de l'image ne sont pas identiques");
        assertEquals("test", produitDtoOut.getImages().get(0).getTitre(), "les titres de l'image n'est pas identique");
    }

    @Test
    void givenInvalidProduitIdDtoIn_whenGetProduit_thenReturnPlateformeDtoOut() {
        assertThrows(EntityNotFoundException.class, () -> this.service.getProduit(UUID.randomUUID()), "l'exception EntityNotFoundException n'est pas levée");
    }

    @Test
    void givenNothing_whenGetProduits_thenReturnListOfProduitDtoOut() {
        var produitsDtoOut = this.service.getProduits();
        assertEquals(1, produitsDtoOut.size(), "la liste des produits n'est pas égale à 1");
    }

    @Test
    void givenValidProduitDtoIn_whenAddProduit_thenReturnProduitDtoOut() {
        var produitDtoIn = new ProduitDtoIn();
        produitDtoIn.setNom("new test");
        produitDtoIn.setDescription("new test");
        produitDtoIn.setEtat(false);

        var categorieDtoIn = new CategorieDtoIn();
        categorieDtoIn.setId(UUID.fromString("8ddee749-65db-4fa4-a5b2-74a4463cba74"));
        categorieDtoIn.setLibelle("test");
        produitDtoIn.setCategorie(categorieDtoIn);

        var plateformeDtoIn = new PlateformeDtoIn();
        plateformeDtoIn.setId(UUID.fromString("4a523eed-9372-4979-89a8-2eec8d41f696"));
        plateformeDtoIn.setLibelle("test");
        produitDtoIn.setPlateforme(plateformeDtoIn);

        var imageDtoIn = new ImageDtoIn();
        imageDtoIn.setTitre("new test");
        imageDtoIn.setImage("new test");
        produitDtoIn.setImages(List.of(imageDtoIn));

        var produitDtoOut = this.service.addProduit(produitDtoIn);

        assertEquals(produitDtoIn.getNom(), produitDtoOut.getNom(), "les noms du produit ne sont pas identiques");
        assertEquals(produitDtoIn.getDescription(), produitDtoOut.getDescription(), "les descriptions du produit ne sont pas identiques");
        assertEquals(produitDtoIn.getEtat(), produitDtoOut.getEtat(), "les états du produit ne sont pas identiques");
        assertEquals(produitDtoIn.getCategorie().getLibelle(), produitDtoOut.getCategorie().getLibelle(), "les libellés de catégorie ne sont pas identiques");
        assertEquals(produitDtoIn.getPlateforme().getLibelle(), produitDtoOut.getPlateforme().getLibelle(), "les libellés de plateforme ne sont pas identiques");
        assertEquals(produitDtoIn.getImages().get(0).getImage(), produitDtoOut.getImages().get(0).getImage(), "les images de l'image ne sont pas identiques");
        assertEquals(produitDtoIn.getImages().get(0).getTitre(), produitDtoOut.getImages().get(0).getTitre(), "les titres de l'image ne sont pas identiques");
    }

    //update all fields
    @Test
    void givenValidProduitDtoIn_whenUpdateProduit1_thenReturnProduitDtoOut() {
        var produitDtoIn = new ProduitDtoIn();
        produitDtoIn.setId(UUID.fromString("6a4a4185-cdb4-418e-9249-a160e384d877"));
        produitDtoIn.setNom("new test");
        produitDtoIn.setDescription("new test");
        produitDtoIn.setEtat(false);

        var categorieDtoIn = new CategorieDtoIn();
        categorieDtoIn.setId(UUID.fromString("8192d7fe-2789-43c3-9789-9f554ff9b9fb"));
        categorieDtoIn.setLibelle("test 2");
        produitDtoIn.setCategorie(categorieDtoIn);

        var plateformeDtoIn = new PlateformeDtoIn();
        plateformeDtoIn.setId(UUID.fromString("171642f4-d2b5-49a3-91f9-dd31bf07d237"));
        plateformeDtoIn.setLibelle("test 2");
        produitDtoIn.setPlateforme(plateformeDtoIn);

        var imageDtoIn1 = new ImageDtoIn();
        imageDtoIn1.setId(UUID.fromString("d9798c40-173f-4061-bc2e-a39edece42ff"));
        imageDtoIn1.setTitre("new test 1");
        imageDtoIn1.setImage("new test 1");

        var imageDtoIn2 = new ImageDtoIn();
        imageDtoIn2.setId(UUID.fromString("1b014282-3fa7-4997-be24-4538dbb74f81"));
        imageDtoIn2.setTitre("new test 2");
        imageDtoIn2.setImage("new test 2");

        produitDtoIn.setImages(List.of(imageDtoIn1, imageDtoIn2));

        var produitDtoOut = this.service.updateProduit(produitDtoIn.getId(), produitDtoIn);

        assertEquals(produitDtoIn.getId(), produitDtoOut.getId(), "les identifiants du produit ne sont pas identiques");
        assertEquals(produitDtoIn.getNom(), produitDtoOut.getNom(), "les noms du produit sont identiques");
        assertEquals(produitDtoIn.getDescription(), produitDtoOut.getDescription(), "les descriptions du produit sont identiques");
        assertEquals(produitDtoIn.getEtat(), produitDtoOut.getEtat(), "les états du produit sont identiques");
        assertEquals(produitDtoIn.getCategorie().getId(), produitDtoOut.getCategorie().getId(), "les identifiants de la catégorie ne sont pas identiques");
        assertEquals(produitDtoIn.getCategorie().getLibelle(), produitDtoOut.getCategorie().getLibelle(), "les libellés de la catégorie ne sont pas identiques");
        assertEquals(produitDtoIn.getPlateforme().getId(), produitDtoOut.getPlateforme().getId(), "les identifiants de la plateforme ne sont pas identiques");
        assertEquals(produitDtoIn.getPlateforme().getLibelle(), produitDtoOut.getPlateforme().getLibelle(), "les libellés de la plateforme ne sont pas identiques");
        assertEquals(produitDtoIn.getImages().get(0).getId(), produitDtoOut.getImages().get(0).getId(), "les identifiants de l'image 1 ne sont pas identiques");
        assertEquals(produitDtoIn.getImages().get(0).getImage(), produitDtoOut.getImages().get(0).getImage(), "les images de l'image 1 ne sont pas identiques");
        assertEquals(produitDtoIn.getImages().get(0).getTitre(), produitDtoOut.getImages().get(0).getTitre(), "les titres de l'image 1 ne sont pas identiques");
        assertEquals(produitDtoIn.getImages().get(1).getId(), produitDtoOut.getImages().get(1).getId(), "les identifiants de l'image 2 ne sont pas identiques");
        assertEquals(produitDtoIn.getImages().get(1).getImage(), produitDtoOut.getImages().get(1).getImage(), "les images de l'image 2 ne sont pas identiques");
        assertEquals(produitDtoIn.getImages().get(1).getTitre(), produitDtoOut.getImages().get(1).getTitre(), "les titres de l'image 2 ne sont pas identiques");
        assertEquals(2, produitDtoOut.getImages().size(), "la liste des images du produit n'est pas égale à 2");
    }

    //create new image
    @Test
    void givenValidProduitDtoIn_whenUpdateProduit2_thenReturnProduitDtoOut() {
        var produitDtoIn = new ProduitDtoIn();
        produitDtoIn.setId(UUID.fromString("6a4a4185-cdb4-418e-9249-a160e384d877"));
        produitDtoIn.setNom("new test");
        produitDtoIn.setDescription("new test");
        produitDtoIn.setEtat(false);

        var categorieDtoIn = new CategorieDtoIn();
        categorieDtoIn.setId(UUID.fromString("8ddee749-65db-4fa4-a5b2-74a4463cba74"));
        categorieDtoIn.setLibelle("test");
        produitDtoIn.setCategorie(categorieDtoIn);

        var plateformeDtoIn = new PlateformeDtoIn();
        plateformeDtoIn.setId(UUID.fromString("4a523eed-9372-4979-89a8-2eec8d41f696"));
        plateformeDtoIn.setLibelle("test");
        produitDtoIn.setPlateforme(plateformeDtoIn);

        var imageDtoIn1 = new ImageDtoIn();
        imageDtoIn1.setId(UUID.fromString("d9798c40-173f-4061-bc2e-a39edece42ff"));
        imageDtoIn1.setTitre("test");
        imageDtoIn1.setImage("test");

        var imageDtoIn2 = new ImageDtoIn();
        imageDtoIn2.setId(UUID.fromString("1b014282-3fa7-4997-be24-4538dbb74f81"));
        imageDtoIn2.setTitre("test 2");
        imageDtoIn2.setImage("test 2");

        var imageDtoIn3 = new ImageDtoIn();
        imageDtoIn3.setTitre("new test");
        imageDtoIn3.setImage("new test");

        produitDtoIn.setImages(List.of(imageDtoIn1, imageDtoIn2, imageDtoIn3));

        var produitDtoOut = this.service.updateProduit(produitDtoIn.getId(), produitDtoIn);

        assertEquals(produitDtoIn.getId(), produitDtoOut.getId(), "les identifiants du produit ne sont pas identiques");
        assertEquals(produitDtoIn.getNom(), produitDtoOut.getNom(), "les noms du produit sont identiques");
        assertEquals(produitDtoIn.getDescription(), produitDtoOut.getDescription(), "les descriptions du produit sont identiques");
        assertEquals(produitDtoIn.getEtat(), produitDtoOut.getEtat(), "les états du produit sont identiques");
        assertEquals(produitDtoIn.getCategorie().getId(), produitDtoOut.getCategorie().getId(), "les identifiants de la catégorie ne sont pas identiques");
        assertEquals(produitDtoIn.getCategorie().getLibelle(), produitDtoOut.getCategorie().getLibelle(), "les libellés de la catégorie ne sont pas identiques");
        assertEquals(produitDtoIn.getPlateforme().getId(), produitDtoOut.getPlateforme().getId(), "les identifiants de la plateforme ne sont pas identiques");
        assertEquals(produitDtoIn.getPlateforme().getLibelle(), produitDtoOut.getPlateforme().getLibelle(), "les libellés de la plateforme ne sont pas identiques");
        assertEquals(produitDtoIn.getImages().get(0).getId(), produitDtoOut.getImages().get(0).getId(), "les identifiants de l'image 1 ne sont pas identiques");
        assertEquals(produitDtoIn.getImages().get(0).getImage(), produitDtoOut.getImages().get(0).getImage(), "les images de l'image 1 ne sont pas identiques");
        assertEquals(produitDtoIn.getImages().get(0).getTitre(), produitDtoOut.getImages().get(0).getTitre(), "les titres de l'image 1 ne sont pas identiques");
        assertEquals(produitDtoIn.getImages().get(1).getId(), produitDtoOut.getImages().get(1).getId(), "les identifiants de l'image 2 ne sont pas identiques");
        assertEquals(produitDtoIn.getImages().get(1).getImage(), produitDtoOut.getImages().get(1).getImage(), "les images de l'image 2 ne sont pas identiques");
        assertEquals(produitDtoIn.getImages().get(1).getTitre(), produitDtoOut.getImages().get(1).getTitre(), "les titres de l'image 2 ne sont pas identiques");
        assertEquals(produitDtoIn.getImages().get(2).getImage(), produitDtoOut.getImages().get(2).getImage(), "les images de l'image 3 ne sont pas identiques");
        assertEquals(produitDtoIn.getImages().get(2).getTitre(), produitDtoOut.getImages().get(2).getTitre(), "les titres de l'image 3 ne sont pas identiques");
        assertEquals(3, produitDtoOut.getImages().size(), "la liste des images du produit n'est pas égale à 3");
    }

    //remove existing image
    @Test
    void givenValidProduitDtoIn_whenUpdateProduit3_thenReturnProduitDtoOut() {
        var produitDtoIn = new ProduitDtoIn();
        produitDtoIn.setId(UUID.fromString("6a4a4185-cdb4-418e-9249-a160e384d877"));
        produitDtoIn.setNom("new test");
        produitDtoIn.setDescription("new test");
        produitDtoIn.setEtat(false);

        var categorieDtoIn = new CategorieDtoIn();
        categorieDtoIn.setId(UUID.fromString("8ddee749-65db-4fa4-a5b2-74a4463cba74"));
        categorieDtoIn.setLibelle("test");
        produitDtoIn.setCategorie(categorieDtoIn);

        var plateformeDtoIn = new PlateformeDtoIn();
        plateformeDtoIn.setId(UUID.fromString("4a523eed-9372-4979-89a8-2eec8d41f696"));
        plateformeDtoIn.setLibelle("test");
        produitDtoIn.setPlateforme(plateformeDtoIn);

        var imageDtoIn = new ImageDtoIn();
        imageDtoIn.setId(UUID.fromString("d9798c40-173f-4061-bc2e-a39edece42ff"));
        imageDtoIn.setTitre("test");
        imageDtoIn.setImage("test");

        produitDtoIn.setImages(List.of(imageDtoIn));

        var produitDtoOut = this.service.updateProduit(produitDtoIn.getId(), produitDtoIn);

        assertEquals(produitDtoIn.getId(), produitDtoOut.getId(), "les identifiants du produit ne sont pas identiques");
        assertEquals(produitDtoIn.getNom(), produitDtoOut.getNom(), "les noms du produit sont identiques");
        assertEquals(produitDtoIn.getDescription(), produitDtoOut.getDescription(), "les descriptions du produit sont identiques");
        assertEquals(produitDtoIn.getEtat(), produitDtoOut.getEtat(), "les états du produit sont identiques");
        assertEquals(produitDtoIn.getCategorie().getId(), produitDtoOut.getCategorie().getId(), "les identifiants de la catégorie ne sont pas identiques");
        assertEquals(produitDtoIn.getCategorie().getLibelle(), produitDtoOut.getCategorie().getLibelle(), "les libellés de la catégorie ne sont pas identiques");
        assertEquals(produitDtoIn.getPlateforme().getId(), produitDtoOut.getPlateforme().getId(), "les identifiants de la plateforme ne sont pas identiques");
        assertEquals(produitDtoIn.getPlateforme().getLibelle(), produitDtoOut.getPlateforme().getLibelle(), "les libellés de la plateforme ne sont pas identiques");
        assertEquals(produitDtoIn.getImages().get(0).getImage(), produitDtoOut.getImages().get(0).getImage(), "les images de l'image ne sont pas identiques");
        assertEquals(produitDtoIn.getImages().get(0).getTitre(), produitDtoOut.getImages().get(0).getTitre(), "les titres de l'image ne sont pas identiques");
        assertEquals(1, produitDtoOut.getImages().size(), "la liste des images du produit n'est pas égale à 1");
    }

    // update & remove image
    @Test
    void givenValidProduitDtoIn_whenUpdateProduit4_thenReturnProduitDtoOut() {
        var produitDtoIn = new ProduitDtoIn();
        produitDtoIn.setId(UUID.fromString("6a4a4185-cdb4-418e-9249-a160e384d877"));
        produitDtoIn.setNom("new test");
        produitDtoIn.setDescription("new test");
        produitDtoIn.setEtat(false);

        var categorieDtoIn = new CategorieDtoIn();
        categorieDtoIn.setId(UUID.fromString("8ddee749-65db-4fa4-a5b2-74a4463cba74"));
        categorieDtoIn.setLibelle("test");
        produitDtoIn.setCategorie(categorieDtoIn);

        var plateformeDtoIn = new PlateformeDtoIn();
        plateformeDtoIn.setId(UUID.fromString("4a523eed-9372-4979-89a8-2eec8d41f696"));
        plateformeDtoIn.setLibelle("test");
        produitDtoIn.setPlateforme(plateformeDtoIn);

        var imageDtoIn = new ImageDtoIn();
        imageDtoIn.setId(UUID.fromString("d9798c40-173f-4061-bc2e-a39edece42ff"));
        imageDtoIn.setTitre("new test");
        imageDtoIn.setImage("new test");

        produitDtoIn.setImages(List.of(imageDtoIn));

        var produitDtoOut = this.service.updateProduit(produitDtoIn.getId(), produitDtoIn);

        assertEquals(produitDtoIn.getId(), produitDtoOut.getId(), "les identifiants du produit ne sont pas identiques");
        assertEquals(produitDtoIn.getNom(), produitDtoOut.getNom(), "les noms du produit sont identiques");
        assertEquals(produitDtoIn.getDescription(), produitDtoOut.getDescription(), "les descriptions du produit sont identiques");
        assertEquals(produitDtoIn.getEtat(), produitDtoOut.getEtat(), "les états du produit sont identiques");
        assertEquals(produitDtoIn.getCategorie().getId(), produitDtoOut.getCategorie().getId(), "les identifiants de la catégorie ne sont pas identiques");
        assertEquals(produitDtoIn.getCategorie().getLibelle(), produitDtoOut.getCategorie().getLibelle(), "les libellés de la catégorie ne sont pas identiques");
        assertEquals(produitDtoIn.getPlateforme().getId(), produitDtoOut.getPlateforme().getId(), "les identifiants de la plateforme ne sont pas identiques");
        assertEquals(produitDtoIn.getPlateforme().getLibelle(), produitDtoOut.getPlateforme().getLibelle(), "les libellés de la plateforme ne sont pas identiques");
        assertEquals(produitDtoIn.getImages().get(0).getImage(), produitDtoOut.getImages().get(0).getImage(), "les images de l'image ne sont pas identiques");
        assertEquals(produitDtoIn.getImages().get(0).getTitre(), produitDtoOut.getImages().get(0).getTitre(), "les titres de l'image ne sont pas identiques");
        assertEquals(1, produitDtoOut.getImages().size(), "la liste des images du produit n'est pas égale à 1");
    }

    @Test
    void givenInvalidProduitIdDtoIn_whenUpdateProduit_thenThrowEntityNotFoundException() {
        var produitDtoIn = new ProduitDtoIn();
        produitDtoIn.setId(UUID.randomUUID());
        assertThrows(EntityNotFoundException.class, () -> this.service.updateProduit(produitDtoIn.getId(), produitDtoIn), "l'exception EntityNotFoundException n'est pas levée");
    }

    @Test
    void givenValidProduitIdDtoIn_whenDisableProduit_thenReturnProduitDtoOut() {
        var produitIdDtoInDtoIn = UUID.fromString("6a4a4185-cdb4-418e-9249-a160e384d877");
        var  produitDtoOut = this.service.disableProduit(produitIdDtoInDtoIn);
        assertFalse(produitDtoOut.getEtat(), "l'état du produit ne doit pas être à true");
    }

    @Test
    void givenInvalidProduitIdDtoIn_whenDeleteProduit_thenThrowEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> this.service.deleteProduit(UUID.randomUUID()), "l'exception EntityNotFoundException n'est pas levée");
    }

    @Test
    void givenValidProduitIdDtoIn_whenDeleteProduit_thenReturnVoid() {
        var produitIdDtoInDtoIn = UUID.fromString("6a4a4185-cdb4-418e-9249-a160e384d877");
        assertDoesNotThrow(() -> this.service.deleteProduit(produitIdDtoInDtoIn), "une exception ne doit pas être levée");
    }

    @Test
    void givenInvalidProduitIdDtoIn_whenDisableProduit_thenThrowEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> this.service.disableProduit(UUID.randomUUID()), "l'exception EntityNotFoundException n'est pas levée");
    }
}