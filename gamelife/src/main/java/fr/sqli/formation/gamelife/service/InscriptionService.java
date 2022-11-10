package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.InscriptionDto;
import fr.sqli.formation.gamelife.dto.InscriptionDtoHandler;
import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import fr.sqli.formation.gamelife.ex.AuthentificationException;
import fr.sqli.formation.gamelife.ex.UtilisateurExistantException;
import fr.sqli.formation.gamelife.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class InscriptionService {
    @Autowired
    private UtilisateurRepository uDao;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public UtilisateurEntity inscription(InscriptionDto dto) throws Exception{
            //UtilisateurEntity.validate(nom,prenom,pwd,email,ville,num_rue,rue,role,num_Siren,etat);
            var newUser = uDao.findByEmail(dto.getEmail());
            if(newUser.isEmpty()){
                UtilisateurEntity u = InscriptionDtoHandler.fromDto(dto);
                u.setMdp(encoder.encode(u.getMdp()));
                return uDao.save(u);
            }else {
                throw new UtilisateurExistantException("Utilisateur deja enregistre");
            }
    }
}
