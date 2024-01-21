package fr.sqli.formation.gamelife.service.commande.impl;

import fr.sqli.formation.gamelife.dto.in.CommandeDtoIn;
import fr.sqli.formation.gamelife.dto.handler.CommandeDtoHandler;
import fr.sqli.formation.gamelife.entity.*;
import fr.sqli.formation.gamelife.ex.ProduitRevendeutException;
import fr.sqli.formation.gamelife.ex.UtilisateurNonExistantException;
import fr.sqli.formation.gamelife.ex.commande.ItemCommandeNotFoundException;
import fr.sqli.formation.gamelife.ex.commande.CommandeNotFoundException;
import fr.sqli.formation.gamelife.repository.ItemCommandeRepository;
import fr.sqli.formation.gamelife.repository.CommandeRepository;
import fr.sqli.formation.gamelife.repository.ProduitRepository;
import fr.sqli.formation.gamelife.repository.UtilisateurRepository;
import fr.sqli.formation.gamelife.service.commande.CommandeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommandeServiceImpl implements CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;
    @Autowired
    private ItemCommandeRepository itemCommandeRepository;
    @Autowired
    private ProduitRepository produitRepository;
    @Autowired // ModelMapper permet de transférer automatiquement les données d'un objet source vers un objet cible
    private ModelMapper modelMapper;
    @Autowired
    private CommandeDtoHandler commandeDtoHandler;

    @PersistenceContext
    private EntityManager entityManager;


    private CommandeDtoIn commandeDto;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    private ItemCommandeDtoHandler itemCommandeDtoHandler;


    // Recuperation de tous les paniers
    @Override
    public List<CommandeDtoIn> getAllCommandes() {
        List<CommandeEntity> panierEntities = commandeRepository.findAll();
        return panierEntities.stream()
                .map(commandeDtoHandler::entityToDto)
                .collect(Collectors.toList());
    }

    // Récupère un commande en utilisant un ID
    @Override
    public CommandeDtoIn getCommandeById(int id) throws CommandeNotFoundException {
        CommandeEntity commandeEntity = commandeRepository.findById(id)
                .orElseThrow(() -> new CommandeNotFoundException("Commande not found with id: " + id));
        return commandeDtoHandler.entityToDto(commandeEntity);
    }

    // Création du commande
    @Override
    public CommandeDtoIn createCommande(CommandeDtoIn commandeDto) throws UtilisateurNonExistantException {
        // Vérifiez si l'utilisateur existe avant de continuer
        UtilisateurEntity utilisateur = utilisateurRepository.findById(commandeDto.getUtilisateur().getId())
                .orElseThrow(() -> new UtilisateurNonExistantException("Utilisateur not found"));

        CommandeEntity commandeEntity = commandeDtoHandler.dtoToEntity(commandeDto);

        commandeEntity.setUtilisateur(utilisateur);
        utilisateur.addCommande(commandeEntity);

        // Ajoutez les ItemCommande à la liste du commande
        if (commandeDto.getItemCommandes() != null) {
            for (ItemCommandeDto itemCommandeDto : commandeDto.getItemCommandes()) {
                ItemCommandeEntity itemCommandeEntity = itemCommandeDtoHandler.toEntity(itemCommandeDto);
                itemCommandeEntity.setCommande(commandeEntity);

                commandeEntity.addItemCommande(itemCommandeEntity);
            }
        }
        // Enregistrez le commande dans la base de données
        commandeEntity = commandeRepository.save(commandeEntity);

        return commandeDtoHandler.entityToDto(commandeEntity);
    }



    // Commande mise à jour ex : gerer l'etat du commande, modifier la date de la livraison
    @Override
    public CommandeDtoIn updateCommande(int id, CommandeDtoIn commandeDto) throws CommandeNotFoundException {
            Optional<CommandeEntity> panierEntityOptional = commandeRepository.findById(id);
            if (panierEntityOptional.isPresent()) {
                CommandeEntity existingCommandeEntity = panierEntityOptional.get();
                // Mettre à jour les propriétés du commande existant avec celles du DTO
                existingCommandeEntity.setDate(commandeDto.getDate());
                existingCommandeEntity.setEtat(commandeDto.getEtat());
                // Enregistrez les modifications dans la base de données
                CommandeEntity updatedCommandeEntity = commandeRepository.save(existingCommandeEntity);
                // Convertissez l'entité mise à jour en DTO avant de le renvoyer
                CommandeDtoIn updatedCommandeDto = commandeDtoHandler.entityToDto(updatedCommandeEntity);
                return updatedCommandeDto;
            } else {
                throw new CommandeNotFoundException("Commande non trouvé avec l'ID : " + id);
            }
        }

    // Supprimer le commande
    @Override
    public void deleteCommande(int id) throws CommandeNotFoundException {
        CommandeEntity commandeEntity = commandeRepository.findById(id)
                .orElseThrow(() -> new CommandeNotFoundException("Commande not found with id: " + id));

        commandeRepository.delete(commandeEntity);
    }

    
    // Mettre à jour la quantité d'un article dans un commande
    @Override
    @Transactional
    public CommandeDtoIn modifierQuantite(int id, ItemCommandeDto itemCommandeDto) throws CommandeNotFoundException, ItemCommandeNotFoundException {
        CommandeEntity commandeEntity = commandeRepository.findById(id)
                .orElseThrow(() -> new CommandeNotFoundException("Commande not found with id: " + id));

        ItemCommandePKDto itemCommandePKDto = itemCommandeDto.getId();
        ItemCommandePK itemCommandePK = new ItemCommandePK();
        itemCommandePK.setIdCommande(itemCommandePKDto.getIdCommande());
        itemCommandePK.setIdProduit(itemCommandePKDto.getIdProduit());

        ItemCommandeEntity itemCommandeEntity = itemCommandeRepository.findById(itemCommandePK)
                .orElseThrow(() -> new ItemCommandeNotFoundException("ItemCommande not found with id: " + itemCommandePK));

        itemCommandeEntity.setQuantite(itemCommandeDto.getQuantite());
        itemCommandeRepository.save(itemCommandeEntity);

        return commandeDtoHandler.entityToDto(commandeEntity);
    }

    // Prix total du commande
    //TODO passage en requete
    @Override
    public double getPrixTotalCommande(int id) {
        CommandeEntity commandeEntity = commandeRepository.findById(id).orElse(null);
        if (commandeEntity != null) {
            return commandeEntity.getItemCommandes().stream()
                    .mapToDouble(itemCommande -> itemCommande.getProduit().getPrix().multiply(BigDecimal.valueOf(itemCommande.getQuantite())).doubleValue())
                    .sum();
        }
        return 0.0;
    }

    // Ajouter un article dans le commande
    @Override
    @Transactional
    public CommandeDtoIn ajoutArticle(int id, ProduitDto produitDto) throws ProduitRevendeutException, CommandeNotFoundException {
        // Vérifier si l'ID du produit est valide
        if (produitDto.getId() <= 0) {
            throw new ProduitRevendeutException("Invalid Product ID: " + produitDto.getId());
        }


        // Récupérer le commande existant
        CommandeEntity commandeEntity = commandeRepository.findById(id)
                .orElseThrow(() -> new CommandeNotFoundException("Commande not found with id: " + id));

        // Récupérer le produit à ajouter au commande
        ProduitEntity produitEntity = produitRepository.findById(produitDto.getId())
                .orElseThrow(() -> new ProduitRevendeutException("Produit not found with id: " + produitDto.getId()));


        // Rechercher l'ItemCommandeEntity dans la liste ItemCommandes du commande
        Optional<ItemCommandeEntity> existingItemCommande = commandeEntity.getItemCommandes().stream()
                .filter(itemCommande -> itemCommande.getId().getIdProduit() == produitDto.getId())
                .findFirst();

        if (existingItemCommande.isPresent()) {
            // Si l'ItemCommandeEntity existe, augmenter la quantité
            existingItemCommande.get().setQuantite(existingItemCommande.get().getQuantite() + 1);
        } else {
            // Si l'ItemCommandeEntity n'existe pas  créer un nouvel ItemCommandeEntity
            ItemCommandeEntity newItemCommande = new ItemCommandeEntity();
            ItemCommandePK itemCommandePK = new ItemCommandePK(id, produitDto.getId());
            newItemCommande.setId(itemCommandePK);
            newItemCommande.setQuantite(1);
            newItemCommande.setCommande(commandeEntity);
            newItemCommande.setProduit(produitEntity);

            commandeEntity.getItemCommandes().add(newItemCommande);
        }

        // Enregistrer les modifications dans la base de données
        commandeRepository.save(commandeEntity);

        return commandeDtoHandler.entityToDto(commandeEntity);
    }

    // Validation du commande
    @Override
    public CommandeDtoIn validerCommande(int id) throws CommandeNotFoundException {
        CommandeEntity commandeEntity = commandeRepository.findByIdWithItemCommandes(id)
                .orElseThrow(() -> new CommandeNotFoundException("Commande not found with id: " + id));

        if (commandeEntity.getEtat() != 1) {
            if (commandeEntity.getItemCommandes().isEmpty()) {
                throw new IllegalStateException("Cannot validate an empty commande (id: " + id + ")");
            }
            // Déduire le stock des produits dans le commande
            for (ItemCommandeEntity itemCommandeEntity : commandeEntity.getItemCommandes()) {
                ProduitEntity produitEntity = itemCommandeEntity.getProduit();
                int newQuantityStock = produitEntity.getStock() - itemCommandeEntity.getQuantite();

                if (newQuantityStock < 0) {
                    throw new IllegalStateException("Insufficient stock for produit with id: " + produitEntity.getId());
                }

                produitEntity.setStock(newQuantityStock);
                produitRepository.save(produitEntity);
            }
            commandeEntity.setEtat((byte) 1);

            commandeEntity = commandeRepository.save(commandeEntity);

            return commandeDtoHandler.entityToDto(commandeEntity);

        } else {
            throw new IllegalStateException("Commande with id " + id + " is already validated.");
        }
    }

    // Supprimer un article dans le commande
    @Override
    @Transactional
    public CommandeDtoIn supprimerArticle(int idCommande, int idProduit) throws CommandeNotFoundException, ProduitRevendeutException, ItemCommandeNotFoundException {
        // Récupérer le commande
        CommandeEntity commandeEntity = commandeRepository.findById(idCommande)
                .orElseThrow(() -> new CommandeNotFoundException("Commande non trouvé avec l'ID : " + idCommande));

        // Récupérer l'article du commande
        ItemCommandeEntity itemCommandeEntity = commandeEntity.getItemCommandes().stream()
                .filter(item -> item.getProduit().getId() == idProduit)
                .findFirst()
                .orElseThrow(() -> new ItemCommandeNotFoundException("Article non trouvé dans le commande avec l'ID du produit : " + idProduit));

        // Supprimer l'article du commande
        commandeEntity.removeItemCommande(itemCommandeEntity);
        commandeRepository.save(commandeEntity);

        // Supprimer l'article de la base de données
        itemCommandeRepository.delete(itemCommandeEntity);

        // Mettez à jour et renvoyez le commande DTO
        return commandeDtoHandler.entityToDto(commandeEntity);
    }
}
