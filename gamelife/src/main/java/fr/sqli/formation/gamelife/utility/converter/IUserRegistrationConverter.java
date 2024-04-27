package fr.sqli.formation.gamelife.utility.converter;

import fr.sqli.formation.gamelife.dto.UserRegistration;
import fr.sqli.formation.gamelife.entity.UserEntity;

import java.util.UUID;

public interface IUserRegistrationConverter {
    public static UserRegistration fromEntity(UserEntity entity) {
        var u = new UserRegistration();
        u.setId(entity.getId());
        u.setNom(entity.getNom());
        u.setPrenom(entity.getPrenom());
        return u;
    }

    public static UserEntity fromDto(UserRegistration dto) {

        var u = new UserEntity();
        u.setId(dto.getId());
        u.setNom(dto.getNom());
        u.setPrenom(dto.getPrenom());
        return u;
    }

    public static UserEntity fromId(UUID userId) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        return userEntity;
    }
}