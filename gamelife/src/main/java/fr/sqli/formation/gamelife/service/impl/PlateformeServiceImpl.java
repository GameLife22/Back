package fr.sqli.formation.gamelife.service.impl;

import fr.sqli.formation.gamelife.dto.handler.IPlateformeDtoHandler;
import fr.sqli.formation.gamelife.dto.in.PlateformeDtoIn;
import fr.sqli.formation.gamelife.dto.out.PlateformeDtoOut;
import fr.sqli.formation.gamelife.repository.IPlateformeDao;
import fr.sqli.formation.gamelife.service.IPlateformeService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

//todo: add javadoc + log
@Service
public class PlateformeServiceImpl implements IPlateformeService {
    private static final Logger LOG = LoggerFactory.getLogger(ProduitServiceImpl.class);

    private IPlateformeDao plateformeDao;

    private ModelMapper modelMapper;

    @Autowired
    public PlateformeServiceImpl(IPlateformeDao pPlateformeDao, ModelMapper pModelMapper) {
        this.plateformeDao = pPlateformeDao;
        this.modelMapper = pModelMapper;
    }

    @Override
    public PlateformeDtoOut getPlateforme(UUID pIdPlateforme) {
        return IPlateformeDtoHandler.dtoOutFromEntity(this.plateformeDao.findById(pIdPlateforme)
                .orElseThrow(() -> new EntityNotFoundException("getPlateforme: l'identifiant " + pIdPlateforme + " est incorrecte")));
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
    public PlateformeDtoOut updatePlateforme(PlateformeDtoIn pPlateformeDtoIn) {
        var plateformeEntity = this.plateformeDao.findById(pPlateformeDtoIn.getId())
                .orElseThrow(() -> new EntityNotFoundException("updatePlateforme: la plateforme " + pPlateformeDtoIn.getLibelle() + " n'existe pas"));

        this.modelMapper.map(pPlateformeDtoIn, plateformeEntity);
        return IPlateformeDtoHandler.dtoOutFromEntity(this.plateformeDao.save(plateformeEntity));
    }

    @Override
    public void deletePlateforme(UUID pIdPlateforme) {
        this.plateformeDao.findById(pIdPlateforme)
                .orElseThrow(() -> new EntityNotFoundException("deletePlateforme: l'identifiant " + pIdPlateforme + " est incorrecte"));

        this.plateformeDao.deleteById(pIdPlateforme);
    }
}
