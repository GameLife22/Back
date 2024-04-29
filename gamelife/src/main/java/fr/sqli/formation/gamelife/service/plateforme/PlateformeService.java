package fr.sqli.formation.gamelife.service.plateforme;

import fr.sqli.formation.gamelife.entite.PlateformeEntite;
import fr.sqli.formation.gamelife.convertisseur.IPlateformeConvertisseur;
import fr.sqli.formation.gamelife.dto.platforme.PlateformeRequete;
import fr.sqli.formation.gamelife.dto.platforme.PlateformeReponse;
import fr.sqli.formation.gamelife.dao.IPlateformeDao;
import fr.sqli.formation.gamelife.service.produit.ProduitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Set;
import java.util.UUID;

@Service
public class PlateformeService implements IPlateformeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProduitService.class);

    private IPlateformeDao plateformeDao;

    @Autowired
    public PlateformeService(IPlateformeDao pPlateformeDao) {
        this.plateformeDao = pPlateformeDao;
    }

    @Override
    public PlateformeReponse getPlateforme(UUID pPlateformeDtoInId) {
        return IPlateformeConvertisseur.dtoOutFromEntity(this.plateformeDao.findById(pPlateformeDtoInId)
                .orElseThrow(() -> new EntityNotFoundException("getPlateforme: l'identifiant " + pPlateformeDtoInId + " est incorrecte")));
    }

    @Override
    public Set<PlateformeReponse> getPlateformes() {
        return IPlateformeConvertisseur.dtoOutFromEntities((Set<PlateformeEntite>) this.plateformeDao.findAll());
    }

    @Override
    public PlateformeReponse creerPlateforme(PlateformeRequete pPlateformeRequete) {
        var libelle = pPlateformeRequete.getLibelle();
        var optionalPlateformeEntity = this.plateformeDao.findByLibelle(libelle);

        if (optionalPlateformeEntity.isPresent()) {
            throw new EntityExistsException("creerPlateforme: la plateforme " + libelle + " existe");
        }

        var plateformeEntity = IPlateformeConvertisseur.toEntity(pPlateformeRequete);
        plateformeEntity = this.plateformeDao.save(plateformeEntity);

        return IPlateformeConvertisseur.dtoOutFromEntity(plateformeEntity);
    }

    @Override
    public PlateformeReponse modifierPlateforme(UUID pPlateformeDtoInId, PlateformeRequete pPlateformeRequete) {
        var plateformeEntity = this.plateformeDao.findById(pPlateformeRequete.getId())
                .orElseThrow(() -> new EntityNotFoundException("modifierPlateforme: l'identifiant " + pPlateformeDtoInId + " est incorrecte"));

        plateformeEntity = IPlateformeConvertisseur.entityFromDtoIn(pPlateformeRequete, plateformeEntity);

        return IPlateformeConvertisseur.dtoOutFromEntity(this.plateformeDao.save(plateformeEntity));
    }

    @Override
    public void deletePlateforme(UUID pPlateformeDtoInId) {
        this.plateformeDao.findById(pPlateformeDtoInId)
                .orElseThrow(() -> new EntityNotFoundException("deletePlateforme: l'identifiant " + pPlateformeDtoInId + " est incorrecte"));

        this.plateformeDao.deleteById(pPlateformeDtoInId);
    }
}
