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
    public Optional<List<ProduitEntity>> findByNomIsContaining(String nom);

    /**
     * Cette méthode permet de récupérer un produit à partir du nom et de la plateforme
     * @param nom
     * @param plateforme:
     * @return un produit
     * @author: Fabien
     */
    public Optional<ProduitEntity> findByNomAndPlateforme(String nom, String plateforme);
}
