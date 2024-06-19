package fr.sqli.formation.gamelife.utility.converter;


import fr.sqli.formation.gamelife.dto.Registration;
import fr.sqli.formation.gamelife.entity.UserEntity;

public interface IRegistrationConverter {

	public static Registration fromEntity(UserEntity entity) {
		var u = new Registration();
		u.setPrenom(entity.getFirstName());
		u.setNom(entity.getLastName());
		u.setMdp(entity.getPassword());
		u.setEmail(entity.getEmail());
		u.setVille(entity.getCity());
		u.setRue(entity.getStreet());
		u.setNum_rue(entity.getStreetNumber());
		u.setNum_siret(entity.getSirenNumber());
		u.setCode_postal(entity.getZipCode());
		return u;
	}

	public static UserEntity fromDto(Registration dto) {

		var u = new UserEntity();
		u.setFirstName(dto.getPrenom());
		u.setLastName(dto.getNom());
		u.setPassword(dto.getMdp());
		u.setEmail(dto.getEmail());
		u.setCity(dto.getVille());
		u.setStreet(dto.getRue());
		u.setStreetNumber(dto.getNum_rue());
		u.setSirenNumber(dto.getNum_siret());
		u.setZipCode(dto.getCode_postal());
		return u;
	}
}