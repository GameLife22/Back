package fr.sqli.formation.gamelife.dao;


import fr.sqli.formation.gamelife.entite.ImageEntite;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IImageDao extends IJpaDao<ImageEntite> {
    Optional<List<ImageEntite>> findByProduitId(UUID pIdProduit);
}
