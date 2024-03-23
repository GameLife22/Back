package fr.sqli.formation.gamelife.service.impl;

import fr.sqli.formation.gamelife.dto.in.PlateformeDtoIn;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

//todo: display name
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PlateformeServiceImplTest {

    @Autowired
    private PlateformeServiceImpl service;

    @Test
    public void givenValidIdPlateforme_whenGetPlateforme_thenReturnPlateformeDtoOut() {
        var plateformeDtoIn = new PlateformeDtoIn();
        plateformeDtoIn.setLibelle("test");

        var plateformeDtoOut1 = this.service.addPlateforme(plateformeDtoIn);
        var id = plateformeDtoOut1.getId();
        var plateformeDtoOut2 = this.service.getPlateforme(id);

        assertEquals(id, plateformeDtoOut2.getId());
        assertEquals("test", plateformeDtoOut2.getLibelle());
    }

    @Test
    public void givenInvalidIdPlateforme_whenGetPlateforme_thenThrowEntityNotFoundException() {
        var id = UUID.randomUUID();
        assertThrows(EntityNotFoundException.class, () -> this.service.getPlateforme(id));
    }

    @Test
    public void givenValidPlateformeDtoIn_whenAddPlateforme_thenReturnPlateformeDtoOut() {
        var plateformeDtoIn = new PlateformeDtoIn();
        plateformeDtoIn.setLibelle("test");

        var plateformeDtoOut = this.service.addPlateforme(plateformeDtoIn);

        assertEquals("test", plateformeDtoOut.getLibelle());
    }

    @Test
    public void givenValidPlateformeDtoIn_whenAddPlateformeExists_thenThrowEntityExistsException() {
        var plateformeDtoIn = new PlateformeDtoIn();
        plateformeDtoIn.setLibelle("test");

        this.service.addPlateforme(plateformeDtoIn); //todo: check

        assertThrows(EntityExistsException.class, () -> this.service.addPlateforme(plateformeDtoIn));
    }

    @Test
    public void givenValidPlateformeDtoIn_whenUpdatePlateforme_thenReturnPlateformeDtoOut() {
        var plateformeDtoIn = new PlateformeDtoIn();
        plateformeDtoIn.setLibelle("before update");

        var plateformeDtoOut1 = this.service.addPlateforme(plateformeDtoIn);
        plateformeDtoIn.setId(plateformeDtoOut1.getId());
        plateformeDtoIn.setLibelle("after update");

        var plateformeDtoOut2 = this.service.updatePlateforme(plateformeDtoIn); //todo: check
        assertEquals("after update", plateformeDtoOut2.getLibelle());
    }

    @Test
    public void givenInvalidPlateformeDtoIn_whenUpdatePlateforme_thenThrowEntityNotFoundException() {
        var plateformeDtoIn = new PlateformeDtoIn();
        plateformeDtoIn.setId(UUID.randomUUID());
        plateformeDtoIn.setLibelle("test");

        assertThrows(EntityNotFoundException.class, () -> this.service.updatePlateforme(plateformeDtoIn));
    }

    @Test
    public void givenValidIdPlateforme_whenDeletePlateforme_thenReturnVoid() {

    }

    @Test
    public void givenInvalidIdPlateforme_whenDeletePlateforme_thenThrowEntityNotFoundException() {
        var id = UUID.randomUUID();
        assertThrows(EntityNotFoundException.class, () -> this.service.deletePlateforme(id));
    }
}