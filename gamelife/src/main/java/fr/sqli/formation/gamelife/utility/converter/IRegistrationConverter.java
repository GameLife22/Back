package fr.sqli.formation.gamelife.utility.converter;


import fr.sqli.formation.gamelife.dto.Registration;
import fr.sqli.formation.gamelife.entity.UserEntity;

public interface IRegistrationConverter {

	public static Registration fromEntity(UserEntity entity) {
		var u = new Registration();
		u.setFirstName(entity.getFirstName());
		u.setLastName(entity.getLastName());
		u.setPassword(entity.getPassword());
		u.setEmail(entity.getEmail());
		u.setCity(entity.getCity());
		u.setStreet(entity.getStreet());
		u.setNum_rue(entity.getStreetNumber());
		u.setNum_siret(entity.getSirenNumber());
		u.setCode_postal(entity.getZipCode());
		return u;
	}

	public static UserEntity fromDto(Registration dto) {

		var u = new UserEntity();
		u.setFirstName(dto.getFirstName());
		u.setLastName(dto.getLastName());
		u.setPassword(dto.getPassword());
		u.setEmail(dto.getEmail());
		u.setCity(dto.getCity());
		u.setStreet(dto.getStreet());
		u.setStreetNumber(dto.getNum_rue());
		u.setSirenNumber(dto.getNum_siret());
		u.setZipCode(dto.getCode_postal());
		return u;
	}
}