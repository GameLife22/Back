package fr.sqli.formation.gamelife.service.panier.impl;

import fr.sqli.formation.gamelife.dto.ProduitDto;
import fr.sqli.formation.gamelife.dto.panier.ItemPanierDto;
import fr.sqli.formation.gamelife.dto.panier.ItemPanierPKDto;
import fr.sqli.formation.gamelife.dto.panier.PanierDto;
import fr.sqli.formation.gamelife.dto.panier.PanierDtoHandler;
import fr.sqli.formation.gamelife.dto.utilisateur.UtilisateurDtoHandler;
import fr.sqli.formation.gamelife.entity.*;
import fr.sqli.formation.gamelife.ex.ProduitException;
import fr.sqli.formation.gamelife.ex.UtilisateurNonExistantException;
import fr.sqli.formation.gamelife.ex.panier.ItemPanierNotFoundException;
import fr.sqli.formation.gamelife.ex.panier.PanierNotFoundException;
import fr.sqli.formation.gamelife.repository.ItemPanierRepository;
import fr.sqli.formation.gamelife.repository.PanierRepository;
import fr.sqli.formation.gamelife.repository.ProduitRepository;
import fr.sqli.formation.gamelife.repository.UtilisateurRepository;
import fr.sqli.formation.gamelife.service.panier.PanierService;
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
public class PanierServiceImpl implements PanierService {


    @Autowired
    private PanierRepository panierRepository;
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


    //Recuperation de tous les paniers
    @Override
    public List<PanierDto> getAllPaniers() {
        List<PanierEntity> panierEntities = panierRepository.findAll();
        return panierEntities.stream()
                .map(panierDtoHandler::entityToDto)
                .collect(Collectors.toList());
    }

    //Récupère un panier en utilisant un ID
    @Override
    public PanierDto getPanierById(int id) throws PanierNotFoundException {
        PanierEntity panierEntity = panierRepository.findById(id)
                .orElseThrow(() -> new PanierNotFoundException("Panier not found with id: " + id));
        return panierDtoHandler.entityToDto(panierEntity);
    }

    //Creation du panier

    @Override
    public PanierDto createPanier(PanierDto panierDto) throws UtilisateurNonExistantException {
        PanierEntity panierEntity = panierDtoHandler.dtoToEntity(panierDto);

        // Récupérez l'utilisateur associé au panier
        UtilisateurEntity utilisateur = UtilisateurDtoHandler.fromDto(panierDto.getUtilisateur());
        panierEntity.setUtilisateur(utilisateur);

        // Enregistrez le panier dans la base de données
        panierEntity = panierRepository.save(panierEntity);
        return panierDtoHandler.entityToDto(panierEntity);
    }

    // Panier mise à jour
    @Override
    public PanierDto updatePanier(int id, PanierDto panierDto) throws PanierNotFoundException {
            Optional<PanierEntity> panierEntityOptional = panierRepository.findById(id);
            if (panierEntityOptional.isPresent()) {
                PanierEntity existingPanierEntity = panierEntityOptional.get();
                // Mettre à jour les propriétés du panier existant avec celles du DTO
                existingPanierEntity.setDate(panierDto.getDate());
                existingPanierEntity.setEtat(panierDto.getEtat());
                // Enregistrez les modifications dans la base de données
                PanierEntity updatedPanierEntity = panierRepository.save(existingPanierEntity);
                // Convertissez l'entité mise à jour en DTO avant de le renvoyer
                PanierDto updatedPanierDto = panierDtoHandler.entityToDto(updatedPanierEntity);
                return updatedPanierDto;
            } else {
                throw new PanierNotFoundException("Panier non trouvé avec l'ID : " + id);
            }
        }
    //Supprimer le panier
    @Override
    public void deletePanier(int id) throws PanierNotFoundException {
        PanierEntity panierEntity = panierRepository.findById(id)
                .orElseThrow(() -> new PanierNotFoundException("Panier not found with id: " + id));

        panierRepository.delete(panierEntity);
    }

    //Mettre à jour la quantité d'un article dans un panie
    @Override
    @Transactional
    public PanierDto modifierQuantite(int id, ItemPanierDto itemPanierDto) throws PanierNotFoundException, ItemPanierNotFoundException {
        PanierEntity panierEntity = panierRepository.findById(id)
                .orElseThrow(() -> new PanierNotFoundException("Panier not found with id: " + id));

        ItemPanierPKDto itemPanierPKDto = itemPanierDto.getId();
        ItemPanierPK itemPanierPK = new ItemPanierPK();
        itemPanierPK.setIdPanier(itemPanierPKDto.getIdPanier());
        itemPanierPK.setIdProduit(itemPanierPKDto.getIdProduit());

        ItemPanierEntity itemPanierEntity = itemPanierRepository.findById(itemPanierPK)
                .orElseThrow(() -> new ItemPanierNotFoundException("ItemPanier not found with id: " + itemPanierPK));

        itemPanierEntity.setQuantite(itemPanierDto.getQuantite());
        itemPanierRepository.save(itemPanierEntity);

        return panierDtoHandler.entityToDto(panierEntity);
    }

    @Override
    public double getPrixTotalPanier(int id) {
        PanierEntity panierEntity = panierRepository.findById(id).orElse(null);
        if (panierEntity != null) {
            return panierEntity.getItemPaniers().stream()
                    .mapToDouble(itemPanier -> itemPanier.getProduit().getPrix().multiply(BigDecimal.valueOf(itemPanier.getQuantite())).doubleValue())
                    .sum();
        }
        return 0.0;
    }

    @Override
    @Transactional
    public PanierDto ajoutArticle(int id, ProduitDto produitDto) throws ProduitException, PanierNotFoundException {
        // Vérifier si l'ID du produit est valide
        if (produitDto.getId() <= 0) {
            throw new ProduitException("Invalid Product ID: " + produitDto.getId());
        }

        // Récupérer le panier existant
        PanierEntity panierEntity = panierRepository.findById(id)
                .orElseThrow(() -> new PanierNotFoundException("Panier not found with id: " + id));

        // Récupérer le produit à ajouter au panier
        ProduitEntity produitEntity = produitRepository.findById(produitDto.getId())
                .orElseThrow(() -> new ProduitException("Produit not found with id: " + produitDto.getId()));

        // Rechercher l'ItemPanierEntity dans la liste ItemPaniers du panier
        Optional<ItemPanierEntity> existingItemPanier = panierEntity.getItemPaniers().stream()
                .filter(itemPanier -> itemPanier.getId().getIdProduit() == produitDto.getId())
                .findFirst();

        if (existingItemPanier.isPresent()) {
            // Si l'ItemPanierEntity existe, augmenter la quantité
            existingItemPanier.get().setQuantite(existingItemPanier.get().getQuantite() + 1);
        } else {
            // Si l'ItemPanierEntity n'existe pas, créer un nouvel ItemPanierEntity
            ItemPanierEntity newItemPanier = new ItemPanierEntity();
            ItemPanierPK itemPanierPK = new ItemPanierPK(id, produitDto.getId());
            newItemPanier.setId(itemPanierPK);
            newItemPanier.setQuantite(1);
            newItemPanier.setPanier(panierEntity);
            newItemPanier.setProduit(produitEntity);

            panierEntity.getItemPaniers().add(newItemPanier);
        }

        // Enregistrer les modifications dans la base de données
        panierRepository.save(panierEntity);

        // Retourner le panier mis à jour sous forme de DTO
        return panierDtoHandler.entityToDto(panierEntity);
    }





}
