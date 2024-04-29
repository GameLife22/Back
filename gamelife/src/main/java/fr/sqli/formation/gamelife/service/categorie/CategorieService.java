package fr.sqli.formation.gamelife.service.categorie;

import fr.sqli.formation.gamelife.dto.categorie.CategorieRequete;
import fr.sqli.formation.gamelife.dto.categorie.CategorieReponse;
import fr.sqli.formation.gamelife.entite.CategorieEntite;
import fr.sqli.formation.gamelife.convertisseur.ICategorieConvertisseur;
import fr.sqli.formation.gamelife.dao.ICategorieDao;
import fr.sqli.formation.gamelife.service.produit.ProduitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Set;
import java.util.UUID;

/**
 * Classe d'implémentation de l'interface ICategorieService.
 * Cette classe contient un logger statique pour le suivi des logs.
 */
@Service
public class CategorieService implements ICategorieService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProduitService.class);

    private final ICategorieDao categorieDao;

    /**
     * Constructeur pour CategorieServiceImpl avec injection de dépendance.
     *
     * @param pCategorieDao Le DAO (Data Access Object) pour les catégories, utilisé pour accéder aux données des catégories.
     */
    @Autowired
    public CategorieService(ICategorieDao pCategorieDao) {
        this.categorieDao = pCategorieDao;
    }

    /**
     * Récupère une catégorie à partir de son identifiant.
     *
     * @param pCategorieId L'identifiant de la catégorie à récupérer.
     * @return La catégorie correspondante sous forme de CategorieReponse.
     * @throws EntityNotFoundException Si aucun enregistrement correspondant à l'identifiant n'est trouvé.
     */
    @Override
    public CategorieReponse recupererCategorie(UUID pCategorieId) {
        LOGGER.info("Tentative de récupération de la catégorie avec l'identifiant : {}", pCategorieId);
        CategorieReponse categorie = ICategorieConvertisseur.convertirEnCategorieReponse(this.categorieDao.findById(pCategorieId)
                .orElseThrow(() -> new EntityNotFoundException("Aucune catégorie trouvée avec l'identifiant fourni : " + pCategorieId)));
        LOGGER.info("Catégorie récupérée avec succès : {}", categorie);
        return categorie;
    }

    /**
     * Récupère la liste des catégories.
     *
     * @return Un ensemble de CategorieReponse représentant les catégories.
     */
    @Override
    public Set<CategorieReponse> recupererCategories() {
        LOGGER.info("Début de la récupération de toutes les catégories.");
        Set<CategorieEntite> categories = (Set<CategorieEntite>) this.categorieDao.findAll();
        Set<CategorieReponse> categoriesDtoOut = ICategorieConvertisseur.convertirEnCategoriesReponse(categories);
        LOGGER.info("Récupération terminée. Nombre de catégories trouvées : {}", categoriesDtoOut.size());
        return categoriesDtoOut;
    }

    /**
     * Crée une nouvelle catégorie à partir des informations fournies dans l'objet CategorieRequest.
     *
     * @param pCategorieRequete L'objet CategorieRequest contenant les informations de la catégorie à créer.
     * @return L'objet CategorieReponse représentant la catégorie créée.
     * @throws EntityExistsException Si la catégorie avec le même libellé existe déjà.
     */
    @Override
    public CategorieReponse creerCategorie(CategorieRequete pCategorieRequete) {
        String libelle = pCategorieRequete.libelle();
        LOGGER.info("Tentative de création d'une nouvelle catégorie avec le libellé : {}", libelle);

        var optionalCategorieEntity = this.categorieDao.findByLibelle(libelle);
        LOGGER.info(optionalCategorieEntity.toString());

        if (optionalCategorieEntity.isPresent()) {
            LOGGER.warn("Création échouée : une " +
                    "catégorie avec le libellé '{}' existe déjà.", libelle);
            throw new EntityExistsException(String.format("Une catégorie avec le libellé '%s' existe déjà.", libelle));
        }

        CategorieEntite categorieEntite = ICategorieConvertisseur.convertirEnCategorieEntite(pCategorieRequete);
        this.categorieDao.save(categorieEntite);
        LOGGER.info("Catégorie créée avec succès : {}", categorieEntite);

        return ICategorieConvertisseur.convertirEnCategorieReponse(categorieEntite);
    }

    /**
     * Met à jour une catégorie existante à partir des informations fournies dans l'objet CategorieRequest.
     *
     * @param pCategorieId L'identifiant de la catégorie à mettre à jour.
     * @param pCategorieRequete  L'objet CategorieRequest contenant les nouvelles informations de la catégorie.
     * @return L'objet CategorieReponse représentant la catégorie mise à jour.
     * @throws EntityNotFoundException Si aucun enregistrement correspondant à l'identifiant n'est trouvé.
     */
    @Override
    public CategorieReponse modifierCategorie(UUID pCategorieId, CategorieRequete pCategorieRequete) {
        LOGGER.info("Début de la mise à jour de la catégorie avec l'identifiant : {}", pCategorieId);
        this.categorieDao.findById(pCategorieId)
                .orElseThrow(() -> new EntityNotFoundException("Mise à jour impossible : aucune catégorie correspondante à l'identifiant " + pCategorieId));
        LOGGER.info("Catégorie trouvée, mise à jour en cours...");

        CategorieEntite categorieEntite = ICategorieConvertisseur.convertirEnCategorieEntite(pCategorieRequete);
        categorieEntite = this.categorieDao.save(categorieEntite);
        LOGGER.info("Catégorie mise à jour avec succès : {}", categorieEntite);

        return ICategorieConvertisseur.convertirEnCategorieReponse(categorieEntite);
    }
    /**
     * Supprime une catégorie existante à partir de son identifiant.
     *
     * @param pCategorieId L'identifiant de la catégorie à supprimer.
     * @throws EntityNotFoundException Si aucun enregistrement correspondant à l'identifiant n'est trouvé.
     */
    @Override
    public void supprimerCategorie(UUID pCategorieId) {
        LOGGER.info("Début de la suppression de la catégorie avec l'identifiant : {}", pCategorieId);
        this.categorieDao.findById(pCategorieId)
                .orElseThrow(() -> new EntityNotFoundException("Suppression impossible : aucun enregistrement trouvé pour l'identifiant " + pCategorieId));

        this.categorieDao.deleteById(pCategorieId);
        LOGGER.info("Catégorie supprimée avec succès pour l'identifiant : {}", pCategorieId);
    }
}
