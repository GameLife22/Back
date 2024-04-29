package fr.sqli.formation.gamelife.controleur;

import fr.sqli.formation.gamelife.dto.categorie.CategorieRequete;
import fr.sqli.formation.gamelife.dto.categorie.CategorieReponse;
import fr.sqli.formation.gamelife.service.categorie.ICategorieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.UUID;

/**
 * Classe CategorieRestController.
 *
 * Cette classe est un contrôleur REST qui gère les opérations liées aux catégories.
 * Elle expose les endpoints suivants :
 * - GET /api/v1/categories/{categorieId} : Récupère une catégorie en fonction de son identifiant.
 * - GET /api/v1/categories : Récupère toutes les catégories.
 * - POST /api/v1/categories : Crée une nouvelle catégorie.
 * - PATCH /api/v1/categories/{categorieId} : Met à jour une catégorie existante.
 * - DELETE /api/v1/categories/{categorieId} : Supprime une catégorie existante.
 */
@RestController
@RequestMapping("/api/v1")
public class CategorieControleur {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategorieControleur.class);

    private final ICategorieService service;

    /**
     * Constructeur de la classe CategorieRestController.
     *
     * Ce constructeur permet d'injecter une instance de ICategorieService dans le contrôleur.
     *
     * @param pService le service de catégorie à injecter
     */
    @Autowired
    public CategorieControleur(ICategorieService pService) {
        service = pService;
    }

    /**
     * Récupère une catégorie en fonction de son identifiant.
     *
     * @param pCategorieId l'identifiant de la catégorie à récupérer
     * @return ResponseEntity<CategorieReponse> contenant la catégorie récupérée si elle existe, sinon HttpStatus.NOT_FOUND
     */
    @GetMapping("/categories/{categorieId}")
    public ResponseEntity<CategorieReponse> recupererCategorie(@PathVariable("categorieId") UUID pCategorieId) {
        LOGGER.info("Tentative de récupération de la catégorie avec l'identifiant: {}", pCategorieId);
        try {
            var result = this.service.recupererCategorie(pCategorieId);
            //todo: test
            if (result == null) {
                LOGGER.warn("Aucune catégorie trouvée avec l'identifiant: {}", pCategorieId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            LOGGER.info("Catégorie retrouvée avec succès avec le libellé: {}", result.pLibelle());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception pException) {
            LOGGER.error("Erreur lors de la récupération de la catégorie avec l'identifiant: {}", pCategorieId, pException);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Récupère toutes les catégories.
     *
     * Cette méthode permet de récupérer toutes les catégories existantes.
     *
     * @return ResponseEntity<Set<CategorieReponse>> contenant l'ensemble des catégories si elles existent, sinon HttpStatus.NO_CONTENT
     * @throws Exception si une erreur se produit lors de la récupération des catégories
     */
    @GetMapping("/categories")
    public ResponseEntity<Set<CategorieReponse>> recupererCategories() {
        LOGGER.info("Début de la récupération de toutes les catégories.");
        try {
            var result = this.service.recupererCategories();
            //todo: test
            if (result.isEmpty()) {
                LOGGER.warn("Aucune catégorie n'a été trouvée.");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            LOGGER.info("Récupération réussie de {} catégories.", result.size());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception pException) {
            LOGGER.error("Erreur lors de la récupération des catégories: {}", pException.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Crée une nouvelle catégorie.
     *
     * Cette méthode permet de créer une nouvelle catégorie en utilisant les informations fournies dans le corps de la requête.
     *
     * @param pCategorieRequete l'objet CategorieRequest contenant les informations de la catégorie à créer
     * @return ResponseEntity<CategorieReponse> contenant la catégorie créée si elle a été créée avec succès, sinon HttpStatus.INTERNAL_SERVER_ERROR
     */
    @PostMapping("/categories")
    public ResponseEntity<CategorieReponse> creerCategorie(@Valid @RequestBody CategorieRequete pCategorieRequete) {
        LOGGER.info("Tentative de création d'une nouvelle catégorie avec les informations: {}", pCategorieRequete);
        try {
            var result = this.service.creerCategorie(pCategorieRequete);
            LOGGER.info("Création réussie de la catégorie avec l'ID: {}", result.pCategorieId());
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch(Exception pException) {
            LOGGER.error("Erreur lors de la création de la catégorie: {}", pException.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Met à jour une catégorie existante.
     *
     * Cette méthode permet de mettre à jour une catégorie existante en utilisant les informations fournies dans le corps de la requête.
     *
     * @param pCategorieId l'identifiant de la catégorie à mettre à jour
     * @param pCategorieRequete l'objet CategorieRequest contenant les informations de la catégorie à mettre à jour
     * @return ResponseEntity<CategorieReponse> contenant la catégorie mise à jour si elle a été mise à jour avec succès, sinon HttpStatus.NOT_FOUND
     */
    @PatchMapping("/categories/{categorieId}")
    public ResponseEntity<CategorieReponse> modifierCategorie(@Valid @PathVariable("categorieId") UUID pCategorieId, @RequestBody CategorieRequete pCategorieRequete) {
        LOGGER.info("Tentative de mise à jour de la catégorie avec l'identifiant: {}", pCategorieId);
        try {
            var result = this.service.modifierCategorie(pCategorieId, pCategorieRequete);
            LOGGER.info("Mise à jour réussie de la catégorie avec l'identifiant: {}", pCategorieId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch(Exception pException) {
            LOGGER.error("Échec de la mise à jour de la catégorie avec l'identifiant: {}. Erreur: {}", pCategorieId, pException.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Supprime une catégorie existante.
     *
     * Cette méthode permet de supprimer une catégorie existante en utilisant l'identifiant de la catégorie fourni en paramètre.
     *
     * @param pCategorieId l'identifiant de la catégorie à supprimer
     * @return ResponseEntity<Void> ne contenant aucun contenu si la catégorie a été supprimée avec succès, sinon HttpStatus.NOT_FOUND
     */
    @DeleteMapping("/categories/{categorieId}")
    public ResponseEntity<Void> supprimerCategorie(@PathVariable("categorieId") UUID pCategorieId) {
        LOGGER.info("Tentative de suppression de la catégorie avec l'identifiant: {}", pCategorieId);
        try {
            this.service.supprimerCategorie(pCategorieId);
            LOGGER.info("Suppression réussie de la catégorie avec l'identifiant: {}", pCategorieId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(Exception pException) {
            LOGGER.error("Échec de la suppression de la catégorie avec l'identifiant: {}. Erreur: {}", pCategorieId, pException.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}