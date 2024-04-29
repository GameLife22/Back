package fr.sqli.formation.gamelife.dao;

import fr.sqli.formation.gamelife.entite.CategorieEntite;

import java.util.Optional;

public interface ICategorieDao extends IJpaDao<CategorieEntite> {
    Optional<CategorieEntite> findByLibelle(String pLibelle);
}
