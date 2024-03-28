package fr.sqli.formation.gamelife.service.impl;

import fr.sqli.formation.gamelife.dto.handler.IPlateformeDtoHandler;
import fr.sqli.formation.gamelife.dto.in.PlateformeDtoIn;
import fr.sqli.formation.gamelife.dto.out.PlateformeDtoOut;
import fr.sqli.formation.gamelife.repository.IPlateformeDao;
import fr.sqli.formation.gamelife.service.IPlateformeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class PlateformeServiceImpl implements IPlateformeService {
    private static final Logger LOG = LoggerFactory.getLogger(ProduitServiceImpl.class);

    private IPlateformeDao plateformeDao;

    @Autowired
    public PlateformeServiceImpl(IPlateformeDao pPlateformeDao) {
        this.plateformeDao = pPlateformeDao;
    }

    @Override
    public PlateformeDtoOut getPlateforme(UUID pPlateformeDtoInId) {
        return IPlateformeDtoHandler.dtoOutFromEntity(this.plateformeDao.findById(pPlateformeDtoInId)
                .orElseThrow(() -> new EntityNotFoundException("getPlateforme: l'identifiant " + pPlateformeDtoInId + " est incorrecte")));
    }

    @Override
    public List<PlateformeDtoOut> getPlateformes() {
        return IPlateformeDtoHandler.dtoOutFromEntities(this.plateformeDao.findAll());
    }

    @Override
    public PlateformeDtoOut addPlateforme(PlateformeDtoIn pPlateformeDtoIn) {
        var libelle = pPlateformeDtoIn.getLibelle();
        var optionalPlateformeEntity = this.plateformeDao.findByLibelle(libelle);

        if (optionalPlateformeEntity.isPresent()) {
            throw new EntityExistsException("addPlateforme: la plateforme " + libelle + " existe");
        }

        var plateformeEntity = IPlateformeDtoHandler.toEntity(pPlateformeDtoIn);
        plateformeEntity = this.plateformeDao.save(plateformeEntity);

        return IPlateformeDtoHandler.dtoOutFromEntity(plateformeEntity);
    }

    @Override
    public PlateformeDtoOut updatePlateforme(UUID pPlateformeDtoInId, PlateformeDtoIn pPlateformeDtoIn) {
        var plateformeEntity = this.plateformeDao.findById(pPlateformeDtoIn.getId())
                .orElseThrow(() -> new EntityNotFoundException("updatePlateforme: l'identifiant " + pPlateformeDtoInId + " est incorrecte"));

        plateformeEntity = IPlateformeDtoHandler.entityFromDtoIn(pPlateformeDtoIn, plateformeEntity);

        return IPlateformeDtoHandler.dtoOutFromEntity(this.plateformeDao.save(plateformeEntity));
    }

    @Override
    public void deletePlateforme(UUID pPlateformeDtoInId) {
        this.plateformeDao.findById(pPlateformeDtoInId)
                .orElseThrow(() -> new EntityNotFoundException("deletePlateforme: l'identifiant " + pPlateformeDtoInId + " est incorrecte"));

        this.plateformeDao.deleteById(pPlateformeDtoInId);
    }
}
