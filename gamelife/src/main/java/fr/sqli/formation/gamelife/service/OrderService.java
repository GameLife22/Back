package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.utility.converter.IItemOrderConverter;
import fr.sqli.formation.gamelife.dto.request.OrderRequest;
import fr.sqli.formation.gamelife.utility.converter.IOrderConverter;
import fr.sqli.formation.gamelife.dto.request.ItemOrderRequest;
import fr.sqli.formation.gamelife.dto.response.OrderResponse;
import fr.sqli.formation.gamelife.dto.response.ItemOrderResponse;
import fr.sqli.formation.gamelife.entity.*;
import fr.sqli.formation.gamelife.enumeration.OrderStatus;
import fr.sqli.formation.gamelife.exception.InvalidStatusOrderException;
import fr.sqli.formation.gamelife.exception.ParameterException;
import fr.sqli.formation.gamelife.exception.SellerGameException;
import fr.sqli.formation.gamelife.exception.NonExistentUserException;
import fr.sqli.formation.gamelife.exception.ItemOrderNotFoundException;
import fr.sqli.formation.gamelife.exception.OrderNotFoundException;
import fr.sqli.formation.gamelife.repository.*;
import fr.sqli.formation.gamelife.utility.validator.FieldValidator;
import fr.sqli.formation.gamelife.utility.validator.OrderValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private IOrderRepository iOrderRepository;
    @Autowired
    private ItemOrderRepository itemOrderRepository;
    @Autowired
    private IGameRepository produitRepository;

    @PersistenceContext
    private EntityManager entityManager;


    private OrderRequest commandeDto;
    @Autowired
    private IUserRepository IUserRepository;
    @Autowired
    private ISellerGameRepository iSellerGameRepository;

    @Autowired
    private OrderValidator orderValidator;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);



    /*
    // Recuperation de tous les paniers
    @Override
    public List<CommandeDtoOut> getAllCommandes() {
        List<CommandeEntity> commandeEntities = commandeRepository.findAll();
        return commandeEntities.stream()
                .map(commandeDtoHandler::EntityToDto)
                .collect(Collectors.toList());
    }
    */

    // Recuperer une seule commande

    @Override
    public List<OrderResponse> getAllCommandes() {
        return null;
    }

    public OrderResponse getCommande(UUID id) throws OrderNotFoundException {
        OrderEntity orderEntity = iOrderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Commande non trouvée avec l'ID : " + id));

        // Récupérer l'état de la commande depuis l'entité CommandeEntity
        OrderStatus etat = orderEntity.getEtat();

        // Mapper l'entité CommandeEntity vers CommandeDtoOut
        OrderResponse orderResponse = IOrderConverter.EntityToDto(orderEntity);
        orderResponse.setEtat(etat);

        return orderResponse;
    }

    // Création du commande
    @Override
    public OrderRequest creerCommande(OrderRequest commandeDto) throws NonExistentUserException {
        // Vérifier si l'utilisateur existe
        UserEntity utilisateur = IUserRepository.findById(commandeDto.getIdUtilisateur())
                .orElseThrow(() -> new NonExistentUserException("User not found with ID: " + commandeDto.getIdUtilisateur()));

        OrderEntity orderEntity = IOrderConverter.DtoToEntity(commandeDto);

        orderEntity.setEtat(OrderStatus.NOUVELLE);


        orderEntity.setUtilisateur(utilisateur);

        OrderEntity savedOrderEntity = iOrderRepository.save(orderEntity);

        OrderResponse savedCommandeDto = IOrderConverter.EntityToDto(savedOrderEntity);

        return commandeDto;
    }


    @Override
    public OrderRequest modifierCommande(UUID id, OrderRequest commandeDto) throws OrderNotFoundException {

        OrderEntity existingCommande = iOrderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Commande non trouvée avec l'ID : " + id));

        // Mettre à jour les informations de la commande avec les données fournies dans le DTO
        existingCommande.setEtat(commandeDto.getEtat());
        existingCommande.setStreetNumberLivraison(commandeDto.getStreetNumberLivraison());
        existingCommande.setStreetLivraison(commandeDto.getStreetLivraison());
        existingCommande.setCityLivraison(commandeDto.getCityLivraison());
        existingCommande.setZipCodeLivraison(commandeDto.getZipCodeLivraison());
        existingCommande.setDate(commandeDto.getDate());

        iOrderRepository.save(existingCommande);

        return commandeDto;
    }


    @Override
    public void deleteCommande(UUID id) throws OrderNotFoundException {
        OrderEntity commande = iOrderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Commande non trouvée avec l'ID : " + id));

        iOrderRepository.delete(commande);
    }


    // Prix total du commande
    @Override
    public double getPrixTotalCommande(UUID id) throws OrderNotFoundException {
        OrderEntity orderEntity = iOrderRepository.findByIdWithItemCommandes(id)
                .orElseThrow(() -> new OrderNotFoundException("Commande non trouvée avec l'ID : " + id));

        List<ItemOrderEntity> itemsCommande = orderEntity.getItemsCommande();

        // Vérification que la liste des éléments de commande n'est pas vide
        if (itemsCommande.isEmpty()) {
            throw new IllegalStateException("La liste des éléments de commande est vide pour la commande avec l'ID : " + id);
        }

        return itemsCommande.stream()
                .mapToDouble(itemCommande -> {

                    if (itemCommande.recupererProduitRevendeur() == null || itemCommande.recupererProduitRevendeur().getPrix() == null) {
                        throw new IllegalStateException("Le produit revendeur ou son prix est nul pour l'élément de commande avec l'ID : " + itemCommande.getId());
                    }

                    // Vérification des valeurs négatives
                    if (itemCommande.getQuantite() < 0 || itemCommande.recupererProduitRevendeur().getPrix().doubleValue() < 0) {
                        throw new IllegalStateException("La quantité ou le prix est négatif pour l'élément de commande avec l'ID : " + itemCommande.getId());
                    }

                    return itemCommande.recupererProduitRevendeur().getPrix().doubleValue() * itemCommande.getQuantite();
                })
                .sum();
    }


    // Ajouter un article dans une commande
    @Override
    public ItemOrderResponse modifierQuantite(UUID id, ItemOrderRequest itemCommandeDto)
            throws OrderNotFoundException, ItemOrderNotFoundException, ParameterException, IllegalAccessException {
        // Vérifier l'existence de la commande avec l'ID spécifié
        OrderEntity orderEntity = iOrderRepository.findByIdWithItemCommandes(id)
                .orElseThrow(() -> new OrderNotFoundException("La commande avec l'ID " + id + " n'a pas été trouvée."));

        // Trouver l'item de commande correspondant dans la commande
        ItemOrderEntity itemOrderEntity = null;
        for (ItemOrderEntity item : orderEntity.getItemsCommande()) {
            if (item.getId().equals(itemCommandeDto.getIdCommande())) {
                itemOrderEntity = item;
                break;
            }
        }

        if (itemOrderEntity == null) {
            throw new ItemOrderNotFoundException("L'item de commande avec l'ID " + itemCommandeDto.getIdCommande() + " n'a pas été trouvé dans la commande.");
        }

        // Vérifier si la quantité est valide
        FieldValidator.validateNonNegative(itemCommandeDto.getQuantite(), "La quantité ne peut pas être négative.");

        // Mettre à jour la quantité de l'item de commande
        itemOrderEntity.setQuantite(itemCommandeDto.getQuantite());
        iOrderRepository.save(orderEntity);

        // Mapper l'entité mise à jour vers un DTO de sortie
        return IItemOrderConverter.EntityToDto(itemOrderEntity);
    }


    // Ajouter un produit dans une commande existante (id) sinon cree une nouvelle commande verifier si l'utilisateur existe
    @Override
    public ItemOrderResponse ajoutProduit(UUID pIdUtilisateur, ItemOrderRequest pItemOrderRequest) throws SellerGameException, ParameterException, OrderNotFoundException, InvalidStatusOrderException {
        // Chercher la commande de l'utilisateur
        OrderEntity orderEntity = iOrderRepository.findByUserId(pIdUtilisateur)
                .orElseThrow(() -> new OrderNotFoundException("Commande non trouvée avec l'identifiant : " + pIdUtilisateur));

        // Vérifier si la commande est dans un état permettant l'ajout de produit
        if (orderEntity.getEtat() == OrderStatus.EN_COURS_DE_TRAITEMENT) {
            throw new InvalidStatusOrderException("Impossible d'ajouter un produit à une commande en cours de traitement.");
        }

        // Vérifier si le produit revendeur existe
        Optional<SellerGameEntity> produitRevendeurOptional = iSellerGameRepository.findById(pItemOrderRequest.getIdProduitRevendeur());

        if (produitRevendeurOptional.isEmpty()) {
            throw new SellerGameException("Produit revendeur non trouvé avec l'identifiant : " + pItemOrderRequest.getIdProduitRevendeur());
        }

        SellerGameEntity sellerGameEntity = produitRevendeurOptional.get();

        // Vérifier si la quantité est valide
        if (pItemOrderRequest.getQuantite() <= 0) {
            throw new ParameterException("La quantité doit être supérieure à zéro.");
        }

        // Vérifier si le stock est suffisant
        if (sellerGameEntity.getStock() < pItemOrderRequest.getQuantite()) {
            throw new SellerGameException("Stock insuffisant pour le produit revendeur avec l'identifiant : " + sellerGameEntity.getId());
        }


        // Créer un nouvel item de commande
        ItemOrderEntity itemOrderEntity = new ItemOrderEntity();
        itemOrderEntity.setCommande(orderEntity);
        itemOrderEntity.setProduitRevendeur(sellerGameEntity);
        itemOrderEntity.setQuantite(pItemOrderRequest.getQuantite());

        // Décrémenter le stock du produit revendeur
        sellerGameEntity.setStock(sellerGameEntity.getStock() - pItemOrderRequest.getQuantite());


        // Enregistrer l'item de commande et mettre à jour le produit revendeur
        itemOrderEntity = itemOrderRepository.save(itemOrderEntity);
        sellerGameEntity = iSellerGameRepository.save(sellerGameEntity);

        // Mettre à jour la liste des items de commande de la commande
        orderEntity.getItemsCommande().add(itemOrderEntity);
        var commandeEntite1 = iOrderRepository.save(orderEntity);

        // Convertir l'item de commande en DTO de sortie
        return IItemOrderConverter.EntityToDto(itemOrderEntity);
    }


    // Valider une commande, changer son état en EN_COURS_DE_TRAITEMENT
    @Override
    public OrderResponse validerCommande(UUID id) throws OrderNotFoundException {
        OrderEntity orderEntity = iOrderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Commande non trouvée avec l'ID : " + id));

        // Changer l'état de la commande
        orderEntity.setEtat(OrderStatus.EN_COURS_DE_TRAITEMENT);
        iOrderRepository.save(orderEntity);


        return IOrderConverter.EntityToDto(orderEntity);
    }


    // Supprimer un article dans une commande existante
    @Override
    public OrderRequest deleteGame(UUID idCommande, UUID idProduit) throws OrderNotFoundException, ItemOrderNotFoundException, InvalidStatusOrderException {
        // Chercher la commande
        OrderEntity orderEntity = iOrderRepository.findById(idCommande)
                .orElseThrow(() -> new OrderNotFoundException("Commande non trouvée avec l'ID : " + idCommande));

        // Vérifier si la commande est dans un état permettant la suppression d'article
        if (orderEntity.getEtat() == OrderStatus.EN_COURS_DE_TRAITEMENT) {
            throw new InvalidStatusOrderException("Impossible de supprimer un article d'une commande en cours de traitement.");
        }

        // Chercher l'item de commande
        ItemOrderEntity itemOrderEntity = null;
        for (ItemOrderEntity item : orderEntity.getItemsCommande()) {
            if (item.recupererProduitRevendeur().getId() == idProduit) {
                itemOrderEntity = item;
                break;
            }
        }

        if (itemOrderEntity == null) {
            throw new ItemOrderNotFoundException("L'item de commande avec l'ID " + idProduit + " n'a pas été trouvé dans la commande.");
        }

        // Récupérer le produit revendeur
        SellerGameEntity sellerGameEntity = itemOrderEntity.recupererProduitRevendeur();

        // Supprimer l'item de commande
        orderEntity.getItemsCommande().remove(itemOrderEntity);
        itemOrderRepository.delete(itemOrderEntity);

        // Incrémenter le stock du produit revendeur
        sellerGameEntity.setStock(sellerGameEntity.getStock() + itemOrderEntity.getQuantite());
        iSellerGameRepository.save(sellerGameEntity);

        // Mettre à jour la commande
        iOrderRepository.save(orderEntity);

        return commandeDto;
    }
}