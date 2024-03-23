package fr.sqli.formation.gamelife.repository;


import fr.sqli.formation.gamelife.entity.ImageEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IImageDao extends IJpaDao<ImageEntity> {
}
