package fr.sqli.formation.gamelife.dao;

import fr.sqli.formation.gamelife.entite.ProduitEntite;
import org.springframework.stereotype.Repository;

@Repository
public interface IProduitDao extends IJpaDao<ProduitEntite> {
}
