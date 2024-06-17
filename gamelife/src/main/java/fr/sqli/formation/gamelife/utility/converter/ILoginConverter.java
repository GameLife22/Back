package fr.sqli.formation.gamelife.utility.converter;

import fr.sqli.formation.gamelife.dto.request.LoginRequest;
import fr.sqli.formation.gamelife.dto.response.LoginResponse;
import fr.sqli.formation.gamelife.entity.UserEntity;

public interface ILoginConverter {

    public static LoginResponse fromEntity(UserEntity entity) {
        var u = new LoginResponse();
        //todo: replace
        //u.setId(entity.getId());
        u.setEmail(entity.getEmail());
        u.setLastName(entity.getLastName());
        u.setEtat(entity.getAccountStatus());
        u.setNum_rue(entity.getStreetNumber());
        u.setStreet(entity.getStreet());
        u.setNum_siren(entity.getSirenNumber());
        u.setFirstName(entity.getFirstName());
        u.setCity(entity.getCity());
        u.setCode_postal(entity.getZipCode());
        return u;
    }

    public static UserEntity toEntity(LoginRequest dto) {

        var u = new UserEntity();
        u.setEmail(dto.email());
        u.setPassword(dto.password());
        return u;
    }
}
