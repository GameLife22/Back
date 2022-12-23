
package fr.sqli.formation.gamelife.controler;

import java.util.ArrayList;
import java.util.List;

import fr.sqli.formation.gamelife.dto.produit.ProduitDtoIn;
import fr.sqli.formation.gamelife.dto.produit.ProduitDtoIn2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import fr.sqli.formation.gamelife.dto.produit.ProduitDtoOut;
import fr.sqli.formation.gamelife.dto.produit.ProduitDtoHandler;
import fr.sqli.formation.gamelife.entity.ProduitEntity;
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
	@GetMapping("/all")
	public ResponseEntity<List<ProduitDtoOut>> getAllProduit() {
		LOG.info("Dans getAllProduit");
		var r = this.produitService.getAllProduit();
		var rd = new ArrayList<ProduitDtoOut>();
		for (ProduitEntity e : r) {
			rd.add(ProduitDtoHandler.fromEntity(e));
		}
		LOG.info("Sortie de getAllProduit avec {} produits", rd.size());
		return ResponseEntity.ok(rd);
	}

	/**
	 * Cette méthode permet d'appeler le service pour récupérer un produit via son nom passé en paramètre de l'url
	 * @param id: l'identifiant unique d'un jeu vidéo
	 * @author: Fabien
	 */
	@GetMapping("{id}")
	public ResponseEntity<ProduitDtoOut> getProductById(@PathVariable Integer id) throws  Exception {
		var jeuVideo = this.produitService.getProductById(id);
		ProduitDtoOut produitDto = ProduitDtoHandler.fromEntity(jeuVideo);
		return ResponseEntity.ok(produitDto);
	}

	/**
	 * Cette méthode permet d'appeler le service pour récupérer un produit via son nom passé en paramètre de l'url
	 * Exemple: http://localhost:8080/produit/search?nom=fi
	 * @param nom : jeu vidéo recherché
	 * @author Fabien
	 */
	@GetMapping("/search")

	public ResponseEntity<List<ProduitDtoOut>> getProductsByName(@RequestParam String nom) throws Exception {
		var listJeuxVideos = this.produitService.getProductsByName(nom);
		var jeuxVideos = new ArrayList<ProduitDtoOut>();

		for (ProduitEntity jeu : listJeuxVideos) {
			jeuxVideos.add(ProduitDtoHandler.fromEntity(jeu));

		}

		return ResponseEntity.ok(jeuxVideos);
	}

	@PostMapping("/create")
	public ResponseEntity<HttpStatus> ajouterNouveauProduit(@RequestBody ProduitDtoIn produitDtoIn) throws Exception {
		this.produitService.ajouterNouveauProduit(produitDtoIn);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<HttpStatus> miseAjourProduit(@RequestBody ProduitDtoIn2 produitDtoIn2) throws Exception{
		this.produitService.miseAJourProduit(produitDtoIn2);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping("/delete/{id}")
	public ResponseEntity<HttpStatus> desactiverProduit(@PathVariable Integer id) throws Exception {
		this.produitService.desactiverProduit(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
