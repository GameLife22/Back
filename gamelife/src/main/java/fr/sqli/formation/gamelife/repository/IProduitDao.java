package fr.sqli.formation.gamelife.repository;

import fr.sqli.formation.gamelife.entity.ProduitEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProduitDao extends IJpaDao<ProduitEntity> {
}
