package fr.sqli.formation.gamelife.service.commande.impl;

import fr.sqli.formation.gamelife.dto.ProduitDto;
import fr.sqli.formation.gamelife.dto.panier.ItemPanierDto;
import fr.sqli.formation.gamelife.dto.panier.ItemPanierDtoHandler;
import fr.sqli.formation.gamelife.dto.panier.ItemPanierPKDto;
import fr.sqli.formation.gamelife.dto.panier.PanierDto;
import fr.sqli.formation.gamelife.dto.panier.PanierDtoHandler;
import fr.sqli.formation.gamelife.entity.*;
import fr.sqli.formation.gamelife.ex.ProduitRevendeutException;
import fr.sqli.formation.gamelife.ex.UtilisateurNonExistantException;
import fr.sqli.formation.gamelife.ex.commande.ItemCommandeNotFoundException;
import fr.sqli.formation.gamelife.ex.commande.CommandeNotFoundException;
import fr.sqli.formation.gamelife.repository.ItemPanierRepository;
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
    private ItemPanierRepository itemPanierRepository;
    @Autowired
    private ProduitRepository produitRepository;
    @Autowired // ModelMapper permet de transférer automatiquement les données d'un objet source vers un objet cible
    private ModelMapper modelMapper;
    @Autowired
    private PanierDtoHandler panierDtoHandler;

    @PersistenceContext
    private EntityManager entityManager;


    private PanierDto panierDto;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    private ItemPanierDtoHandler itemPanierDtoHandler;


    // Recuperation de tous les paniers
    @Override
    public List<PanierDto> getAllPaniers() {
        List<PanierEntity> panierEntities = commandeRepository.findAll();
        return panierEntities.stream()
                .map(panierDtoHandler::entityToDto)
                .collect(Collectors.toList());
    }

    // Récupère un panier en utilisant un ID
    @Override
    public PanierDto getPanierById(int id) throws CommandeNotFoundException {
        PanierEntity panierEntity = commandeRepository.findById(id)
                .orElseThrow(() -> new CommandeNotFoundException("Panier not found with id: " + id));
        return panierDtoHandler.entityToDto(panierEntity);
    }

    // Création du panier
    @Override
    public PanierDto createPanier(PanierDto panierDto) throws UtilisateurNonExistantException {
        // Vérifiez si l'utilisateur existe avant de continuer
        UtilisateurEntity utilisateur = utilisateurRepository.findById(panierDto.getUtilisateur().getId())
                .orElseThrow(() -> new UtilisateurNonExistantException("Utilisateur not found"));

        PanierEntity panierEntity = panierDtoHandler.dtoToEntity(panierDto);

        panierEntity.setUtilisateur(utilisateur);
        utilisateur.addCommande(panierEntity);

        // Ajoutez les ItemPanier à la liste du panier
        if (panierDto.getItemPaniers() != null) {
            for (ItemPanierDto itemPanierDto : panierDto.getItemPaniers()) {
                ItemPanierEntity itemPanierEntity = itemPanierDtoHandler.toEntity(itemPanierDto);
                itemPanierEntity.setPanier(panierEntity);

                panierEntity.addItemPanier(itemPanierEntity);
            }
        }
        // Enregistrez le panier dans la base de données
        panierEntity = commandeRepository.save(panierEntity);

        return panierDtoHandler.entityToDto(panierEntity);
    }



    // Panier mise à jour ex : gerer l'etat du panier, modifier la date de la livraison
    @Override
    public PanierDto updatePanier(int id, PanierDto panierDto) throws CommandeNotFoundException {
            Optional<PanierEntity> panierEntityOptional = commandeRepository.findById(id);
            if (panierEntityOptional.isPresent()) {
                PanierEntity existingPanierEntity = panierEntityOptional.get();
                // Mettre à jour les propriétés du panier existant avec celles du DTO
                existingPanierEntity.setDate(panierDto.getDate());
                existingPanierEntity.setEtat(panierDto.getEtat());
                // Enregistrez les modifications dans la base de données
                PanierEntity updatedPanierEntity = commandeRepository.save(existingPanierEntity);
                // Convertissez l'entité mise à jour en DTO avant de le renvoyer
                PanierDto updatedPanierDto = panierDtoHandler.entityToDto(updatedPanierEntity);
                return updatedPanierDto;
            } else {
                throw new CommandeNotFoundException("Panier non trouvé avec l'ID : " + id);
            }
        }

    // Supprimer le panier
    @Override
    public void deletePanier(int id) throws CommandeNotFoundException {
        PanierEntity panierEntity = commandeRepository.findById(id)
                .orElseThrow(() -> new CommandeNotFoundException("Panier not found with id: " + id));

        commandeRepository.delete(panierEntity);
    }

    
    // Mettre à jour la quantité d'un article dans un panier
    @Override
    @Transactional
    public PanierDto modifierQuantite(int id, ItemPanierDto itemPanierDto) throws CommandeNotFoundException, ItemCommandeNotFoundException {
        PanierEntity panierEntity = commandeRepository.findById(id)
                .orElseThrow(() -> new CommandeNotFoundException("Panier not found with id: " + id));

        ItemPanierPKDto itemPanierPKDto = itemPanierDto.getId();
        ItemPanierPK itemPanierPK = new ItemPanierPK();
        itemPanierPK.setIdPanier(itemPanierPKDto.getIdPanier());
        itemPanierPK.setIdProduit(itemPanierPKDto.getIdProduit());

        ItemPanierEntity itemPanierEntity = itemPanierRepository.findById(itemPanierPK)
                .orElseThrow(() -> new ItemCommandeNotFoundException("ItemPanier not found with id: " + itemPanierPK));

        itemPanierEntity.setQuantite(itemPanierDto.getQuantite());
        itemPanierRepository.save(itemPanierEntity);

        return panierDtoHandler.entityToDto(panierEntity);
    }

    // Prix total du panier
    //TODO passage en requete
    @Override
    public double getPrixTotalPanier(int id) {
        PanierEntity panierEntity = commandeRepository.findById(id).orElse(null);
        if (panierEntity != null) {
            return panierEntity.getItemPaniers().stream()
                    .mapToDouble(itemPanier -> itemPanier.getProduit().getPrix().multiply(BigDecimal.valueOf(itemPanier.getQuantite())).doubleValue())
                    .sum();
        }
        return 0.0;
    }

    // Ajouter un article dans le panier
    @Override
    @Transactional
    public PanierDto ajoutArticle(int id, ProduitDto produitDto) throws ProduitRevendeutException, CommandeNotFoundException {
        // Vérifier si l'ID du produit est valide
        if (produitDto.getId() <= 0) {
            throw new ProduitRevendeutException("Invalid Product ID: " + produitDto.getId());
        }


        // Récupérer le panier existant
        PanierEntity panierEntity = commandeRepository.findById(id)
                .orElseThrow(() -> new CommandeNotFoundException("Panier not found with id: " + id));

        // Récupérer le produit à ajouter au panier
        ProduitEntity produitEntity = produitRepository.findById(produitDto.getId())
                .orElseThrow(() -> new ProduitRevendeutException("Produit not found with id: " + produitDto.getId()));


        // Rechercher l'ItemPanierEntity dans la liste ItemPaniers du panier
        Optional<ItemPanierEntity> existingItemPanier = panierEntity.getItemPaniers().stream()
                .filter(itemPanier -> itemPanier.getId().getIdProduit() == produitDto.getId())
                .findFirst();

        if (existingItemPanier.isPresent()) {
            // Si l'ItemPanierEntity existe, augmenter la quantité
            existingItemPanier.get().setQuantite(existingItemPanier.get().getQuantite() + 1);
        } else {
            // Si l'ItemPanierEntity n'existe pas  créer un nouvel ItemPanierEntity
            ItemPanierEntity newItemPanier = new ItemPanierEntity();
            ItemPanierPK itemPanierPK = new ItemPanierPK(id, produitDto.getId());
            newItemPanier.setId(itemPanierPK);
            newItemPanier.setQuantite(1);
            newItemPanier.setPanier(panierEntity);
            newItemPanier.setProduit(produitEntity);

            panierEntity.getItemPaniers().add(newItemPanier);
        }

        // Enregistrer les modifications dans la base de données
        commandeRepository.save(panierEntity);

        return panierDtoHandler.entityToDto(panierEntity);
    }

    // Validation du panier
    @Override
    public PanierDto validerPanier(int id) throws CommandeNotFoundException {
        PanierEntity panierEntity = commandeRepository.findByIdWithItemPaniers(id)
                .orElseThrow(() -> new CommandeNotFoundException("Panier not found with id: " + id));

        if (panierEntity.getEtat() != 1) {
            if (panierEntity.getItemPaniers().isEmpty()) {
                throw new IllegalStateException("Cannot validate an empty panier (id: " + id + ")");
            }
            // Déduire le stock des produits dans le panier
            for (ItemPanierEntity itemPanierEntity : panierEntity.getItemPaniers()) {
                ProduitEntity produitEntity = itemPanierEntity.getProduit();
                int newQuantityStock = produitEntity.getStock() - itemPanierEntity.getQuantite();

                if (newQuantityStock < 0) {
                    throw new IllegalStateException("Insufficient stock for produit with id: " + produitEntity.getId());
                }

                produitEntity.setStock(newQuantityStock);
                produitRepository.save(produitEntity);
            }
            panierEntity.setEtat((byte) 1);

            panierEntity = commandeRepository.save(panierEntity);

            return panierDtoHandler.entityToDto(panierEntity);

        } else {
            throw new IllegalStateException("Panier with id " + id + " is already validated.");
        }
    }

    // Supprimer un article dans le panier
    @Override
    @Transactional
    public PanierDto supprimerArticle(int idPanier, int idProduit) throws CommandeNotFoundException, ProduitRevendeutException, ItemCommandeNotFoundException {
        // Récupérer le panier
        PanierEntity panierEntity = commandeRepository.findById(idPanier)
                .orElseThrow(() -> new CommandeNotFoundException("Panier non trouvé avec l'ID : " + idPanier));

        // Récupérer l'article du panier
        ItemPanierEntity itemPanierEntity = panierEntity.getItemPaniers().stream()
                .filter(item -> item.getProduit().getId() == idProduit)
                .findFirst()
                .orElseThrow(() -> new ItemCommandeNotFoundException("Article non trouvé dans le panier avec l'ID du produit : " + idProduit));

        // Supprimer l'article du panier
        panierEntity.removeItemPanier(itemPanierEntity);
        commandeRepository.save(panierEntity);

        // Supprimer l'article de la base de données
        itemPanierRepository.delete(itemPanierEntity);

        // Mettez à jour et renvoyez le panier DTO
        return panierDtoHandler.entityToDto(panierEntity);
    }
}