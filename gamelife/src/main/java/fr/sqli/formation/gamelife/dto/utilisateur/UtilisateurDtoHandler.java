package fr.sqli.formation.gamelife.dto.utilisateur;

import fr.sqli.formation.gamelife.entity.UtilisateurEntity;

public class UtilisateurDtoHandler {
    public static UtilisateurDto fromEntity(UtilisateurEntity entity) {
        var u = new UtilisateurDto();
        u.setId(entity.getId());
        u.setNom(entity.getNom());
        u.setPrenom(entity.getPrenom());
        return u;
    }

    public static UtilisateurEntity fromDto(UtilisateurDto dto) {

        var u = new UtilisateurEntity();
        u.setId(dto.getId());
        u.setNom(dto.getNom());
        u.setPrenom(dto.getPrenom());
        return u;
    }

    public static UtilisateurEntity fromId(Integer userId) {
        UtilisateurEntity utilisateurEntity = new UtilisateurEntity();
        utilisateurEntity.setId(userId);
        return utilisateurEntity;
    }
}
