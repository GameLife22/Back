package fr.sqli.formation.gamelife.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.sqli.formation.gamelife.entity.ProduitEntity;
import fr.sqli.formation.gamelife.repository.ProduitRepository;

/**
 * Produit service
 */
@Service
public class ProduitService {

	private static final Logger LOG = LogManager.getLogger();

	@Autowired
	private ProduitRepository produitRepository;

	public List<ProduitEntity> getAllProduit() {
        List<ProduitEntity> produit = new ArrayList<>();
        this.produitRepository.findAll().forEach(p -> produit.add(p));
        return produit;
    }

    /**
     * Cette méthode permet de faire une requête d'interrogation (SELECT) avec un filtre (WHERE) sur l'id du produit.
     * Exemple: SELECT * FROM gamelife.produit WHERE produit.id = 1
     * @param id: identifiant unique du jeu vidéo
     * @return un jeu vidéo
     * @author: Fabien
     */
    public ProduitEntity getProductById(String id) {
		if (id != null && !id.trim().isEmpty()) {
			var game= this.produitRepository.findById(Integer.valueOf(id));

			if(game.isPresent()) {
				LOG.debug("Le jeu vidéo Ok");
				return game.get();
			}

			LOG.debug("Object vide");
			return new ProduitEntity();
		}

		throw new IllegalArgumentException();
    }

	/**
	 * Cette méthode permet de faire une requête d'interrogation (SELECT) avec un filtre (WHERE) sur le nom du produit.
	 * Exemple: SELECT * FROM gamelife.produit WHERE produit.nom LIKE '%fi%'
	 * @param name: nom du jeu vidéo
	 * @return une liste de jeux vidéos
	 * @author Fabien
	 */
	public List<ProduitEntity> getProductsByName(String name) {
		if (name != null && !name.trim().isEmpty()) {
			var games= produitRepository.findByNomIsContaining(name);

			if (games.get().size() > 0) {
				LOG.debug("Le(s) jeu(x) vidéo(s) Ok");
				return games.get();
			}

			LOG.debug("Liste vide");
			return new ArrayList<>();
		}

		throw new IllegalArgumentException();
	}

}
