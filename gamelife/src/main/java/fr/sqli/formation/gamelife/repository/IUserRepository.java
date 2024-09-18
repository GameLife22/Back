package fr.sqli.formation.gamelife.repository;


import fr.sqli.formation.gamelife.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, UUID> {

    // @Query("FROM UtilisateurEntity as c WHERE c.email=:email")
    // public UtilisateurEntity trouverEmail(@Param("email") String email);
    public Optional<UserEntity> findByEmail(String email);
    public Optional<UserEntity> findById(UUID pUUID);
    public UserEntity findByResetPasswordToken(String token);
    public List<UserEntity> findAll();
}
