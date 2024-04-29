package fr.sqli.formation.gamelife.dao;


import fr.sqli.formation.gamelife.entite.UtilisateurEntite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUtilisateurDao extends JpaRepository<UtilisateurEntite, UUID> {

    // @Query("FROM UtilisateurEntity as c WHERE c.email=:email")
    // public UtilisateurEntity trouverEmail(@Param("email") String email);
    public Optional<UtilisateurEntite> findByEmail(String email);
    public Optional<UtilisateurEntite> findById(UUID pUUID);
    public UtilisateurEntite findByResetPasswordToken(String token);
    public List<UtilisateurEntite> findAll();



}
