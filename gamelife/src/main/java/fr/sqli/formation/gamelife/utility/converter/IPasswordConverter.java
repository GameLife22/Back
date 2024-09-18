package fr.sqli.formation.gamelife.utility.converter;

import fr.sqli.formation.gamelife.dto.response.EmailResponse;
import fr.sqli.formation.gamelife.dto.request.PasswordRequest;
import fr.sqli.formation.gamelife.entity.UserEntity;

public interface IPasswordConverter {


    public static UserEntity toEntity(PasswordRequest dto) {

        var u = new UserEntity();
        u.setEmail(dto.getLogin());
        return u;
    }

    public static EmailResponse fromEntity(UserEntity entity) {
        var u = new EmailResponse();
        u.setLogin(entity.getEmail());
        return u;
    }
}