package fr.sqli.formation.gamelife.utility.converter;

import fr.sqli.formation.gamelife.dto.request.ResetPasswordRequest;
import fr.sqli.formation.gamelife.entity.UserEntity;

public interface IResetPasswordConverter {

    public static UserEntity toEntity(ResetPasswordRequest dto) {

        var u = new UserEntity();
        u.setPassword(dto.getPassword());
        return u;
    }
}
