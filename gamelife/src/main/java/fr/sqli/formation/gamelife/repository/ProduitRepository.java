package fr.sqli.formation.gamelife.repository;


import fr.sqli.formation.gamelife.entity.ProduitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduitRepository extends JpaRepository<ProduitEntity,Integer> {
}
