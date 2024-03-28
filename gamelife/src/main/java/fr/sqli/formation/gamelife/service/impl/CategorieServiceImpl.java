package fr.sqli.formation.gamelife.service.impl;

import fr.sqli.formation.gamelife.dto.handler.ICategorieDtoHandler;
import fr.sqli.formation.gamelife.dto.in.CategorieDtoIn;
import fr.sqli.formation.gamelife.dto.out.CategorieDtoOut;
import fr.sqli.formation.gamelife.repository.ICategorieDao;
import fr.sqli.formation.gamelife.service.ICategorieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class CategorieServiceImpl implements ICategorieService {
    private static final Logger LOG = LoggerFactory.getLogger(ProduitServiceImpl.class);

    private ICategorieDao categorieDao;

    @Autowired
    public CategorieServiceImpl(ICategorieDao pCategorieDao) {
        this.categorieDao = pCategorieDao;
    }

    @Override
    public CategorieDtoOut getCategorie(UUID pCategorieIdDtoIn) {
        return ICategorieDtoHandler.dtoOutFromEntity(this.categorieDao.findById(pCategorieIdDtoIn)
                .orElseThrow(() -> new EntityNotFoundException("getCategorie: l'identifiant " + pCategorieIdDtoIn + " est incorrecte")));
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
            throw new EntityExistsException("addCategorie: la catégorie " + libelle + " existe");
        }

        var categorieEntity = ICategorieDtoHandler.toEntity(pCategorieDtoIn);
        categorieEntity = this.categorieDao.save(categorieEntity);

        return ICategorieDtoHandler.dtoOutFromEntity(categorieEntity);
    }

    @Override
    public CategorieDtoOut updateCategorie(UUID pCategorieIdDtoIn, CategorieDtoIn pCategorieDtoIn) {
        var categorieEntity = this.categorieDao.findById(pCategorieIdDtoIn)
                .orElseThrow(() -> new EntityNotFoundException("updateCategorie: l'identifiant " + pCategorieIdDtoIn + " est incorrecte"));

        categorieEntity = ICategorieDtoHandler.entityFromDtoIn(pCategorieDtoIn, categorieEntity);

        return ICategorieDtoHandler.dtoOutFromEntity(this.categorieDao.save(categorieEntity));
    }

    @Override
    public void deleteCategorie(UUID pCategorieIdDtoIn) {
        this.categorieDao.findById(pCategorieIdDtoIn)
                .orElseThrow(() -> new EntityNotFoundException("deleteCategorie: l'identifiant " + pCategorieIdDtoIn + " est incorrecte"));

        this.categorieDao.deleteById(pCategorieIdDtoIn);
    }
}
