package fr.sqli.formation.gamelife.repository;


import fr.sqli.formation.gamelife.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity,Integer> {
}
