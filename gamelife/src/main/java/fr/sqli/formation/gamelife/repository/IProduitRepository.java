package fr.sqli.formation.gamelife.repository;

import fr.sqli.formation.gamelife.entity.ProduitEntity;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface IProduitRepository extends JpaRepositoryImplementation<ProduitEntity, Integer> {
}
