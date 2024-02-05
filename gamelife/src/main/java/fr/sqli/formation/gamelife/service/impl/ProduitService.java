package fr.sqli.formation.gamelife.service.impl;

import fr.sqli.formation.gamelife.controler.ProduitRestController;
import fr.sqli.formation.gamelife.dto.handler.ProduitDtoHandler;
import fr.sqli.formation.gamelife.dto.in.ProduitDtoIn;
import fr.sqli.formation.gamelife.dto.out.ProduitDtoOut;
import fr.sqli.formation.gamelife.entity.ProduitEntity;
import fr.sqli.formation.gamelife.ex.EntityExistException;
import fr.sqli.formation.gamelife.ex.EntityNotFoundException;
import fr.sqli.formation.gamelife.ex.ParameterException;
import fr.sqli.formation.gamelife.repository.IProduitDao;
import fr.sqli.formation.gamelife.service.IProduitService;
import fr.sqli.formation.gamelife.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service
public class ProduitService extends AbstractService<ProduitEntity> implements IProduitService {
    private static final Logger LOG = LoggerFactory.getLogger(ProduitService.class);

    private IProduitDao produitDao;

    @Autowired
    public ProduitService(IProduitDao pProduitDao) {
        produitDao = pProduitDao;
    }

    @Override
    public ProduitDtoOut getProduit(Integer pIdProduit) throws ParameterException, EntityNotFoundException {
        return ProduitDtoHandler.dtoOutFromEntity(super.findEntity(pIdProduit));
    }

    @Override
    public List<ProduitDtoOut> getProduits() {
        return ProduitDtoHandler.dtosOutFromEntity(super.findAllEntities());
    }

    @Override
    public ProduitDtoOut addProduit(ProduitDtoIn pProduitDtoIn) throws ParameterException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, EntityExistException {
        ValidationUtils.validateDto(pProduitDtoIn, "addProduit");

        var nomCategorie = pProduitDtoIn.getCategorie();
        var nomPlateforme = pProduitDtoIn.getPlateforme();
        var optionalProduitEntity = this.produitDao.findByNomAndCategorieAndPlateforme(pProduitDtoIn.getNom(), nomCategorie, nomPlateforme);

        if (optionalProduitEntity.isEmpty()) {
            var produitEntity = ProduitDtoHandler.toEntity(pProduitDtoIn);
            var produitDtoOut = ProduitDtoHandler.dtoOutFromEntity(this.produitDao.save(produitEntity));
            return produitDtoOut;
        }

        throw new EntityExistException("addProduit - L'Entité existe");
    }

    @Override
    public ProduitDtoOut updateProduit(ProduitDtoIn pProduitDtoIn) throws ParameterException, EntityNotFoundException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, EntityExistException {
        ValidationUtils.validateDto(pProduitDtoIn, "updateProduit");


        var nomCategorie = pProduitDtoIn.getCategorie();
        var nomPlateforme = pProduitDtoIn.getPlateforme();
        var optionalProduitEntity = this.produitDao.findByNomAndCategorieAndPlateforme(pProduitDtoIn.getNom(), nomCategorie, nomPlateforme);

        if (optionalProduitEntity.isEmpty()) {
            ProduitEntity produitEntity = super.findEntity(pProduitDtoIn.getId());
            ValidationUtils.updateEntityIfDtoDiffers(produitEntity, pProduitDtoIn);
            var produitDtoOut = ProduitDtoHandler.dtoOutFromEntity(this.produitDao.save(produitEntity));
            return produitDtoOut;
        }

        throw new EntityExistException("addProduit - L'Entité existe");
    }

    @Override
    public Boolean deleteProduit(Integer pIdProduit) throws ParameterException, EntityNotFoundException {
        this.getProduit(pIdProduit);
        this.produitDao.deleteById(pIdProduit);
        return true;
    }

    @Override
    protected JpaRepository<ProduitEntity, Integer> getTargetedDao() {
        return this.produitDao;
    }
}
