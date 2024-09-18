package fr.sqli.formation.gamelife.controller;

import fr.sqli.formation.gamelife.dto.request.OrderRequest;
import fr.sqli.formation.gamelife.exception.*;
import fr.sqli.formation.gamelife.utility.converter.IOrderConverter;
import fr.sqli.formation.gamelife.dto.request.ItemOrderRequest;
import fr.sqli.formation.gamelife.dto.response.OrderResponse;
import fr.sqli.formation.gamelife.dto.response.ExceptionResponse;
import fr.sqli.formation.gamelife.dto.response.ItemOrderResponse;
import fr.sqli.formation.gamelife.repository.IOrderRepository;
import fr.sqli.formation.gamelife.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.UUID;

@RestController
@RequestMapping("/commande")
public class OrderRestController {

    @Autowired
    private IOrderService IOrderService;
    @Autowired
    private IOrderRepository IOrderRepository;
    private IOrderConverter IOrderConverter;

    // Recuperer une seule commande
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getCommande(@PathVariable UUID id) throws OrderNotFoundException {
        OrderResponse commandeDto = IOrderService.getCommande(id);
        return ResponseEntity.status(commandeDto != null ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(commandeDto);
    }

    // Afficher le prix total du commande
    @GetMapping("/{id}/prix-total")
    public double getPrixTotalPanier(@PathVariable UUID id) throws OrderNotFoundException {
        return IOrderService.getPrixTotalCommande(id);
    }



    // Crée une commande pour un utilisateur
    @PostMapping("/creer")
    public ResponseEntity<OrderRequest> creerCommande(@RequestBody OrderRequest commandeDto) {
        try {
            OrderRequest creerdCommande = IOrderService.creerCommande(commandeDto);
            return new ResponseEntity<>(creerdCommande, HttpStatus.CREATED);
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
    public ResponseEntity<String> deleteCommande(@PathVariable("id") UUID id) {
        try {
            IOrderService.deleteCommande(id);
            return new ResponseEntity<>("La commande a été supprimée avec succès", HttpStatus.OK);
        } catch (OrderNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
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
            ItemOrderResponse itemOrderResponse = IOrderService.ajoutProduit(idUtilisateur, itemCommandeDto);
            return ResponseEntity.ok(itemOrderResponse);
        } catch (SellerGameException | ParameterException |
                 OrderNotFoundException | InvalidStatusOrderException e) {
            // Gérer les exceptions
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }







    // Valider une commande (changer l'état de la commande)
    @PutMapping("/{id}/valider-commande")
    public ResponseEntity<?> validerCommande(@PathVariable("id") UUID id) {
        try {
            OrderResponse orderResponse = IOrderService.validerCommande(id);

            return ResponseEntity.ok(orderResponse);
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
    public ResponseEntity<?> deleteGame(@PathVariable("idCommande") UUID idCommande,
                                             @PathVariable("idProduit") UUID idProduit) {
        try {
            OrderRequest orderRequest = IOrderService.deleteGame(idCommande, idProduit);
            return ResponseEntity.ok(orderRequest);
        } catch (OrderNotFoundException | SellerGameException | ItemOrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse("Une erreur s'est produite lors de la suppression de l'article."));
        }
    }


}
