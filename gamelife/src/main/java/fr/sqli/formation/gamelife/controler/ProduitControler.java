
package fr.sqli.formation.gamelife.controler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.sqli.formation.gamelife.dto.ProduitDto;
import fr.sqli.formation.gamelife.dto.ProduitDtoHandler;
import fr.sqli.formation.gamelife.entity.ProduitEntity;
import fr.sqli.formation.gamelife.service.ProduitService;

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

}
