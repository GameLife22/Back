package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.request.OrderRequest;
import fr.sqli.formation.gamelife.dto.request.ItemOrderRequest;
import fr.sqli.formation.gamelife.dto.response.OrderResponse;
import fr.sqli.formation.gamelife.dto.response.ItemOrderResponse;
import fr.sqli.formation.gamelife.exception.InvalidStatusOrderException;
import fr.sqli.formation.gamelife.exception.ParameterException;
import fr.sqli.formation.gamelife.exception.SellerGameException;
import fr.sqli.formation.gamelife.exception.NonExistentUserException;
import fr.sqli.formation.gamelife.exception.ItemOrderNotFoundException;
import fr.sqli.formation.gamelife.exception.OrderNotFoundException;

import java.util.List;
import java.util.UUID;


public interface IOrderService {
    List<OrderResponse> getAllCommandes();

    // Recuperer une seule commande
    OrderResponse getCommande(UUID id) throws OrderNotFoundException;


   OrderRequest creerCommande(OrderRequest commandeDto) throws NonExistentUserException;

    OrderRequest modifierCommande(UUID id, OrderRequest commandeDto) throws OrderNotFoundException;

    void deleteCommande(UUID id) throws OrderNotFoundException;

    double getPrixTotalCommande(UUID id) throws OrderNotFoundException;


    ItemOrderResponse modifierQuantite(UUID id, ItemOrderRequest itemCommandeDto) throws OrderNotFoundException, ItemOrderNotFoundException, ParameterException, IllegalAccessException;

    // ajouter un produit dans une commande existante (id) sinon cree une nouvelle commande
    ItemOrderResponse ajoutProduit(UUID id, ItemOrderRequest itemCommandeDto) throws OrderNotFoundException, SellerGameException, ParameterException, InvalidStatusOrderException;



    OrderResponse validerCommande(UUID id) throws OrderNotFoundException, SellerGameException;

    OrderRequest deleteGame(UUID idCommande, UUID idProduit) throws OrderNotFoundException, SellerGameException, ItemOrderNotFoundException, InvalidStatusOrderException;


}