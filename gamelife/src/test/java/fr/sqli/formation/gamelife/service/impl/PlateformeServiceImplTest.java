package fr.sqli.formation.gamelife.service.impl;

import fr.sqli.formation.gamelife.dto.in.PlateformeDtoIn;
import org.junit.jupiter.api.Assertions;
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
class PlateformeServiceImplTest {

    private PlateformeServiceImpl service;

    @Autowired
    public PlateformeServiceImplTest(PlateformeServiceImpl pService) {
        this.service = pService;
    }

    @Test
    void givenValidPlateformeIdDtoIn_whenGetPlateforme_thenReturnPlateformeDtoOut() {
        final var plateformeDtoOut = this.service.getPlateforme(UUID.fromString("4a523eed-9372-4979-89a8-2eec8d41f696"));
        assertEquals(UUID.fromString("4a523eed-9372-4979-89a8-2eec8d41f696"), plateformeDtoOut.getId(), "les identifiants ne sont pas identiques");
        assertEquals("test", plateformeDtoOut.getLibelle(), "les libellés ne sont pas identiques");
    }

    @Test
    void givenInvalidPlateformeIdDtoIn_whenGetPlateforme_thenThrowEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> this.service.getPlateforme(UUID.randomUUID()), "l'exception EntityNotFoundException n'est pas levée");
    }

    @Test
    void givenNothing_whenGetPlateformes_thenReturnListOfPlateformeDtoOut() {
        var plateformesDtoOut = this.service.getPlateformes();
        Assertions.assertEquals(2, plateformesDtoOut.size(), "la liste des plateformes n'est pas égale à 2");
    }

    @Test
    void givenValidPlateformeDtoIn_whenAddPlateforme_thenReturnPlateformeDtoOut() {
        var plateformeDtoIn = new PlateformeDtoIn();
        plateformeDtoIn.setLibelle("new test");
        var plateformeDtoOut = this.service.addPlateforme(plateformeDtoIn);
        assertEquals(plateformeDtoIn.getLibelle(), plateformeDtoOut.getLibelle(), "les libellés ne sont pas identiques");
    }

    @Test
    void givenValidPlateformeDtoIn_whenAddPlateformeExists_thenThrowEntityExistsException() {
        var plateformeDtoIn = new PlateformeDtoIn();
        plateformeDtoIn.setLibelle("test");
        assertThrows(EntityExistsException.class, () -> this.service.addPlateforme(plateformeDtoIn), "la catégorie existe déjà en base de données");
    }

    @Test
    void givenValidPlateformeDtoIn_whenUpdatePlateforme_thenReturnPlateformeDtoOut() {
        final var plateformeIdDtoInDtoIn = UUID.fromString("4a523eed-9372-4979-89a8-2eec8d41f696");
        var plateformeDtoIn = new PlateformeDtoIn();
        plateformeDtoIn.setId(plateformeIdDtoInDtoIn);
        plateformeDtoIn.setLibelle("new test");

        var plateformeDtoOut = this.service.updatePlateforme(plateformeDtoIn.getId(), plateformeDtoIn);

        assertEquals(plateformeDtoIn.getId(), plateformeDtoOut.getId(), "les identifiants ne sont pas identiques");
        assertEquals(plateformeDtoIn.getLibelle(), plateformeDtoOut.getLibelle(), "les libellés ne sont pas identiques");
    }

    @Test
    void givenInvalidPlateformeDtoIn_whenUpdatePlateforme_thenThrowEntityNotFoundException() {
        var plateformeDtoIn = new PlateformeDtoIn();
        var plateformeIdDtoIn = UUID.randomUUID();
        plateformeDtoIn.setId(plateformeIdDtoIn);
        plateformeDtoIn.setLibelle("test");

        assertThrows(EntityNotFoundException.class, () -> this.service.updatePlateforme(plateformeIdDtoIn, plateformeDtoIn), "l'exception EntityNotFoundException n'est pas levée");
    }

    @Test
    void givenValidPlateformeIdDtoIn_whenDeletePlateforme_thenReturnVoid() {
        final var plateformeIdDtoInDtoIn = UUID.fromString("4a523eed-9372-4979-89a8-2eec8d41f696");
        assertDoesNotThrow(() -> this.service.deletePlateforme(plateformeIdDtoInDtoIn), "une exception ne doit pas être levée");
    }

    @Test
    void givenValidPlateformeIdDtoIn_whenDeletePlateforme_thenThrowEntityNotFoundException() {
        final var plateformeIdDtoInDtoIn = UUID.randomUUID();
        assertThrows(EntityNotFoundException.class, () -> this.service.deletePlateforme(plateformeIdDtoInDtoIn), "l'exception EntityNotFoundException n'est pas levée");
    }
}