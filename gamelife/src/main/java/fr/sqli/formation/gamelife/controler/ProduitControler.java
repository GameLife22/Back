
package fr.sqli.formation.gamelife.controler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fr.sqli.formation.gamelife.dto.ProduitDto;
import fr.sqli.formation.gamelife.dto.ProduitDtoHandler;
import fr.sqli.formation.gamelife.entity.ProduitEntity;
import fr.sqli.formation.gamelife.service.ProduitService;

/**
 * Produit Controller
 */
@RestController
@RequestMapping("/produit")
@CrossOrigin("*")
public class ProduitControler {

	@Autowired
	private ProduitService produitService;

	@GetMapping("/all")
	public ResponseEntity<List<ProduitDto>> getAllProduit() {
		var r = this.produitService.getAllProduit();
		var rd = new ArrayList<ProduitDto>();
		for (ProduitEntity e : r) {
			rd.add(ProduitDtoHandler.fromEntity(e));
		}
		return ResponseEntity.ok(rd);
	}

	/**
	 * Cette méthode permet d'appeler le service pour récupérer un produit via son nom passé en paramètre de l'url
	 * Exemple: http://localhost:8080/produit/search?nom=fi
	 * @param nom
	 * @return
	 * @throws Exception
	 * @author Fabien
	 */
	@GetMapping("/search")
	public ResponseEntity<List<ProduitDto>> getProductsByName(@RequestParam String nom) throws Exception {
		var listJeuVideos = this.produitService.getProductsByName(nom);
		var jeuxVideos = new ArrayList<ProduitDto>();
		for (ProduitEntity e : listJeuVideos) {
			jeuxVideos.add(ProduitDtoHandler.fromEntity(e));
		}
		return ResponseEntity.ok(jeuxVideos);
	}
}
