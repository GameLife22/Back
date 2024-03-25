package fr.sqli.formation.gamelife.service.impl;

import fr.sqli.formation.gamelife.dto.handler.ICategorieDtoHandler;
import fr.sqli.formation.gamelife.dto.in.CategorieDtoIn;
import fr.sqli.formation.gamelife.dto.in.CategorieDtoIn;
import fr.sqli.formation.gamelife.dto.out.CategorieDtoOut;
import fr.sqli.formation.gamelife.dto.out.CategorieDtoOut;
import fr.sqli.formation.gamelife.repository.ICategorieDao;
import fr.sqli.formation.gamelife.service.ICategorieService;
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
public class CategorieServiceImpl implements ICategorieService {
    private static final Logger LOG = LoggerFactory.getLogger(ProduitServiceImpl.class);

    private ICategorieDao categorieDao;

    private ModelMapper modelMapper;

    @Autowired
    public CategorieServiceImpl(ICategorieDao pCategorieDao, ModelMapper pModelMapper) {
        this.categorieDao = pCategorieDao;
        this.modelMapper = pModelMapper;
    }

    @Override
    public CategorieDtoOut getCategorie(UUID pIdCategorie) {
        return ICategorieDtoHandler.dtoOutFromEntity(this.categorieDao.findById(pIdCategorie)
                .orElseThrow(() -> new EntityNotFoundException("getCategorie: l'identifiant " + pIdCategorie + " est incorrecte")));
    }

    @Override
    public List<CategorieDtoOut> getCategories() {
        return ICategorieDtoHandler.dtoOutFromEntities(this.categorieDao.findAll());
    }

    @Override
    public CategorieDtoOut addCategorie(CategorieDtoIn pCategorieDtoIn) {
        var libelle = pCategorieDtoIn.getLibelle();
        var optionalCategorieEntity = this.categorieDao.findByLibelle(libelle);

        if (optionalCategorieEntity.isPresent()) {
            throw new EntityExistsException("addCategorie: la plateforme " + libelle + " existe");
        }

        var plateformeEntity = ICategorieDtoHandler.toEntity(pCategorieDtoIn);
        plateformeEntity = this.categorieDao.save(plateformeEntity);

        return ICategorieDtoHandler.dtoOutFromEntity(plateformeEntity);
    }

    @Override
    public CategorieDtoOut updateCategorie(CategorieDtoIn pCategorieDtoIn) {
        var plateformeEntity = this.categorieDao.findById(pCategorieDtoIn.getId())
                .orElseThrow(() -> new EntityNotFoundException("updateCategorie: la plateforme " + pCategorieDtoIn.getLibelle() + " n'existe pas"));

        this.modelMapper.map(pCategorieDtoIn, plateformeEntity);
        return ICategorieDtoHandler.dtoOutFromEntity(this.categorieDao.save(plateformeEntity));
    }

    @Override
    public void deleteCategorie(UUID pIdCategorie) {
        this.categorieDao.findById(pIdCategorie)
                .orElseThrow(() -> new EntityNotFoundException("deleteCategorie: l'identifiant " + pIdCategorie + " est incorrecte"));

        this.categorieDao.deleteById(pIdCategorie);
    }
}
