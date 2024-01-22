package fr.sqli.formation.gamelife.repository;

import fr.sqli.formation.gamelife.entity.ProduitRevendeurEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProduitRevendeurRepository extends JpaRepository<ProduitRevendeurEntity, Integer> {
}
