package fr.sqli.formation.gamelife.service;

import java.util.ArrayList;
import java.util.List;

import fr.sqli.formation.gamelife.dto.produit.ProduitDtoHandler;
import fr.sqli.formation.gamelife.dto.produit.ProduitDtoIn;
import fr.sqli.formation.gamelife.dto.produit.ProduitDtoIn2;
import fr.sqli.formation.gamelife.ex.*;
import fr.sqli.formation.gamelife.repository.UtilisateurRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Service;

import fr.sqli.formation.gamelife.entity.ProduitEntity;
import fr.sqli.formation.gamelife.repository.ProduitRepository;

/**
 * Produit service
 */
@Service
public class ProduitService {

	private static final Logger LOG = LogManager.getLogger();
	private ProduitRepository produitRepository;
	private UtilisateurRepository utilisateurRepository;

	public ProduitService(ProduitRepository produitRepository, UtilisateurRepository utilisateurRepository) {
		this.produitRepository = produitRepository;
		this.utilisateurRepository = utilisateurRepository;
	}

	public List<ProduitEntity> getAllProduit() {
		List<ProduitEntity> produit = new ArrayList<>();
		this.produitRepository.findAll().forEach(p -> produit.add(p));
		return produit;
	}

	/**
	 * Cette méthode permet de faire une requête d'interrogation (SELECT) avec un filtre (WHERE) sur l'id du produit.
	 * Exemple: SELECT * FROM gamelife.produit WHERE produit.id = 1
	 *
	 * @param id: identifiant unique du jeu vidéo
	 * @return un jeu vidéo
	 * @author: Fabien
	 */
	public ProduitEntity getProductById(Integer id) throws Exception {
		if (id != null && id > 0) {
			var game = this.produitRepository.findById(id);

			if (game.isPresent()) {
				LOG.info("Le jeu vidéo Ok");
				return game.get();
			}

			throw new ProduitNonExistantException("le produit n'existe pas");
		}

		throw new IllegalArgumentException();
	}

	/**
	 * Cette méthode permet de faire une requête d'interrogation (SELECT) avec un filtre (WHERE) sur le nom du produit.
	 * Exemple: SELECT * FROM gamelife.produit WHERE produit.nom LIKE '%fi%'
	 *
	 * @param name: nom du jeu vidéo
	 * @return une liste de jeux vidéos
	 * @author Fabien
	 */
	public List<ProduitEntity> getProductsByName(String name) throws Exception {
		if (name != null && !name.trim().isEmpty()) {
			var games = produitRepository.findByNomIsContaining(name);

			if (games.get().size() > 0) {

				LOG.info("Le(s) jeu(x) vidéo(s) Ok");
				return games.get();
			}

			throw new ProduitsNonExistantsException("Aucun produits");
		}

		throw new IllegalArgumentException();
	}


	/**
	 * Cette méthode permet de créer un produit
	 * @param nouveauProduit
	 * @return
	 * @throws Exception
	 * @author: Fabien
	 */
	public ProduitEntity ajouterNouveauProduit(ProduitDtoIn nouveauProduit) throws Exception { 	// add authorization
		var utilisateur = this.utilisateurRepository.findById(nouveauProduit.getId_utilisateur());

		if (utilisateur.isEmpty()) {
			throw new UtilisateurNonExistantException("l'utilisateur n'existe pas");
		}

		validerNouveauProduit(nouveauProduit); 		// check si le produit est correcte

		String nomProduit = nouveauProduit.getNom(); 		// check si le produit n'existe pas
		String plateformeProduit = nouveauProduit.getPlateforme();
		var produitExiste = this.produitRepository.findByNomAndPlateforme(nomProduit, plateformeProduit);

		if (!produitExiste.isEmpty()) {
			throw new ProduitExistantException("le produit existe");
		}

		ProduitEntity produit = ProduitDtoHandler.fromDto(nouveauProduit, utilisateur);
		return this.produitRepository.save(produit);
	}

	/**
	 * Cette méthode permet de mettre à jour un produit
	 * @param produitDtoIn2
	 * @return
	 * @throws Exception
	 * @author: Fabien
	 */
	public ProduitEntity miseAJourProduit(ProduitDtoIn2 produitDtoIn2) throws Exception {
		if (produitDtoIn2 != null) {
			var utilisateur = this.utilisateurRepository.findById(produitDtoIn2.getId_utilisateur());

			if (utilisateur.isEmpty()) {
				throw new UtilisateurNonExistantException("l'utilisateur n'existe pas");
			}

			var produitExistant = this.produitRepository.findById(produitDtoIn2.getId());

			if (produitExistant.isEmpty()) {
				throw new ProduitNonExistantException("le produit n'existe pas");
			}

			ProduitEntity produitEntity = ProduitDtoHandler.fromDto2(produitDtoIn2, utilisateur);
			return this.produitRepository.save(produitEntity);
		}

		throw new IllegalArgumentException();
	}

	/**
	 * Cette méthode permet de changer l'état d'un produit
	 * @param id
	 * @return
	 * @throws Exception
	 * @author: Fabien
	 */
	public ProduitEntity desactiverProduit(Integer id) throws Exception {
		if (id != null && id > 0) {
			var produit = this.produitRepository.findById(id);

			if (produit.isEmpty()) {
				throw new ProduitNonExistantException("le produit n'existe pas");
			}

			ProduitEntity produitEntity = produit.get();
			produitEntity.setEtat(0);
			return this.produitRepository.save(produitEntity);
		}

		throw new IllegalArgumentException();
	}

	// ACTIVER UN PRODUIT

	/**
	 * Cette méthode permet de vérifier si un produit est bien valide
	 * @param produitDtoIn: un produit
	 * @author: Fabien
	 */
	private static void validerNouveauProduit(ProduitDtoIn produitDtoIn) {
		if (produitDtoIn.getNom() == null || produitDtoIn.getNom().trim().isEmpty() ||
				produitDtoIn.getDetail() == null || produitDtoIn.getDetail().trim().isEmpty() ||
				produitDtoIn.getTexteDescriptif() == null || produitDtoIn.getTexteDescriptif().trim().isEmpty() ||
				produitDtoIn.getCategorie() == null || produitDtoIn.getCategorie().trim().isEmpty() ||
				produitDtoIn.getPlateforme() == null || produitDtoIn.getPlateforme().trim().isEmpty() ||
				produitDtoIn.getPrix() == null || produitDtoIn.getPrix().doubleValue() < 0 ||
				produitDtoIn.getStock() < 0) {
			throw new IllegalArgumentException();
		}
	}

}
