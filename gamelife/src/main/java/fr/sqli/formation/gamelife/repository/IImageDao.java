package fr.sqli.formation.gamelife.repository;


import fr.sqli.formation.gamelife.dto.out.ImageDtoOut;
import fr.sqli.formation.gamelife.entity.ImageEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IImageDao extends IJpaDao<ImageEntity> {
    Optional<List<ImageEntity>> findByProduitId(UUID pId);
}
