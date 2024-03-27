package fr.sqli.formation.gamelife.service.impl;

import fr.sqli.formation.gamelife.dto.handler.IImageDtoHandler;
import fr.sqli.formation.gamelife.dto.handler.IProduitDtoHandler;
import fr.sqli.formation.gamelife.dto.in.ImageDtoIn;
import fr.sqli.formation.gamelife.dto.in.ProduitDtoIn;
import fr.sqli.formation.gamelife.dto.out.ProduitDtoOut;
import fr.sqli.formation.gamelife.entity.ImageEntity;
import fr.sqli.formation.gamelife.repository.ICategorieDao;
import fr.sqli.formation.gamelife.repository.IImageDao;
import fr.sqli.formation.gamelife.repository.IPlateformeDao;
import fr.sqli.formation.gamelife.repository.IProduitDao;
import fr.sqli.formation.gamelife.service.IProduitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//todo: add log + javadoc
@Service
public class ProduitServiceImpl implements IProduitService {
    private static final Logger LOG = LoggerFactory.getLogger(ProduitServiceImpl.class);

    private IProduitDao produitDao;

    private ICategorieDao categorieDao;

    private IPlateformeDao plateformeDao;

    private IImageDao imageDao;


    @Autowired
    public ProduitServiceImpl(IProduitDao pProduitDao, ICategorieDao pCategorieDao, IPlateformeDao pPlateformeDao, IImageDao pIImageDao) {
        this.produitDao = pProduitDao;
        this.categorieDao = pCategorieDao;
        this.plateformeDao = pPlateformeDao;
        this.imageDao = pIImageDao;
    }

    @Override
    public ProduitDtoOut getProduit(UUID pProduitDtoInId) {
        return IProduitDtoHandler.dtoOutFromEntity(this.produitDao.findById(pProduitDtoInId)
                .orElseThrow(() -> new EntityNotFoundException("getProduit: l'identifiant du produit " + pProduitDtoInId + " est incorrecte")));
    }

    @Override
    public List<ProduitDtoOut> getProduits() {
        return IProduitDtoHandler.dtoOutFromEntities(this.produitDao.findAll());
    }

    //todo: create a procedure to delete duplications
    @Override
    public ProduitDtoOut addProduit(ProduitDtoIn pProduitDtoIn) {
        var categorieDtoInLibelle = pProduitDtoIn.getCategorie().getLibelle();
        var plateformeDtoInLibelle = pProduitDtoIn.getPlateforme().getLibelle();

        var categorieEntity = this.categorieDao.findByLibelle(categorieDtoInLibelle)
                .orElseThrow(() -> new EntityNotFoundException("addProduit: la catégorie " + categorieDtoInLibelle + " est incorrecte"));

        var plateformeEntity = this.plateformeDao.findByLibelle(plateformeDtoInLibelle)
                .orElseThrow(() -> new EntityNotFoundException("addProduit: la plateforme " + plateformeDtoInLibelle + " est incorrecte"));

        var produitEntity = IProduitDtoHandler.toEntity(pProduitDtoIn);
        var imagesEntity = IImageDtoHandler.toEntities(pProduitDtoIn.getImages());
        produitEntity.setImages(imagesEntity);
        produitEntity.setCategorie(categorieEntity);
        produitEntity.setPlateforme(plateformeEntity);

        return IProduitDtoHandler.dtoOutFromEntity(this.produitDao.save(produitEntity));
    }

    @Override
    public ProduitDtoOut updateProduit(UUID pProduitDtoInId, ProduitDtoIn pProduitDtoIn) {
        var produitEntity = this.produitDao.findById(pProduitDtoInId)
                .orElseThrow(() -> new EntityNotFoundException("updateProduit: l'identifiant du produit " + pProduitDtoInId + " est incorrecte"));

        var categorieDtoInLibelle = pProduitDtoIn.getCategorie().getLibelle();
        var plateformeDtoInLibelle = pProduitDtoIn.getPlateforme().getLibelle();

        var categorieEntity = this.categorieDao.findByLibelle(categorieDtoInLibelle)
                .orElseThrow(() -> new EntityNotFoundException("updateProduit: la catégorie " + categorieDtoInLibelle + " est incorrecte"));

        var plateformeEntity = this.plateformeDao.findByLibelle(plateformeDtoInLibelle)
                .orElseThrow(() -> new EntityNotFoundException("updateProduit: la plateforme " + plateformeDtoInLibelle + " est incorrecte"));

        var imagesEntity = new ArrayList<ImageEntity>();

        for (ImageDtoIn imageDtoIn: pProduitDtoIn.getImages()) {
            try {
                var optionalImageEntity = this.imageDao.findById(imageDtoIn.getId());
                optionalImageEntity.get().setImage(imageDtoIn.getImage());
                optionalImageEntity.get().setTitre(imageDtoIn.getTitre());
                imagesEntity.add(optionalImageEntity.get());
            } catch (InvalidDataAccessApiUsageException | IllegalArgumentException pException) {
                var imageEntity = new ImageEntity();
                imageEntity.setImage(imageDtoIn.getImage());
                imageEntity.setTitre(imageDtoIn.getTitre());
                imagesEntity.add(imageEntity);
            }
        }

        var newProduitEntity = IProduitDtoHandler.entityFromDtoIn(pProduitDtoIn, produitEntity);
        newProduitEntity.setImages(imagesEntity);
        newProduitEntity.setCategorie(categorieEntity);
        newProduitEntity.setPlateforme(plateformeEntity);

        return IProduitDtoHandler.dtoOutFromEntity(this.produitDao.save(newProduitEntity));
    }

    @Override
    public ProduitDtoOut disableProduit(UUID pProduitDtoInId) {
        var produitEntity = this.produitDao.findById(pProduitDtoInId)
                .orElseThrow(() -> new EntityNotFoundException("disableProduit: l'identifiant du produit " + pProduitDtoInId + " est incorrecte"));

        produitEntity.setEtat(false);

        return IProduitDtoHandler.dtoOutFromEntity(this.produitDao.save(produitEntity));
    }

    @Override
    public void deleteProduit(UUID pProduitDtoInId) {
        this.produitDao.findById(pProduitDtoInId)
                .orElseThrow(() -> new EntityNotFoundException("deleteProduit: l'identifiant " + pProduitDtoInId + " est incorrecte"));

        this.produitDao.deleteById(pProduitDtoInId);
    }
}
