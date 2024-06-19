package fr.sqli.formation.gamelife.repository;

import fr.sqli.formation.gamelife.entity.SellerGameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface ISellerGameRepository extends JpaRepository<SellerGameEntity, UUID> {
    public Optional<SellerGameEntity> findById(UUID pUUID);

    @Query("SELECT pr FROM SellerGameEntity pr WHERE pr.utilisateur.id = :uuid")
    public List<SellerGameEntity> findAllByUtilisateur(UUID uuid);

    @Override
    List<SellerGameEntity> findAll();
}
