package fr.sqli.formation.gamelife.controleur;

import fr.sqli.formation.gamelife.dto.commande.CommandeRequete;
import fr.sqli.formation.gamelife.convertisseur.ICommandeConvertisseur;
import fr.sqli.formation.gamelife.dto.commande.ItemCommandeRequete;
import fr.sqli.formation.gamelife.dto.commande.CommandeReponse;
import fr.sqli.formation.gamelife.dto.ExceptionDtoOut;
import fr.sqli.formation.gamelife.dto.commande.ItemCommandeReponse;
import fr.sqli.formation.gamelife.exception.commande.EtatCommandeInvalideException;
import fr.sqli.formation.gamelife.exception.ParameterException;
import fr.sqli.formation.gamelife.exception.ProduitRevendeurException;
import fr.sqli.formation.gamelife.exception.utilisateur.UtilisateurNonExistantException;
import fr.sqli.formation.gamelife.exception.commande.ItemCommandeNotFoundException;
import fr.sqli.formation.gamelife.exception.commande.CommandeNotFoundException;
import fr.sqli.formation.gamelife.dao.ICommandeDao;
import fr.sqli.formation.gamelife.service.commande.ICommandeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
;import java.util.UUID;

@RestController
@RequestMapping("/commande")
public class CommandeControleur {

    @Autowired
    private ICommandeService ICommandeService;
    @Autowired
    private ICommandeDao ICommandeDAO;
    private ICommandeConvertisseur ICommandeConvertisseur;

    private static final Logger logger = LoggerFactory.getLogger(CategorieControleur.class);

    //recuperer tous les produits qu'un utilisateur a dans son panier
    @GetMapping("/{userId}/produits")
    public ResponseEntity<?> getAllProduitsPanier(@PathVariable UUID userId) {
        try {
            return ResponseEntity.ok(ICommandeService.getAllProduitsPanier(userId));
        } catch (CommandeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDtoOut(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionDtoOut("Une erreur s'est produite lors de la récupération des produits."));
        }
    }

    // Recuperer une seule commande
    @GetMapping("/{id}")
    public ResponseEntity<CommandeReponse> getCommande(@PathVariable UUID id) {
        try {
            CommandeReponse commandeDto = ICommandeService.getCommande(id);
            return new ResponseEntity<>(commandeDto, HttpStatus.OK);
        } catch (CommandeNotFoundException ex) {
            logger.error("Commande non trouvée avec l'ID : {}", id, ex);
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            logger.error("Une erreur inattendue est survenue lors de la récupération de la commande avec l'ID : {}", id, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Afficher le prix total du commande
    @GetMapping("/{id}/prix-total")
    public double getPrixTotalPanier(@PathVariable UUID id) throws CommandeNotFoundException {
        return ICommandeService.getPrixTotalCommande(id);
    }



    // Crée une commande pour un utilisateur
    @PostMapping("/creer")
    public ResponseEntity<CommandeRequete> creerCommande(@RequestBody CommandeRequete commandeDto) {
        try {
            CommandeRequete creerdCommande = ICommandeService.creerCommande(commandeDto);
            return new ResponseEntity<>(creerdCommande, HttpStatus.CREATED);
        } catch (UtilisateurNonExistantException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non existant", e);
        }
    }

    // Modifier une commande
    @PutMapping("/{id}")
    public ResponseEntity<CommandeRequete> modifierCommande(@PathVariable UUID id, @RequestBody CommandeRequete commandeDto) throws CommandeNotFoundException {
        CommandeRequete modifierdCommandeDto = ICommandeService.modifierCommande(id, commandeDto);
        return new ResponseEntity<>(modifierdCommandeDto, HttpStatus.OK);
    }

    // Supprimer une commande
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCommande(@PathVariable UUID id) {
        try {
            ICommandeService.deleteCommande(id);
            return ResponseEntity.ok().build();
        } catch (CommandeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDtoOut(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionDtoOut("Une erreur s'est produite lors de la suppression de la commande."));
        }
    }

    // Modifier la quantité d'un item dans une commande

    @PutMapping("/{id}/modif-quantite")
    public ResponseEntity<?> modifierQuantite(@PathVariable("id") UUID id,
                                              @RequestBody ItemCommandeRequete itemCommandeDto) {
        try {
            ItemCommandeReponse commandeDtoOut = ICommandeService.modifierQuantite(id, itemCommandeDto);
            return ResponseEntity.ok(commandeDtoOut);
        } catch (CommandeNotFoundException | ItemCommandeNotFoundException | IllegalAccessException e) {
            // Gérer les exceptions spécifiques
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDtoOut(e.getMessage()));
        } catch (ParameterException e) {
            // Capturer les exceptions de paramètre et renvoyer une réponse BadRequest
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionDtoOut(e.getMessage()));
        } catch (Exception e) {
            // Capturer les autres exceptions et renvoyer une réponse BadRequest
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionDtoOut("Une erreur s'est produite lors du traitement de la demande."));
        }
    }

    // Ajouter un produit dans une commande existante (id) sinon créer une nouvelle commande
    @PutMapping("/{id}/ajout-produit")
    public ResponseEntity<ItemCommandeReponse> ajoutProduit(@PathVariable("id") UUID idUtilisateur,
                                                            @RequestBody ItemCommandeRequete itemCommandeDto) {
        try {
            // Utilisation du logger pour enregistrer des informations de débogage
            logger.info("Requête d'ajout de produit pour l'utilisateur avec l'ID : {}", idUtilisateur);

            // Votre logique métier pour ajouter le produit à la commande
            ItemCommandeReponse itemCommandeReponse = ICommandeService.ajoutProduit(idUtilisateur, itemCommandeDto);

            // Utilisation du logger pour enregistrer des informations de débogage
            logger.info("Produit ajouté avec succès pour l'utilisateur avec l'ID : {}", idUtilisateur);

            // Retourner une réponse avec le statut 201 Created
            return new ResponseEntity<>(itemCommandeReponse, HttpStatus.CREATED);
        } catch (ProduitRevendeurException | ParameterException  |
                 CommandeNotFoundException | EtatCommandeInvalideException e) {
            // Utilisation du logger pour enregistrer des informations sur l'erreur
            logger.error("Une erreur s'est produite lors de l'ajout du produit pour l'utilisateur avec l'ID : {}", idUtilisateur, e);

            // Retourner une réponse avec un code d'erreur
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }







    // Valider une commande (changer l'état de la commande)
    @PutMapping("/{id}/valider-commande")
    public ResponseEntity<?> validerCommande(@PathVariable("id") UUID id) {
        try {
            CommandeReponse commandeReponse = ICommandeService.validerCommande(id);

            return ResponseEntity.ok(commandeReponse);
        } catch (CommandeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDtoOut(e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionDtoOut(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionDtoOut("Une erreur s'est produite lors de la validation de la commande."));
        }
    }





    // Supprimer un item dans une commande
    @DeleteMapping("/{idCommande}/supp-article/{idProduit}")
public ResponseEntity<?> supprimerArticle(@PathVariable("idCommande") UUID idCommande,
                                             @PathVariable("idProduit") UUID idProduit) {
        try {
            ICommandeService.supprimerProduit(idCommande, idProduit);
            return ResponseEntity.ok().build();
        } catch (CommandeNotFoundException | ItemCommandeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDtoOut(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionDtoOut("Une erreur s'est produite lors de la suppression de l'article."));
        }
    }


}
