package fr.sqli.formation.gamelife.service;

import java.util.ArrayList;
import java.util.List;

import fr.sqli.formation.gamelife.ex.ProduitException;
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
	 * Cette méthode permet de faire une requête d'interrogation (SELECT) avec un filtre (LIKE).
	 * Exemple: SELECT * FROM gamelife.produit WHERE produit.nom LIKE '%fi%'
	 * @param name
	 * @return une liste de jeux vidéos
	 * @author Fabien
	 */
	public List<ProduitEntity> getProductsByName(String name) throws Exception {
		if (name != null && !name.trim().isEmpty()) {
			var jeuxVideos= produitRepository.findByNomIsContaining(name);
			if (jeuxVideos.get().size() > 0) {
				LOG.debug("Le(s) jeu(x) vidéo(s) Ok");
				return jeuxVideos.get();
			}

			LOG.debug("Liste vide");
			return new ArrayList<>();
		}

		throw new IllegalArgumentException();
	}

}
