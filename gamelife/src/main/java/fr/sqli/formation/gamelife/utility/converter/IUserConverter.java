package fr.sqli.formation.gamelife.utility.converter;

import fr.sqli.formation.gamelife.dto.response.UserResponse;
import fr.sqli.formation.gamelife.entity.UserEntity;

public interface IUserConverter {
    public static UserResponse fromEntity(UserEntity entity) {
        var u = new UserResponse();
        u.setPrenom(entity.getPrenom());
        u.setNom(entity.getNom());
        u.setEmail(entity.getEmail());
        u.setVille(entity.getVille());
        u.setRue(entity.getRue());
        u.setNum_rue(entity.getNumRue());
        u.setNum_siret(entity.getNumSiren());
        u.setCode_postal(entity.getCodePostal());
        return u;
    }

    public static UserEntity fromDto(UserResponse dto) {

        var u = new UserEntity();
        u.setPrenom(dto.getPrenom());
        u.setNom(dto.getNom());
        u.setEmail(dto.getEmail());
        u.setVille(dto.getVille());
        u.setRue(dto.getRue());
        u.setNumRue(dto.getNum_rue());
        u.setNumSiren(dto.getNum_siret());
        u.setCodePostal(dto.getCode_postal());
        return u;
    }
}