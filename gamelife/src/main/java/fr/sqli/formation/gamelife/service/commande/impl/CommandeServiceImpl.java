package fr.sqli.formation.gamelife.service.commande.impl;

import fr.sqli.formation.gamelife.dto.handler.ItemCommandeDtoHandler;
import fr.sqli.formation.gamelife.dto.in.CommandeDtoIn;
import fr.sqli.formation.gamelife.dto.handler.CommandeDtoHandler;
import fr.sqli.formation.gamelife.dto.in.ItemCommandeDtoIn;
import fr.sqli.formation.gamelife.dto.in.ProduitRevendeurDtoIn;
import fr.sqli.formation.gamelife.dto.out.CommandeDtoOut;
import fr.sqli.formation.gamelife.dto.out.ItemCommandeDtoOut;
import fr.sqli.formation.gamelife.entity.*;
import fr.sqli.formation.gamelife.enums.EtatCommande;
import fr.sqli.formation.gamelife.ex.ParameterException;
import fr.sqli.formation.gamelife.ex.ProduitRevendeutException;
import fr.sqli.formation.gamelife.ex.UtilisateurNonExistantException;
import fr.sqli.formation.gamelife.ex.commande.ItemCommandeNotFoundException;
import fr.sqli.formation.gamelife.ex.commande.CommandeNotFoundException;
import fr.sqli.formation.gamelife.repository.*;
import fr.sqli.formation.gamelife.service.commande.CommandeService;
import fr.sqli.formation.gamelife.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommandeServiceImpl implements CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;
    @Autowired
    private ItemCommandeRepository itemCommandeRepository;
    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private CommandeDtoHandler commandeDtoHandler;

    @PersistenceContext
    private EntityManager entityManager;


    private CommandeDtoIn commandeDto;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    private ItemCommandeDtoHandler itemCommandeDtoHandler;
    @Autowired
    private ProduitRevendeurRepository produitRevendeurRepository;


    @Autowired
    public CommandeServiceImpl(CommandeDtoHandler commandeDtoHandler) {
        this.commandeDtoHandler = commandeDtoHandler;
    }

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
    public List<CommandeDtoOut> getAllCommandes() {
        return null;
    }

    public CommandeDtoOut getCommande(int id) throws CommandeNotFoundException {
        CommandeEntity commandeEntity = commandeRepository.findById(id)
                .orElseThrow(() -> new CommandeNotFoundException("Commande non trouvée avec l'ID : " + id));

        // Récupérer l'état de la commande depuis l'entité CommandeEntity
        EtatCommande etat = commandeEntity.getEtat();

        // Mapper l'entité CommandeEntity vers CommandeDtoOut
        CommandeDtoOut commandeDtoOut = CommandeDtoHandler.EntityToDto(commandeEntity);
        commandeDtoOut.setEtat(etat);

        return commandeDtoOut;
    }

    // Création du commande
    @Override
    public CommandeDtoIn createCommande(CommandeDtoIn commandeDto) throws UtilisateurNonExistantException {
        // Vérifier si l'utilisateur existe
        UtilisateurEntity utilisateur = utilisateurRepository.findById(commandeDto.getIdUtilisateur())
                .orElseThrow(() -> new UtilisateurNonExistantException("User not found with id: " + commandeDto.getIdUtilisateur()));

        CommandeEntity commandeEntity = CommandeDtoHandler.DtoToEntity(commandeDto);

        commandeEntity.setIdUtilisateur(utilisateur);

        CommandeEntity savedCommandeEntity = commandeRepository.save(commandeEntity);

        CommandeDtoOut savedCommandeDto = CommandeDtoHandler.EntityToDto(savedCommandeEntity);

        return commandeDto;
    }

    @Override
    public CommandeDtoIn updateCommande(int id, CommandeDtoIn commandeDto) throws CommandeNotFoundException {

        CommandeEntity existingCommande = commandeRepository.findById(id)
                .orElseThrow(() -> new CommandeNotFoundException("Commande non trouvée avec l'ID : " + id));

        // Mettre à jour les informations de la commande avec les données fournies dans le DTO
        existingCommande.setEtat(commandeDto.getEtat());
        existingCommande.setNumRueLivraison(commandeDto.getNumRueLivraison());
        existingCommande.setRueLivraison(commandeDto.getRueLivraison());
        existingCommande.setVilleLivraison(commandeDto.getVilleLivraison());
        existingCommande.setCodePostalLivraison(commandeDto.getCodePostalLivraison());
        existingCommande.setDate(commandeDto.getDate());

        commandeRepository.save(existingCommande);

        return commandeDto;
    }


    @Override
    public void deleteCommande(int id) throws CommandeNotFoundException {
        CommandeEntity commande = commandeRepository.findById(id)
                .orElseThrow(() -> new CommandeNotFoundException("Commande non trouvée avec l'ID : " + id));

        commandeRepository.delete(commande);
    }


    // Prix total du commande
    @Override
    public double getPrixTotalCommande(int id) throws CommandeNotFoundException {
        CommandeEntity commandeEntity = commandeRepository.findByIdWithItemCommandes(id)
                .orElseThrow(() -> new CommandeNotFoundException("Commande non trouvée avec l'ID : " + id));

        List<ItemCommandeEntity> itemsCommande = commandeEntity.getItemsCommande();

        // Vérification que la liste des éléments de commande n'est pas vide
        if (itemsCommande.isEmpty()) {
            throw new IllegalStateException("La liste des éléments de commande est vide pour la commande avec l'ID : " + id);
        }

        return itemsCommande.stream()
                .mapToDouble(itemCommande -> {

                    if (itemCommande.getIdProduitRevendeur() == null || itemCommande.getIdProduitRevendeur().getPrix() == null) {
                        throw new IllegalStateException("Le produit revendeur ou son prix est nul pour l'élément de commande avec l'ID : " + itemCommande.getId());
                    }

                    // Vérification des valeurs négatives
                    if (itemCommande.getQuantite() < 0 || itemCommande.getIdProduitRevendeur().getPrix().doubleValue() < 0) {
                        throw new IllegalStateException("La quantité ou le prix est négatif pour l'élément de commande avec l'ID : " + itemCommande.getId());
                    }

                    return itemCommande.getIdProduitRevendeur().getPrix().doubleValue() * itemCommande.getQuantite();
                })
                .sum();
    }


    // Ajouter un article dans une commande
    // TODO Verifier l'id , faire les controle d'integrité des donnees ,exception
    @Override
    public ItemCommandeDtoOut modifierQuantite(int id, ItemCommandeDtoIn itemCommandeDto)
            throws CommandeNotFoundException, ItemCommandeNotFoundException, ParameterException, IllegalAccessException {
        // Vérifier l'existence de la commande avec l'ID spécifié
        CommandeEntity commandeEntity = commandeRepository.findByIdWithItemCommandes(id)
                .orElseThrow(() -> new CommandeNotFoundException("La commande avec l'ID " + id + " n'a pas été trouvée."));

        // Trouver l'item de commande correspondant dans la commande
        ItemCommandeEntity itemCommandeEntity = null;
        for (ItemCommandeEntity item : commandeEntity.getItemsCommande()) {
            if (item.getId().equals(itemCommandeDto.getIdCommande())) {
                itemCommandeEntity = item;
                break;
            }
        }

        if (itemCommandeEntity == null) {
            throw new ItemCommandeNotFoundException("L'item de commande avec l'ID " + itemCommandeDto.getIdCommande() + " n'a pas été trouvé dans la commande.");
        }

        // Vérifier si la quantité est valide
        ValidationUtils.validateNonNegative(itemCommandeDto.getQuantite(), "La quantité ne peut pas être négative.");

        // Mettre à jour la quantité de l'item de commande
        itemCommandeEntity.setQuantite(itemCommandeDto.getQuantite());
        commandeRepository.save(commandeEntity);

        // Mapper l'entité mise à jour vers un DTO de sortie
        return ItemCommandeDtoHandler.EntityToDto(itemCommandeEntity);
    }

    // Ajouter un article dans une commande
    @Override
    public CommandeDtoOut ajoutArticle(int id, ProduitRevendeurDtoIn produitRevendeurDto) throws ProduitRevendeutException, CommandeNotFoundException {
        // Récupérer la commande
        CommandeEntity commandeEntity = commandeRepository.findById(id)
                .orElseThrow(() -> new CommandeNotFoundException("Commande non trouvée avec l'ID : " + id));

        ProduitRevendeurEntity produitRevendeurEntity = produitRevendeurRepository.findById(produitRevendeurDto.getIdProduit())
                .orElseThrow(() -> new ProduitRevendeutException("Produit revendeur non trouvé avec l'ID : " + produitRevendeurDto.getIdProduit()));

        // Vérifier la disponibilité du produit
        if (produitRevendeurEntity.getStock() <= 0) {
            throw new ProduitRevendeutException("Produit revendeur en rupture de stock.");
        }

        // Vérifier si le stock disponible est suffisant pour ajouter l'article à la commande
        int quantiteDemandee = 1; // On ajoute un seul article pour le moment
        if (produitRevendeurEntity.getStock() < quantiteDemandee) {
            throw new ProduitRevendeutException("Stock insuffisant pour ajouter l'article à la commande.");
        }

        // Créer et associer le nouvel article de commande
        ItemCommandeEntity itemCommandeEntity = new ItemCommandeEntity();
        itemCommandeEntity.setIdProduitRevendeur(produitRevendeurEntity);
        itemCommandeEntity.setIdCommande(commandeEntity);
        itemCommandeEntity.setQuantite(quantiteDemandee);

        // Mettre à jour la commande avec le nouvel article
        List<ItemCommandeEntity> itemsCommande = commandeEntity.getItemsCommande();
        if (itemsCommande == null) {
            itemsCommande = new ArrayList<>();
        }
        itemsCommande.add(itemCommandeEntity);
        commandeEntity.setItemsCommande(itemsCommande);

        // Décrémenter le stock du produit revendeur
        produitRevendeurEntity.setStock(produitRevendeurEntity.getStock() - quantiteDemandee);

        // Sauvegarder les modifications
        commandeRepository.save(commandeEntity);
        produitRevendeurRepository.save(produitRevendeurEntity);

        // Convertir l'entité de commande en DTO et la retourner
        return CommandeDtoHandler.EntityToDto(commandeEntity);
    }


}