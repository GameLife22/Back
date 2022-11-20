package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.dto.LoginDto;
import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import fr.sqli.formation.gamelife.ex.AuthentificationException;
import fr.sqli.formation.gamelife.ex.CompteDesactiveException;
import fr.sqli.formation.gamelife.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthentificationService {

    @Autowired
    private UtilisateurRepository uDao;
    @Autowired
    private BCryptPasswordEncoder encoder;


    public UtilisateurEntity authentifier(LoginDto dto) throws Exception {
        if (dto.getLogin() != null && !dto.getLogin().trim().isEmpty() && dto.getPwd() != null && !dto.getPwd().trim().isEmpty() ) {
            var monUser = uDao.findByEmail(dto.getLogin());

            if (monUser.isPresent()) {
                if(monUser.get().getEtatCompte().equals("active")){
                    if (encoder.matches(dto.getPwd(), monUser.get().getMdp())) {
                        return monUser.get();
                    } else {
                        //pas ok
                        throw new AuthentificationException("Utilisateur inconnu");
                    }
                } else {
                    throw new CompteDesactiveException("Compte Desactive");
                }
            } else {
                //pas ok
                throw new AuthentificationException("Utilisateur inconnu");
            }

        }
        else {
            throw new IllegalArgumentException("Login ou password vide ou null");
        }
    }
}
