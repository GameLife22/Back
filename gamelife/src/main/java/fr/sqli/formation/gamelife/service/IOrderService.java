package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.request.OrderRequest;
import fr.sqli.formation.gamelife.dto.request.ItemOrderRequest;
import fr.sqli.formation.gamelife.dto.response.GameResponse;
import fr.sqli.formation.gamelife.dto.response.OrderResponse;
import fr.sqli.formation.gamelife.dto.response.ItemOrderResponse;
import fr.sqli.formation.gamelife.exception.*;

import java.util.List;
import java.util.UUID;


public interface IOrderService {
    List<OrderResponse> getAllCommandes();

    List<GameResponse> getAllProduitsPanier(UUID userId) throws OrderNotFoundException;

    // Recuperer une seule commande
    OrderResponse getCommande(UUID id) throws OrderNotFoundException;

    // recuperer tous les produits qu'un utilisateur a dans son panier
    OrderRequest creerCommande(OrderRequest commandeDto) throws NonExistentUserException;

    OrderRequest modifierCommande(UUID id, OrderRequest commandeDto) throws OrderNotFoundException;

    void deleteCommande(UUID id) throws OrderNotFoundException;

    double getPrixTotalCommande(UUID id) throws OrderNotFoundException;


    ItemOrderResponse modifierQuantite(UUID id, ItemOrderRequest itemCommandeDto) throws OrderNotFoundException, ItemOrderNotFoundException, ParameterException, IllegalAccessException;

    // ajouter un produit dans une commande existante (id) sinon cree une nouvelle commande
    ItemOrderResponse ajoutProduit(UUID id, ItemOrderRequest itemCommandeDto) throws OrderNotFoundException, SellerGameException, ParameterException, InvalidStatusOrderException;



    OrderResponse validerCommande(UUID id) throws OrderNotFoundException, SellerGameException;

    OrderRequest supprimerProduit(UUID idCommande, UUID idProduit) throws OrderNotFoundException, SellerGameException, ItemOrderNotFoundException, InvalidStatusOrderException;

}