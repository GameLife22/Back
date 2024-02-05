package fr.sqli.formation.gamelife.repository;

import fr.sqli.formation.gamelife.entity.ProduitEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProduitDao extends IJpaDao<ProduitEntity> {
    public Optional<ProduitEntity> findByNomAndCategorie(String pNom, String pCategorie);
}
