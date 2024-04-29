package fr.sqli.formation.gamelife.dto.utilisateur;

import fr.sqli.formation.gamelife.entite.UtilisateurEntite;

import java.util.UUID;

public class UtilisateurDtoHandler {
    public static UtilisateurDto fromEntity(UtilisateurEntite entity) {
        var u = new UtilisateurDto();
        u.setId(entity.getId());
        u.setNom(entity.getNom());
        u.setPrenom(entity.getPrenom());
        return u;
    }

    public static UtilisateurEntite fromDto(UtilisateurDto dto) {

        var u = new UtilisateurEntite();
        u.setId(dto.getId());
        u.setNom(dto.getNom());
        u.setPrenom(dto.getPrenom());
        return u;
    }

    public static UtilisateurEntite fromId(UUID userId) {
        UtilisateurEntite utilisateurEntite = new UtilisateurEntite();
        utilisateurEntite.setId(userId);
        return utilisateurEntite;
    }
}
