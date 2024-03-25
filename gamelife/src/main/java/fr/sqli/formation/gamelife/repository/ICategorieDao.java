package fr.sqli.formation.gamelife.repository;

import fr.sqli.formation.gamelife.entity.CategorieEntity;

import java.util.Optional;

public interface ICategorieDao extends IJpaDao<CategorieEntity> {
    Optional<CategorieEntity> findByLibelle(String pLibelle);
}
