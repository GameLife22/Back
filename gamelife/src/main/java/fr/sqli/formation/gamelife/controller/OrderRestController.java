package fr.sqli.formation.gamelife.controller;

import fr.sqli.formation.gamelife.dto.request.OrderRequest;
import fr.sqli.formation.gamelife.utility.converter.IOrderConverter;
import fr.sqli.formation.gamelife.dto.request.ItemOrderRequest;
import fr.sqli.formation.gamelife.dto.response.OrderResponse;
import fr.sqli.formation.gamelife.dto.response.ExceptionResponse;
import fr.sqli.formation.gamelife.dto.response.ItemOrderResponse;
import fr.sqli.formation.gamelife.exception.InvalidStatusOrderException;
import fr.sqli.formation.gamelife.exception.ParameterException;
import fr.sqli.formation.gamelife.exception.SellerGameException;
import fr.sqli.formation.gamelife.exception.NonExistentUserException;
import fr.sqli.formation.gamelife.exception.ItemOrderNotFoundException;
import fr.sqli.formation.gamelife.exception.OrderNotFoundException;
import fr.sqli.formation.gamelife.repository.IOrderRepository;
import fr.sqli.formation.gamelife.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/commande")
public class OrderRestController {

    @Autowired
    private IOrderService IOrderService;
    @Autowired
    private IOrderRepository IOrderRepository;
    private IOrderConverter IOrderConverter;

    private static final Logger logger = LoggerFactory.getLogger(AccountRestController.class);

    // Recuperer une seule commande
    @GetMapping("/{userId}/produits")
    public ResponseEntity<?> getAllProduitsPanier(@PathVariable UUID userId) {
        try {
            return ResponseEntity.ok(this.IOrderService.getAllProduitsPanier(userId));
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse("Une erreur s'est produite lors de la récupération des produits."));
        }
    }

    // Recuperer une seule commande
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getCommande(@PathVariable UUID id) {
        try {
            OrderResponse commandeDto = IOrderService.getCommande(id);
            return new ResponseEntity<>(commandeDto, HttpStatus.OK);
        } catch (OrderNotFoundException ex) {
            logger.error("Commande non trouvée avec l'ID : {}", id, ex);
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            logger.error("Une erreur inattendue est survenue lors de la récupération de la commande avec l'ID : {}", id, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Afficher le prix total du commande
    @GetMapping("/{id}/prix-total")
    public double getPrixTotalPanier(@PathVariable UUID id) throws OrderNotFoundException {
        return IOrderService.getPrixTotalCommande(id);
    }



    // Crée une commande pour un utilisateur
    @PostMapping("/creer")
    public ResponseEntity<OrderRequest> creerCommande(@RequestBody OrderRequest commandeDto) {
        // Validation de la date de livraison
        LocalDate today = LocalDate.now();
        if (commandeDto.getDate().isBefore(today)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date de livraison invalide");
        }

        // Logique de création de commande
        try {
            OrderRequest createdCommande = IOrderService.creerCommande(commandeDto);
            return new ResponseEntity<>(createdCommande, HttpStatus.CREATED);
        } catch (NonExistentUserException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non existant", e);
        }
    }


    // Modifier une commande
    @PutMapping("/{id}")
    public ResponseEntity<OrderRequest> modifierCommande(@PathVariable UUID id, @RequestBody OrderRequest commandeDto) throws OrderNotFoundException {
        OrderRequest modifierdCommandeDto = IOrderService.modifierCommande(id, commandeDto);
        return new ResponseEntity<>(modifierdCommandeDto, HttpStatus.OK);
    }

    // Supprimer une commande
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCommande(@PathVariable UUID id) {
        try {
            IOrderService.deleteCommande(id);
            return ResponseEntity.ok().build();
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse("Une erreur s'est produite lors de la suppression de la commande."));
        }
    }

    // Modifier la quantité d'un item dans une commande

    @PutMapping("/{id}/modif-quantite")
    public ResponseEntity<?> modifierQuantite(@PathVariable("id") UUID id,
                                              @RequestBody ItemOrderRequest itemCommandeDto) {
        try {
            ItemOrderResponse commandeDtoOut = IOrderService.modifierQuantite(id, itemCommandeDto);
            return ResponseEntity.ok(commandeDtoOut);
        } catch (OrderNotFoundException | ItemOrderNotFoundException | IllegalAccessException e) {
            // Gérer les exceptions spécifiques
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(e.getMessage()));
        } catch (ParameterException e) {
            // Capturer les exceptions de paramètre et renvoyer une réponse BadRequest
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(e.getMessage()));
        } catch (Exception e) {
            // Capturer les autres exceptions et renvoyer une réponse BadRequest
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse("Une erreur s'est produite lors du traitement de la demande."));
        }
    }

    // Ajouter un produit dans une commande existante (id) sinon créer une nouvelle commande
    @PutMapping("/{id}/ajout-produit")
    public ResponseEntity<ItemOrderResponse> ajoutProduit(@PathVariable("id") UUID idUtilisateur,
                                                          @RequestBody ItemOrderRequest itemCommandeDto) {
        try {
            logger.info("Requête d'ajout de produit pour l'utilisateur avec l'ID : {}", idUtilisateur);

            // Your business logic to add the product to the order
            ItemOrderResponse itemOrderResponse = IOrderService.ajoutProduit(idUtilisateur, itemCommandeDto);

            logger.info("Produit ajouté avec succès pour l'utilisateur avec l'ID : {}", idUtilisateur);

            return new ResponseEntity<>(itemOrderResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Une erreur s'est produite lors de l'ajout du produit pour l'utilisateur avec l'ID : {}", idUtilisateur, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }







    // Valider une commande (changer l'état de la commande)
    @PutMapping("/{id}/valider-commande")
    public ResponseEntity<?> validerCommande(@PathVariable("id") UUID id) {
        try {
            OrderResponse OrderResponse = IOrderService.validerCommande(id);

            return ResponseEntity.ok(OrderResponse);
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse("Une erreur s'est produite lors de la validation de la commande."));
        }
    }





    // Supprimer un item dans une commande
    @DeleteMapping("/{idCommande}/supp-article/{idProduit}")
    public ResponseEntity<?> supprimerArticle(@PathVariable("idCommande") UUID idCommande,
                                              @PathVariable("idProduit") UUID idProduit) {
        try {
            IOrderService.supprimerProduit(idCommande, idProduit);
            return ResponseEntity.ok().build();
        } catch (OrderNotFoundException | ItemOrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse("Une erreur s'est produite lors de la suppression de l'article."));
        }
    }


}
