package fr.sqli.formation.gamelife.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.sqli.formation.gamelife.entity.ProduitEntity;
import fr.sqli.formation.gamelife.repository.ProduitRepository;

@Service
public class ProduitService {

	@Autowired
	private ProduitRepository produitRepository;

	public List<ProduitEntity> getAllProduit() {
		List<ProduitEntity> produit = new ArrayList<>();
		this.produitRepository.findAll().forEach(p -> produit.add(p));
		return produit;
	}

}
