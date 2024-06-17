package fr.sqli.formation.gamelife.utility.converter;

import fr.sqli.formation.gamelife.dto.response.UserResponse;
import fr.sqli.formation.gamelife.entity.UserEntity;

public interface IUserConverter {
    public static UserResponse fromEntity(UserEntity entity) {
        var u = new UserResponse();
        u.setFirstName(entity.getFirstName());
        u.setLastName(entity.getLastName());
        u.setEmail(entity.getEmail());
        u.setCity(entity.getCity());
        u.setStreet(entity.getStreet());
        u.setNum_rue(entity.getStreetNumber());
        u.setNum_siret(entity.getSirenNumber());
        u.setCode_postal(entity.getZipCode());
        return u;
    }

    public static UserEntity fromDto(UserResponse dto) {

        var u = new UserEntity();
        u.setFirstName(dto.getFirstName());
        u.setLastName(dto.getLastName());
        u.setEmail(dto.getEmail());
        u.setCity(dto.getCity());
        u.setStreet(dto.getStreet());
        u.setStreetNumber(dto.getNum_rue());
        u.setSirenNumber(dto.getNum_siret());
        u.setZipCode(dto.getCode_postal());
        return u;
    }
}