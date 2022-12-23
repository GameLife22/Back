package fr.sqli.formation.gamelife.dto;


import fr.sqli.formation.gamelife.entity.UtilisateurEntity;

public class InscriptionDtoHandler {

	public static InscriptionDto fromEntity(UtilisateurEntity entity) {
		var u = new InscriptionDto();
		u.setPrenom(entity.getPrenom());
		u.setNom(entity.getNom());
		u.setMdp(entity.getMdp());
		u.setEmail(entity.getEmail());
		u.setVille(entity.getVille());
		u.setRue(entity.getRue());
		u.setNum_rue(entity.getNum_rue());
		u.setNum_siret(entity.getNumSiret());
		u.setEtat(entity.getEtatCompte());
		if(entity.getNumSiret() == null){ //A MODIFIER QUAND LE CHECK DE L'API FONCTIONNE
			u.setRole("ROLE_ACHETEUR");
		}else if(!entity.getNumSiret().trim().isEmpty()){
			u.setRole("ROLE_REVENDEUR");
		}
		u.setCode_postal(entity.getCodePostal());
		return u;
	}

	public static UtilisateurEntity fromDto(InscriptionDto dto) {

		var u = new UtilisateurEntity();
		u.setPrenom(dto.getPrenom());
		u.setNom(dto.getNom());
		u.setMdp(dto.getMdp());
		u.setEmail(dto.getEmail());
		u.setVille(dto.getVille());
		u.setRue(dto.getRue());
		u.setNum_rue(dto.getNum_rue());
		u.setNumSiret(dto.getNum_siret());

		if(dto.getNum_siret() == null){ //A MODIFIER QUAND LE CHECK DE L'API FONCTIONNE
			u.setRole("ROLE_ACHETEUR");
		}else if(!dto.getNum_siret().trim().isEmpty()){
			u.setRole("ROLE_REVENDEUR");
		}
		u.setEtatCompte(dto.getEtat());
		u.setCodePostal(dto.getCode_postal());
		return u;
	}

}
