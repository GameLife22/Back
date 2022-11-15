package fr.sqli.formation.gamelife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.sqli.formation.gamelife.entity.ProduitEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProduitRepository extends JpaRepository<ProduitEntity, Integer> {

    /**
     * Cette méthode permet de rechercher un jeu vidéo
     * @param nom
     * @return une liste correspondant au nom du jeu vidéo
     * @author: Fabien
     */
    public Optional<List<ProduitEntity>> findByNomStartsWith(String nom);
}
