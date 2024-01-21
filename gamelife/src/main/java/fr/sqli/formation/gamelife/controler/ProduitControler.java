
package fr.sqli.formation.gamelife.controler;

import java.util.ArrayList;
import java.util.List;

import fr.sqli.formation.gamelife.entity.ProduitEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fr.sqli.formation.gamelife.service.ProduitService;

/**
 * Produit Controller
 */
@RestController
@RequestMapping("/produit")
public class ProduitControler {

	private static final Logger LOG = LogManager.getLogger();
	
	@Autowired
	private ProduitService produitService;

	/**
	 * Cette méthode permet d'appeler le service pour récupérer des produits
	 * @return: une liste de jeux vidéos
	 */

	//@PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
	@GetMapping("/all")
	public ResponseEntity<List<ProduitDto>> getAllProduit() {
		LOG.info("Dans getAllProduit");
		var r = this.produitService.getAllProduit();
		var rd = new ArrayList<ProduitDto>();
		for (ProduitEntity e : r) {
			rd.add(ProduitDtoHandler.fromEntity(e));
		}
		LOG.info("Sortie de getAllProduit avec {} produits", rd.size());
		return ResponseEntity.ok(rd);
	}

	/**
	 * Cette méthode permet d'appeler le service pour récupérer un produit via son nom passé en paramètre de l'url
	 * @param id: l'identifiant unique d'un jeu vidéo
	 * @return HTTP Status + Produit DTO
	 * @author: Fabien
	 */
	@GetMapping("{id}")
	public ResponseEntity<ProduitDto> getProductById(@PathVariable String id) {
		var jeuVideo = this.produitService.getProductById(id);

		if (jeuVideo.getId() == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

		ProduitDto produitDto = ProduitDtoHandler.fromEntity(jeuVideo);

		return ResponseEntity.ok(produitDto);
	}

	/**
	 * Cette méthode permet d'appeler le service pour récupérer un produit via son nom passé en paramètre de l'url
	 * Exemple: http://localhost:8080/produit/search?nom=fi
	 * @param nom : jeu vidéo recherché
	 * @return HTTP Status + List<ProduitDTO>
	 * @throws Exception
	 * @author Fabien
	 */
	@GetMapping("/search")

	public ResponseEntity<List<ProduitDto>> getProductsByName(@RequestParam String nom) {
		var listJeuxVideos = this.produitService.getProductsByName(nom);

		if(listJeuxVideos.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<ProduitDto>());
		}

		var jeuxVideos = new ArrayList<ProduitDto>();

		for (ProduitEntity jeu : listJeuxVideos) {
			jeuxVideos.add(ProduitDtoHandler.fromEntity(jeu));

		}

		return ResponseEntity.ok(jeuxVideos);
	}
}
