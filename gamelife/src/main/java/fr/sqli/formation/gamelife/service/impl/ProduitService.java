package fr.sqli.formation.gamelife.service.impl;

import fr.sqli.formation.gamelife.dto.handler.ImageDtoHandler;
import fr.sqli.formation.gamelife.dto.handler.ProduitDtoHandler;
import fr.sqli.formation.gamelife.dto.in.ProduitDtoIn;
import fr.sqli.formation.gamelife.dto.out.ProduitDtoOut;
import fr.sqli.formation.gamelife.entity.ProduitEntity;
import fr.sqli.formation.gamelife.repository.IImageDao;
import fr.sqli.formation.gamelife.repository.IProduitDao;
import fr.sqli.formation.gamelife.service.IProduitService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

//todo: add log + javadoc
@Service
public class ProduitService implements IProduitService {
    private static final Logger LOG = LoggerFactory.getLogger(ProduitService.class);
    private IProduitDao produitDao;
    private IImageDao imageDao;
    private ModelMapper modelMapper;

    @Autowired
    public ProduitService(IProduitDao pProduitDao, IImageDao imageDao, ModelMapper pModelMapper) {
        this.produitDao = pProduitDao;
        this.imageDao = imageDao;
        this.modelMapper = pModelMapper;
    }

    @Override
    public ProduitDtoOut getProduit(int pIdProduit) {
        return ProduitDtoHandler.dtoOutFromEntity(this.produitDao.findById(pIdProduit)
                .orElseThrow(() -> new EntityNotFoundException("getProduit -> le produit n'existe pas, l'identifiant: " + pIdProduit + " est incorrecte")));
    }

    @Override
    public List<ProduitDtoOut> getProduits() {
        return ProduitDtoHandler.dtoOutFromEntities(this.produitDao.findAll());
    }

    @Override
    public ProduitDtoOut addProduit(ProduitDtoIn pProduitDtoIn) {
        var nomProduit = pProduitDtoIn.getNom();
        var libelleCategorie = pProduitDtoIn.getCategorie();
        var libellePlateforme = pProduitDtoIn.getPlateforme();
        var optionalProduitEntity = this.produitDao.findByNomAndCategorieAndPlateforme(nomProduit, libelleCategorie, libellePlateforme);

        if (!optionalProduitEntity.isEmpty()) {
            throw new EntityExistsException("addProduit -> le produit: " + nomProduit + " existe");
        }

        var produitEntity = ProduitDtoHandler.toEntity(pProduitDtoIn);
        var imagesEntity = ImageDtoHandler.toEntities(pProduitDtoIn.getImages());
        produitEntity.setImages(imagesEntity);

        var produitEntityFromDatabase = this.produitDao.saveAndFlush(produitEntity);

        this.imageDao.saveAllAndFlush(imagesEntity);

        return  ProduitDtoHandler.dtoOutFromEntity(produitEntityFromDatabase);
    }

    @Override
    public ProduitDtoOut updateProduit(ProduitDtoIn pProduitDtoIn) {
        var produitDtoOut = this.getProduit(pProduitDtoIn.getId());

        modelMapper.map(pProduitDtoIn, produitDtoOut);

        var produitEntity = ProduitDtoHandler.toEntity(pProduitDtoIn);
        var imagesEntity = ImageDtoHandler.toEntities(pProduitDtoIn.getImages());
        produitEntity.setImages(imagesEntity);

        var produitEntityFromDatabase = this.produitDao.saveAndFlush(produitEntity);

        this.imageDao.saveAllAndFlush(imagesEntity);

        return  ProduitDtoHandler.dtoOutFromEntity(produitEntityFromDatabase);
    }

    @Override
    public void deleteProduit(int pIdProduit) {
        this.getProduit(pIdProduit);
        this.produitDao.deleteById(pIdProduit);
    }
}
