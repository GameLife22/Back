package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.response.GameResponse;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private IOrderRepository IOrderRepository;

    @Autowired
    private ItemOrderRepository ItemOrderRepository;

    @Autowired
    private IGameRepository ProduitRepository;

    @Autowired
    private IUserRepository IUserRepository;
    @PersistenceContext
    private EntityManager entityManager;

    private OrderRequest commandeDto;


    @Autowired
    private ISellerGameRepository ISellerGameRepository;

    @Autowired
    private OrderValidator orderValidator;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);


    

    @Override
    public List<GameResponse> getAllProduitsPanier(UUID userId) throws OrderNotFoundException {

        OrderEntity OrderEntity = IOrderRepository.findByUtilisateurIdWithItemCommandes(userId)
                .orElseThrow(() -> new OrderNotFoundException("Commande non trouvée avec l'identifiant de l'utilisateur : " + userId));

        List<GameResponse> produitsPanier = new ArrayList<>();

        for (ItemOrderEntity itemCommande : OrderEntity.getItemsCommande()) {
            GameEntity produit = itemCommande.recupererProduitRevendeur().getProduit();

            GameResponse produitDetails = new GameResponse();
            produitDetails.setName(produit.getName()); // Utiliser le nom du produit
            produitDetails.setPrix(itemCommande.recupererProduitRevendeur().getPrix()); // Utiliser le prix du produit revendeur
            produitsPanier.add(produitDetails);
        }

        return produitsPanier;
    }




    // Recuperer une seule commande

    @Override
    public List<OrderResponse> getAllCommandes() {
        return null;
    }

    public OrderResponse getCommande(UUID id) throws OrderNotFoundException {
        try {
            OrderEntity OrderEntity = IOrderRepository.findByUtilisateurId(id)
                    .orElseThrow(() -> new OrderNotFoundException("Commande non trouvée avec l'ID : " + id));

            return IOrderConverter.EntityToDto(OrderEntity);
        } catch (OrderNotFoundException ex) {
            LOGGER.error("Erreur lors de la récupération de la commande avec l'ID : {}", id, ex);
            throw ex;
        } catch (Exception ex) {
            LOGGER.error("Une erreur inattendue est survenue lors de la récupération de la commande avec l'ID : {}", id, ex);
            throw ex;
        }
    }

    // Création du commande
    @Override
    public OrderRequest creerCommande(OrderRequest commandeDto) throws NonExistentUserException {
        // Vérifier si l'utilisateur existe
        UserEntity utilisateur = IUserRepository.findById(commandeDto.getIdUtilisateur())
                .orElseThrow(() -> new NonExistentUserException("User not found with id: " + commandeDto.getIdUtilisateur()));

        OrderEntity OrderEntity = IOrderConverter.DtoToEntity(commandeDto);

        OrderEntity.setEtat(OrderStatus.NOUVELLE);


        OrderEntity.setUtilisateur(utilisateur);

        OrderEntity savedOrderEntity = IOrderRepository.save(OrderEntity);

        OrderResponse savedCommandeDto = IOrderConverter.EntityToDto(savedOrderEntity);

        return commandeDto;
    }


    @Override
    public OrderRequest modifierCommande(UUID id, OrderRequest commandeDto) throws OrderNotFoundException {

        OrderEntity existingCommande = IOrderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Commande non trouvée avec l'ID : " + id));

        // Mettre à jour les informations de la commande avec les données fournies dans le DTO
        existingCommande.setEtat(commandeDto.getEtat());
        existingCommande.setStreetNumberLivraison(commandeDto.getNumRueLivraison());
        existingCommande.setStreetLivraison(commandeDto.getRueLivraison());
        existingCommande.setCityLivraison(commandeDto.getVilleLivraison());
        existingCommande.setZipCodeLivraison(commandeDto.getCodePostalLivraison());
        existingCommande.setDate(commandeDto.getDate());

        IOrderRepository.save(existingCommande);

        return commandeDto;
    }


    @Override
    public void deleteCommande(UUID id) throws OrderNotFoundException {
        OrderEntity OrderEntity = IOrderRepository.findByUtilisateurId(id)
                .orElseThrow(() -> new OrderNotFoundException("Commande non trouvée avec l'ID : " + id));

        IOrderRepository.delete(OrderEntity);
    }


    // Prix total du commande
    @Override
    public double getPrixTotalCommande(UUID id) throws OrderNotFoundException {
        OrderEntity OrderEntity = IOrderRepository.findByIdWithItemCommandes(id)
                .orElseThrow(() -> new OrderNotFoundException("Commande non trouvée avec l'ID : " + id));

        List<ItemOrderEntity> itemsCommande = OrderEntity.getItemsCommande();

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
        OrderEntity OrderEntity = IOrderRepository.findByIdWithItemCommandes(id)
                .orElseThrow(() -> new OrderNotFoundException("La commande avec l'ID " + id + " n'a pas été trouvée."));

        // Trouver l'item de commande correspondant dans la commande
        ItemOrderEntity itemOrderEntity = null;
        for (ItemOrderEntity item : OrderEntity.getItemsCommande()) {
            if (item.getId().equals(itemCommandeDto.getIdCommande())) {
                itemOrderEntity = item;
                break;
            }
        }

        if (itemOrderEntity == null) {
            throw new ItemOrderNotFoundException("L'item de commande avec l'ID " + itemCommandeDto.getIdCommande() + " n'a pas été trouvé dans la commande.");
        }

        // Vérifier si la quantité est valide
        FieldValidator.ItemOrderRepository(itemCommandeDto.getQuantite(), "La quantité ne peut pas être négative.");

        // Mettre à jour la quantité de l'item de commande
        itemOrderEntity.setQuantite(itemCommandeDto.getQuantite());
        IOrderRepository.save(OrderEntity);

        // Mapper l'entité mise à jour vers un DTO de sortie
        return IItemOrderConverter.EntityToDto(itemOrderEntity);
    }


    // Ajouter un produit dans une commande existante (id) sinon cree une nouvelle commande verifier si l'utilisateur existe
    @Override
    public ItemOrderResponse ajoutProduit(UUID pIdUtilisateur, ItemOrderRequest pItemOrderRequest) throws SellerGameException, ParameterException, OrderNotFoundException, InvalidStatusOrderException {
        // Chercher la commande de l'utilisateur
        OrderEntity OrderEntity = IOrderRepository.findByUtilisateurId(pIdUtilisateur)
                .orElseThrow(() -> new OrderNotFoundException("Commande non trouvée avec l'identifiant : " + pIdUtilisateur));

        // Vérifier si la commande est dans un état permettant l'ajout de produit
        if (OrderEntity.getEtat() == OrderStatus.EN_COURS_DE_TRAITEMENT) {
            throw new InvalidStatusOrderException("Impossible d'ajouter un produit à une commande en cours de traitement.");
        }

        // Vérifier si le produit revendeur existe
        Optional<SellerGameEntity> produitRevendeurOptional = ISellerGameRepository.findById(pItemOrderRequest.getIdProduitRevendeur());

        if (produitRevendeurOptional.isEmpty()) {
            throw new SellerGameException("Produit revendeur non trouvé avec l'identifiant : " + pItemOrderRequest.getIdProduitRevendeur());
        }

        SellerGameEntity SellerGameEntity = produitRevendeurOptional.get();

        // Vérifier si la quantité est valide
        if (pItemOrderRequest.getQuantite() <= 0) {
            throw new ParameterException("La quantité doit être supérieure à zéro.");
        }

        // Vérifier si le stock est suffisant
        if (SellerGameEntity.getStock() < pItemOrderRequest.getQuantite()) {
            throw new SellerGameException("Stock insuffisant pour le produit revendeur avec l'identifiant : " + SellerGameEntity.getId());
        }


        // Créer un nouvel item de commande
        ItemOrderEntity itemOrderEntity = new ItemOrderEntity();
        itemOrderEntity.setCommande(OrderEntity);
        itemOrderEntity.setProduitRevendeur(SellerGameEntity);
        itemOrderEntity.setQuantite(pItemOrderRequest.getQuantite());

        // Décrémenter le stock du produit revendeur
        SellerGameEntity.setStock(SellerGameEntity.getStock() - pItemOrderRequest.getQuantite());


        // Enregistrer l'item de commande et mettre à jour le produit revendeur
        itemOrderEntity = ItemOrderRepository.save(itemOrderEntity);
        SellerGameEntity = ISellerGameRepository.save(SellerGameEntity);

        // Mettre à jour la liste des items de commande de la commande
        OrderEntity.getItemsCommande().add(itemOrderEntity);
        var OrderEntity1 = IOrderRepository.save(OrderEntity);

        // Convertir l'item de commande en DTO de sortie
        return IItemOrderConverter.EntityToDto(itemOrderEntity);
    }


    // Valider une commande, changer son état en EN_COURS_DE_TRAITEMENT
    @Override
    public OrderResponse validerCommande(UUID id) throws OrderNotFoundException {
        // Chercher la commande
        OrderEntity OrderEntity = IOrderRepository.findByUtilisateurIdWithItemCommandes(id)
                .orElseThrow(() -> new OrderNotFoundException("Commande non trouvée avec l'ID : " + id));

        // Changer l'état de la commande
        OrderEntity.setEtat(OrderStatus.EN_COURS_DE_TRAITEMENT);
        IOrderRepository.save(OrderEntity);


        return IOrderConverter.EntityToDto(OrderEntity);
    }


    // Supprimer un article dans une commande existante
    @Override
    public OrderRequest supprimerProduit(UUID idCommande, UUID idProduit) throws OrderNotFoundException, ItemOrderNotFoundException, InvalidStatusOrderException {
        // Chercher la commande
        OrderEntity OrderEntity = IOrderRepository.findById(idCommande)
                .orElseThrow(() -> new OrderNotFoundException("Commande non trouvée avec l'ID : " + idCommande));

        // Vérifier si la commande est dans un état permettant la suppression d'article
        if (OrderEntity.getEtat() == OrderStatus.EN_COURS_DE_TRAITEMENT) {
            throw new InvalidStatusOrderException("Impossible de supprimer un article d'une commande en cours de traitement.");
        }

        // Chercher l'item de commande
        ItemOrderEntity itemOrderEntity = null;
        for (ItemOrderEntity item : OrderEntity.getItemsCommande()) {
            if (item.recupererProduitRevendeur().getId() == idProduit) {
                itemOrderEntity = item;
                break;
            }
        }

        if (itemOrderEntity == null) {
            throw new ItemOrderNotFoundException("L'item de commande avec l'ID " + idProduit + " n'a pas été trouvé dans la commande.");
        }

        // Récupérer le produit revendeur
        SellerGameEntity SellerGameEntity = itemOrderEntity.recupererProduitRevendeur();

        // Supprimer l'item de commande
        OrderEntity.getItemsCommande().remove(itemOrderEntity);
        ItemOrderRepository.delete(itemOrderEntity);

        // Incrémenter le stock du produit revendeur
        SellerGameEntity.setStock(SellerGameEntity.getStock() + itemOrderEntity.getQuantite());
        ISellerGameRepository.save(SellerGameEntity);

        // Mettre à jour la commande
        IOrderRepository.save(OrderEntity);

        return commandeDto;
    }


}