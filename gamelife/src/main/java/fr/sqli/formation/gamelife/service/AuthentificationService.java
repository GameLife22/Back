package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.entity.UtilisateurEntity;
import fr.sqli.formation.gamelife.ex.AuthentificationException;
import fr.sqli.formation.gamelife.repository.UtilisateurRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthentificationService {
    @Autowired
    private static final Logger LOG = LogManager.getLogger();
    private UtilisateurRepository uDao;


    public UtilisateurEntity authentifier(String login, String pwd) throws Exception {
        if(login != null && !login.trim().isEmpty() && pwd!= null && !pwd.trim().isEmpty()){
            //ok

            var monUser = uDao.findByEmail(login);

            if(monUser.isPresent()){
                LOG.debug("Email OK");
                //ok
                if(monUser.get().getMdp().equals(pwd)){
                    LOG.debug("Password OK");
                    return monUser.get();
                }
                else {
                    //pas ok
                    throw new AuthentificationException("Utilisateur inconnu");
                }
            }
            else {
                //pas ok
                throw new AuthentificationException("Utilisateur inconnu");
            }
        }
        throw new IllegalArgumentException("Login ou password vide ou null");

    }
}
