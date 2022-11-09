package fr.sqli.formation.gamelife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.sqli.formation.gamelife.entity.ProduitEntity;

@Repository
public interface ProduitRepository extends JpaRepository<ProduitEntity, Integer> {
}
