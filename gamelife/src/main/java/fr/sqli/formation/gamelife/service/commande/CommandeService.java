package fr.sqli.formation.gamelife.service.commande;

import fr.sqli.formation.gamelife.convertisseur.ItemCommandeConvertisseur;
import fr.sqli.formation.gamelife.dto.commande.CommandeRequete;
import fr.sqli.formation.gamelife.convertisseur.ICommandeConvertisseur;
import fr.sqli.formation.gamelife.dto.commande.ItemCommandeRequete;
import fr.sqli.formation.gamelife.dto.commande.CommandeReponse;
import fr.sqli.formation.gamelife.dto.commande.ItemCommandeReponse;
import fr.sqli.formation.gamelife.entite.*;
import fr.sqli.formation.gamelife.enumeration.EtatCommande;
import fr.sqli.formation.gamelife.exception.commande.EtatCommandeInvalideException;
import fr.sqli.formation.gamelife.exception.ParameterException;
import fr.sqli.formation.gamelife.exception.ProduitRevendeurException;
import fr.sqli.formation.gamelife.exception.utilisateur.UtilisateurNonExistantException;
import fr.sqli.formation.gamelife.exception.commande.ItemCommandeNotFoundException;
import fr.sqli.formation.gamelife.exception.commande.CommandeNotFoundException;
import fr.sqli.formation.gamelife.dao.*;
import fr.sqli.formation.gamelife.utilitaire.ValidationUtilitaire;
import fr.sqli.formation.gamelife.validateur.CommandeValidateur;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommandeService implements ICommandeService {

    @Autowired
    private ICommandeDao iCommandeDao;
    @Autowired
    private ItemCommandeDao itemCommandeDAO;
    @Autowired
    private IProduitDao produitRepository;

    @Autowired
    private ICommandeConvertisseur ICommandeConvertisseur;

    @PersistenceContext
    private EntityManager entityManager;


    private CommandeRequete commandeDto;
    @Autowired
    private IUtilisateurDao IUtilisateurDao;
    private ItemCommandeConvertisseur itemCommandeConvertisseur;
    @Autowired
    private IProduitRevendeurDao iProduitRevendeurDao;

    @Autowired
    private CommandeValidateur commandeValidateur;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandeService.class);

    @Autowired
    public CommandeService(ICommandeConvertisseur pICommandeConvertisseur) {
        this.ICommandeConvertisseur = pICommandeConvertisseur;
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
    public List<CommandeReponse> getAllCommandes() {
        return null;
    }

    public CommandeReponse getCommande(UUID id) throws CommandeNotFoundException {
        CommandeEntite commandeEntite = iCommandeDao.findById(id)
                .orElseThrow(() -> new CommandeNotFoundException("Commande non trouvée avec l'ID : " + id));

        // Récupérer l'état de la commande depuis l'entité CommandeEntity
        EtatCommande etat = commandeEntite.getEtat();

        // Mapper l'entité CommandeEntity vers CommandeDtoOut
        CommandeReponse commandeReponse = ICommandeConvertisseur.EntityToDto(commandeEntite);
        commandeReponse.setEtat(etat);

        return commandeReponse;
    }

    // Création du commande
    @Override
    public CommandeRequete creerCommande(CommandeRequete commandeDto) throws UtilisateurNonExistantException {
        // Vérifier si l'utilisateur existe
        UtilisateurEntite utilisateur = IUtilisateurDao.findById(commandeDto.getIdUtilisateur())
                .orElseThrow(() -> new UtilisateurNonExistantException("User not found with id: " + commandeDto.getIdUtilisateur()));

        CommandeEntite commandeEntite = ICommandeConvertisseur.DtoToEntity(commandeDto);

        commandeEntite.setEtat(EtatCommande.NOUVELLE);


        commandeEntite.setUtilisateur(utilisateur);

        CommandeEntite savedCommandeEntite = iCommandeDao.save(commandeEntite);

        CommandeReponse savedCommandeDto = ICommandeConvertisseur.EntityToDto(savedCommandeEntite);

        return commandeDto;
    }


    @Override
    public CommandeRequete modifierCommande(UUID id, CommandeRequete commandeDto) throws CommandeNotFoundException {

        CommandeEntite existingCommande = iCommandeDao.findById(id)
                .orElseThrow(() -> new CommandeNotFoundException("Commande non trouvée avec l'ID : " + id));

        // Mettre à jour les informations de la commande avec les données fournies dans le DTO
        existingCommande.setEtat(commandeDto.getEtat());
        existingCommande.setNumRueLivraison(commandeDto.getNumRueLivraison());
        existingCommande.setRueLivraison(commandeDto.getRueLivraison());
        existingCommande.setVilleLivraison(commandeDto.getVilleLivraison());
        existingCommande.setCodePostalLivraison(commandeDto.getCodePostalLivraison());
        existingCommande.setDate(commandeDto.getDate());

        iCommandeDao.save(existingCommande);

        return commandeDto;
    }


    @Override
    public void deleteCommande(UUID id) throws CommandeNotFoundException {
        CommandeEntite commande = iCommandeDao.findById(id)
                .orElseThrow(() -> new CommandeNotFoundException("Commande non trouvée avec l'ID : " + id));

        iCommandeDao.delete(commande);
    }


    // Prix total du commande
    @Override
    public double getPrixTotalCommande(UUID id) throws CommandeNotFoundException {
        CommandeEntite commandeEntite = iCommandeDao.findByIdWithItemCommandes(id)
                .orElseThrow(() -> new CommandeNotFoundException("Commande non trouvée avec l'ID : " + id));

        List<ItemCommandeEntite> itemsCommande = commandeEntite.getItemsCommande();

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
    public ItemCommandeReponse modifierQuantite(UUID id, ItemCommandeRequete itemCommandeDto)
            throws CommandeNotFoundException, ItemCommandeNotFoundException, ParameterException, IllegalAccessException {
        // Vérifier l'existence de la commande avec l'ID spécifié
        CommandeEntite commandeEntite = iCommandeDao.findByIdWithItemCommandes(id)
                .orElseThrow(() -> new CommandeNotFoundException("La commande avec l'ID " + id + " n'a pas été trouvée."));

        // Trouver l'item de commande correspondant dans la commande
        ItemCommandeEntite itemCommandeEntite = null;
        for (ItemCommandeEntite item : commandeEntite.getItemsCommande()) {
            if (item.getId().equals(itemCommandeDto.getIdCommande())) {
                itemCommandeEntite = item;
                break;
            }
        }

        if (itemCommandeEntite == null) {
            throw new ItemCommandeNotFoundException("L'item de commande avec l'ID " + itemCommandeDto.getIdCommande() + " n'a pas été trouvé dans la commande.");
        }

        // Vérifier si la quantité est valide
        ValidationUtilitaire.validateNonNegative(itemCommandeDto.getQuantite(), "La quantité ne peut pas être négative.");

        // Mettre à jour la quantité de l'item de commande
        itemCommandeEntite.setQuantite(itemCommandeDto.getQuantite());
        iCommandeDao.save(commandeEntite);

        // Mapper l'entité mise à jour vers un DTO de sortie
        return ItemCommandeConvertisseur.EntityToDto(itemCommandeEntite);
    }


    // Ajouter un produit dans une commande existante (id) sinon cree une nouvelle commande verifier si l'utilisateur existe
    @Override
    public ItemCommandeReponse ajoutProduit(UUID pIdUtilisateur, ItemCommandeRequete pItemCommandeRequete) throws ProduitRevendeurException, ParameterException, CommandeNotFoundException, EtatCommandeInvalideException {
        // Chercher la commande de l'utilisateur
        CommandeEntite commandeEntite = iCommandeDao.findByUtilisateurId(pIdUtilisateur)
                .orElseThrow(() -> new CommandeNotFoundException("Commande non trouvée avec l'identifiant : " + pIdUtilisateur));

        // Vérifier si la commande est dans un état permettant l'ajout de produit
        if (commandeEntite.getEtat() == EtatCommande.EN_COURS_DE_TRAITEMENT) {
            throw new EtatCommandeInvalideException("Impossible d'ajouter un produit à une commande en cours de traitement.");
        }

        // Vérifier si le produit revendeur existe
        Optional<ProduitRevendeurEntite> produitRevendeurOptional = iProduitRevendeurDao.findById(pItemCommandeRequete.getIdProduitRevendeur());

        if (produitRevendeurOptional.isEmpty()) {
            throw new ProduitRevendeurException("Produit revendeur non trouvé avec l'identifiant : " + pItemCommandeRequete.getIdProduitRevendeur());
        }

        ProduitRevendeurEntite produitRevendeurEntite = produitRevendeurOptional.get();

        // Vérifier si la quantité est valide
        if (pItemCommandeRequete.getQuantite() <= 0) {
            throw new ParameterException("La quantité doit être supérieure à zéro.");
        }

        // Vérifier si le stock est suffisant
        if (produitRevendeurEntite.getStock() < pItemCommandeRequete.getQuantite()) {
            throw new ProduitRevendeurException("Stock insuffisant pour le produit revendeur avec l'identifiant : " + produitRevendeurEntite.getId());
        }


        // Créer un nouvel item de commande
        ItemCommandeEntite itemCommandeEntite = new ItemCommandeEntite();
        itemCommandeEntite.setCommande(commandeEntite);
        itemCommandeEntite.setProduitRevendeur(produitRevendeurEntite);
        itemCommandeEntite.setQuantite(pItemCommandeRequete.getQuantite());

        // Décrémenter le stock du produit revendeur
        produitRevendeurEntite.setStock(produitRevendeurEntite.getStock() - pItemCommandeRequete.getQuantite());


        // Enregistrer l'item de commande et mettre à jour le produit revendeur
        itemCommandeEntite = itemCommandeDAO.save(itemCommandeEntite);
        produitRevendeurEntite = iProduitRevendeurDao.save(produitRevendeurEntite);

        // Mettre à jour la liste des items de commande de la commande
        commandeEntite.getItemsCommande().add(itemCommandeEntite);
        var commandeEntite1 = iCommandeDao.save(commandeEntite);

        // Convertir l'item de commande en DTO de sortie
        return ItemCommandeConvertisseur.EntityToDto(itemCommandeEntite);
    }


    // Valider une commande, changer son état en EN_COURS_DE_TRAITEMENT
    @Override
    public CommandeReponse validerCommande(UUID id) throws CommandeNotFoundException {
        CommandeEntite commandeEntite = iCommandeDao.findById(id)
                .orElseThrow(() -> new CommandeNotFoundException("Commande non trouvée avec l'ID : " + id));

        // Changer l'état de la commande
        commandeEntite.setEtat(EtatCommande.EN_COURS_DE_TRAITEMENT);
        iCommandeDao.save(commandeEntite);


        return ICommandeConvertisseur.EntityToDto(commandeEntite);
    }


    // Supprimer un article dans une commande existante
    @Override
    public CommandeRequete supprimerProduit(UUID idCommande, UUID idProduit) throws CommandeNotFoundException, ItemCommandeNotFoundException, EtatCommandeInvalideException {
        // Chercher la commande
        CommandeEntite commandeEntite = iCommandeDao.findById(idCommande)
                .orElseThrow(() -> new CommandeNotFoundException("Commande non trouvée avec l'ID : " + idCommande));

        // Vérifier si la commande est dans un état permettant la suppression d'article
        if (commandeEntite.getEtat() == EtatCommande.EN_COURS_DE_TRAITEMENT) {
            throw new EtatCommandeInvalideException("Impossible de supprimer un article d'une commande en cours de traitement.");
        }

        // Chercher l'item de commande
        ItemCommandeEntite itemCommandeEntite = null;
        for (ItemCommandeEntite item : commandeEntite.getItemsCommande()) {
            if (item.recupererProduitRevendeur().getId() == idProduit) {
                itemCommandeEntite = item;
                break;
            }
        }

        if (itemCommandeEntite == null) {
            throw new ItemCommandeNotFoundException("L'item de commande avec l'ID " + idProduit + " n'a pas été trouvé dans la commande.");
        }

        // Récupérer le produit revendeur
        ProduitRevendeurEntite produitRevendeurEntite = itemCommandeEntite.recupererProduitRevendeur();

        // Supprimer l'item de commande
        commandeEntite.getItemsCommande().remove(itemCommandeEntite);
        itemCommandeDAO.delete(itemCommandeEntite);

        // Incrémenter le stock du produit revendeur
        produitRevendeurEntite.setStock(produitRevendeurEntite.getStock() + itemCommandeEntite.getQuantite());
        iProduitRevendeurDao.save(produitRevendeurEntite);

        // Mettre à jour la commande
        iCommandeDao.save(commandeEntite);

        return commandeDto;
    }
}