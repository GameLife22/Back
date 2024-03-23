package fr.sqli.formation.gamelife.service.impl;

import fr.sqli.formation.gamelife.dto.handler.IProduitDtoHandler;
import fr.sqli.formation.gamelife.dto.in.ProduitDtoIn;
import fr.sqli.formation.gamelife.dto.out.ProduitDtoOut;
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
import java.util.UUID;

//todo: add log + javadoc
@Service
public class ProduitServiceImpl implements IProduitService {
    private static final Logger LOG = LoggerFactory.getLogger(ProduitServiceImpl.class);

    private IProduitDao produitDao;

    private IImageDao imageDao;

    private ModelMapper modelMapper;

    @Autowired
    public ProduitServiceImpl(IProduitDao pProduitDao, IImageDao pIImageDao, ModelMapper pModelMapper) {
        this.produitDao = pProduitDao;
        this.imageDao = pIImageDao;
        this.modelMapper = pModelMapper;
    }

    @Override
    public ProduitDtoOut getProduit(UUID pIdProduit) {
        return IProduitDtoHandler.dtoOutFromEntity(this.produitDao.findById(pIdProduit)
                .orElseThrow(() -> new EntityNotFoundException("getProduit -> le produit n'existe pas, l'identifiant: " + pIdProduit + " est incorrecte")));
    }

    @Override
    public List<ProduitDtoOut> getProduits() {
        return IProduitDtoHandler.dtoOutFromEntities(this.produitDao.findAll());
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

        var produitEntity = IProduitDtoHandler.toEntity(pProduitDtoIn);
        produitEntity = this.produitDao.save(produitEntity);

        return  IProduitDtoHandler.dtoOutFromEntity(produitEntity);
    }

    @Override
    public ProduitDtoOut updateProduit(ProduitDtoIn pProduitDtoIn) {
        var produitDtoOut = this.getProduit(pProduitDtoIn.getId());
        this.modelMapper.map(pProduitDtoIn, produitDtoOut);
        var produitEntity = IProduitDtoHandler.toEntity(pProduitDtoIn);
        return  IProduitDtoHandler.dtoOutFromEntity(this.produitDao.save(produitEntity));
    }

    @Override
    public void deleteProduit(UUID pIdProduit) {
        this.getProduit(pIdProduit);
        this.produitDao.deleteById(pIdProduit);
    }
}
