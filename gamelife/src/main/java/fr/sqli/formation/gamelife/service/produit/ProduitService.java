package fr.sqli.formation.gamelife.service.produit;

import fr.sqli.formation.gamelife.dto.categorie.CategorieRequete;
import fr.sqli.formation.gamelife.dto.platforme.PlateformeRequete;
import fr.sqli.formation.gamelife.entite.CategorieEntite;
import fr.sqli.formation.gamelife.entite.PlateformeEntite;
import fr.sqli.formation.gamelife.convertisseur.IImageConvertisseur;
import fr.sqli.formation.gamelife.convertisseur.IProduitConvertisseur;
import fr.sqli.formation.gamelife.dto.image.ImageRequete;
import fr.sqli.formation.gamelife.dto.produit.ProduitRequete;
import fr.sqli.formation.gamelife.dto.produit.ProduitReponse;
import fr.sqli.formation.gamelife.entite.ImageEntite;
import fr.sqli.formation.gamelife.dao.ICategorieDao;
import fr.sqli.formation.gamelife.dao.IImageDao;
import fr.sqli.formation.gamelife.dao.IPlateformeDao;
import fr.sqli.formation.gamelife.dao.IProduitDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProduitService implements IProduitService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProduitService.class);

    private IProduitDao produitDao;

    private ICategorieDao categorieDao;

    private IPlateformeDao plateformeDao;

    private IImageDao imageDao;

    @Autowired
    public ProduitService(IProduitDao pProduitDao, ICategorieDao pCategorieDao, IPlateformeDao pPlateformeDao, IImageDao pIImageDao) {
        this.produitDao = pProduitDao;
        this.categorieDao = pCategorieDao;
        this.plateformeDao = pPlateformeDao;
        this.imageDao = pIImageDao;
    }

    @Override
    public ProduitReponse recupererProduit(UUID pProduitDtoInId) {
        return IProduitConvertisseur.dtoOutFromEntity(this.produitDao.findById(pProduitDtoInId)
                .orElseThrow(() -> new EntityNotFoundException("recupererProduit: l'identifiant du produit " + pProduitDtoInId + " est incorrecte")));
    }

    @Override
    public List<ProduitReponse> recupererProduits() {
        return IProduitConvertisseur.dtoOutFromEntities(this.produitDao.findAll());
    }

    @Override
    public ProduitReponse creerProduit(ProduitRequete pProduitRequete) {
        //todo: check if product exist (find by nom)
        this.LOGGER.info("addproduit");

        var categoriesEntity = new HashSet<CategorieEntite>();
        for(CategorieRequete pCategorieRequete : pProduitRequete.recupererCategories()) {
            var optionalCategorieEntity = this.categorieDao.findByLibelle(pCategorieRequete.libelle());
            if(optionalCategorieEntity.isPresent()) {
                categoriesEntity.add(optionalCategorieEntity.get());
            }
        }

        this.LOGGER.info("categories:" + categoriesEntity);

        var plateformesEntity = new HashSet<PlateformeEntite>();
        for(PlateformeRequete plateformeRequete : pProduitRequete.getPlateformes()) {
            var optionalPlateformeEntity = this.plateformeDao.findByLibelle(plateformeRequete.getLibelle());
            if(optionalPlateformeEntity.isPresent()) {
                plateformesEntity.add(optionalPlateformeEntity.get());
            }
        }

        this.LOGGER.info("plateformes:" + plateformesEntity);

        var produitEntity = IProduitConvertisseur.toEntity(pProduitRequete);
        var imagesEntity = IImageConvertisseur.toEntities(pProduitRequete.getImages(), produitEntity);
        produitEntity.setImages(imagesEntity);
        produitEntity.setCategories(categoriesEntity);
        produitEntity.setPlateformes(plateformesEntity);

        return IProduitConvertisseur.dtoOutFromEntity(this.produitDao.save(produitEntity));
    }

    @Override
    public ProduitReponse modifierProduit(UUID pProduitDtoInId, ProduitRequete pProduitRequete) {
        var produitEntity = this.produitDao.findById(pProduitDtoInId)
                .orElseThrow(() -> new EntityNotFoundException("modifierProduit: l'identifiant du produit " + pProduitDtoInId + " est incorrecte"));

        var categoriesEntity = pProduitRequete.recupererCategories().stream()
                .map(categorie -> this.categorieDao.findByLibelle(categorie.libelle())
                        .orElseThrow(() -> new EntityNotFoundException("creerProduit: la catégorie " + categorie.libelle() + " est incorrecte")))
                .collect(Collectors.toSet());

        var plateformesEntity = pProduitRequete.getPlateformes().stream()
                .map(plateforme -> this.plateformeDao.findByLibelle(plateforme.getLibelle())
                        .orElseThrow(() -> new EntityNotFoundException("creerProduit: la plateforme " + plateforme.getLibelle() + " est incorrecte")))
                .collect(Collectors.toSet());

        var imagesEntity = new ArrayList<ImageEntite>();

        for (ImageRequete imageRequete : pProduitRequete.getImages()) {
            var imageEntity = new ImageEntite();

            try {
                imageEntity = this.imageDao.findById(imageRequete.getId()).get();
                imageEntity.setImage(imageRequete.getImage());
                imageEntity.setTitre(imageRequete.getTitre());
            } catch (InvalidDataAccessApiUsageException | IllegalArgumentException pException) {
                imageEntity.setImage(imageRequete.getImage());
                imageEntity.setTitre(imageRequete.getTitre());
            }

            imagesEntity.add(imageEntity);
        }

        var newProduitEntity = IProduitConvertisseur.entityFromDtoIn(pProduitRequete, produitEntity);
        newProduitEntity.setImages(imagesEntity);
        newProduitEntity.setCategories(categoriesEntity);
        newProduitEntity.setPlateformes(plateformesEntity);

        return IProduitConvertisseur.dtoOutFromEntity(this.produitDao.save(newProduitEntity));
    }

    @Override
    public void supprimerProduit(UUID pProduitDtoInId) {
        this.produitDao.findById(pProduitDtoInId)
                .orElseThrow(() -> new EntityNotFoundException("supprimerProduit: l'identifiant " + pProduitDtoInId + " est incorrecte"));

        this.produitDao.deleteById(pProduitDtoInId);
    }
}
