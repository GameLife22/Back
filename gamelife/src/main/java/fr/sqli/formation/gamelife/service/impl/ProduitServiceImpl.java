package fr.sqli.formation.gamelife.service.impl;

import fr.sqli.formation.gamelife.dto.handler.ProduitDtoHandler;
import fr.sqli.formation.gamelife.dto.in.ProduitDtoIn;
import fr.sqli.formation.gamelife.dto.out.ProduitDtoOut;
import fr.sqli.formation.gamelife.entity.Categorie;
import fr.sqli.formation.gamelife.entity.Plateforme;
import fr.sqli.formation.gamelife.entity.ProduitEntity;
import fr.sqli.formation.gamelife.ex.EntityExistException;
import fr.sqli.formation.gamelife.ex.EntityNotFoundException;
import fr.sqli.formation.gamelife.ex.ParameterException;
import fr.sqli.formation.gamelife.repository.IProduitDao;
import fr.sqli.formation.gamelife.service.IProduitService;
import fr.sqli.formation.gamelife.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service
public class ProduitServiceImpl extends AbstractService<ProduitEntity> implements IProduitService {
    private IProduitDao produitDao;

    @Autowired
    public ProduitServiceImpl(IProduitDao pProduitDao) {
        produitDao = pProduitDao;
    }

    @Override
    public ProduitDtoOut getProduit(Integer pIdProduit) throws ParameterException, EntityNotFoundException {
        ValidationUtils.isPositiveInt(pIdProduit, "getProduit - l'id est inférieur à 1");

        return ProduitDtoHandler.dtoOutFromEntity(super.findEntity(pIdProduit));
    }

    @Override
    public List<ProduitDtoOut> getProduits() {
        return ProduitDtoHandler.dtosOutFromEntity(super.findAllEntities());
    }

    @Override
    public ProduitDtoOut addProduit(ProduitDtoIn pProduitDtoIn) throws ParameterException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, EntityExistException {
        ValidationUtils.isNotNull(pProduitDtoIn, "addProduit - le pProduitDtoIn est null");
        ValidationUtils.isPositiveInt(pProduitDtoIn.getId(), "addProduit - le pProduitDtoIn id est inférieur à 1");
        ValidationUtils.validateFields(pProduitDtoIn);
        ValidationUtils.validateFieldEnum(Categorie.values(), pProduitDtoIn.getCategorie(), "addProduit - La valeur " + pProduitDtoIn.getCategorie() + " n'est pas présente dans la liste des catégories");
        ValidationUtils.validateFieldEnum(Plateforme.values(), pProduitDtoIn.getPlateforme(), "addProduit - La valeur " + pProduitDtoIn.getPlateforme() + " n'est pas présente dans la liste des plateformes");

        var opProduitEntity = this.produitDao.findByNomAndCategorie(pProduitDtoIn.getNom(), pProduitDtoIn.getCategorie());

        if (!opProduitEntity.isPresent()) {
            var produitEntity = ProduitDtoHandler.toEntity(pProduitDtoIn);
            var produitDtoOut = ProduitDtoHandler.dtoOutFromEntity(this.produitDao.save(produitEntity));
            return produitDtoOut;
        }

        throw new EntityExistException("addProduit - L'Entité existe");
    }

    @Override
    public ProduitDtoOut updateProduit(ProduitDtoIn pProduitDtoIn) throws ParameterException, EntityNotFoundException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ValidationUtils.isNotNull(pProduitDtoIn, "updateProduit - le pProduitDtoIn est null");
        ValidationUtils.isPositiveInt(pProduitDtoIn.getId(), "updateProduit - le pProduitDtoIn id est inférieur à 1");
        ValidationUtils.validateFields(pProduitDtoIn); // TODO: KYSS
        ValidationUtils.validateFieldEnum(Categorie.values(), pProduitDtoIn.getCategorie(), "updateProduit - La valeur " + pProduitDtoIn.getCategorie() + " n'est pas présente dans la liste des catégories");
        ValidationUtils.validateFieldEnum(Plateforme.values(), pProduitDtoIn.getPlateforme(), "updateProduit - La valeur " + pProduitDtoIn.getPlateforme() + " n'est pas présente dans la liste des plateformes");

        ProduitEntity produitEntity = super.findEntity(pProduitDtoIn.getId());

        ValidationUtils.updateFieldsIfDifferentBetweenEntityAndDto(produitEntity, pProduitDtoIn); // TODO: KYSS

        return ProduitDtoHandler.dtoOutFromEntity(produitEntity);
    }

    @Override
    public Boolean deleteProduit(Integer pIdProduit) throws ParameterException, EntityNotFoundException {
        ValidationUtils.isPositiveInt(pIdProduit, "deleteProduit - l'id est inférieur à 1");

        this.getProduit(pIdProduit);

        this.produitDao.deleteById(pIdProduit);

        return true;
    }

    @Override
    protected JpaRepository<ProduitEntity, Integer> getTargetedDao() {
        return this.produitDao;
    }
}
