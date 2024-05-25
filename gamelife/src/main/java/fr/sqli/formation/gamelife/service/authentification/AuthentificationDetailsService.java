package fr.sqli.formation.gamelife.service.authentification;

import fr.sqli.formation.gamelife.entite.SecuriteUtilisateur;
import fr.sqli.formation.gamelife.dao.IUtilisateurDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthentificationDetailsService implements UserDetailsService {

    private final IUtilisateurDao IUtilisateurDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthentificationDetailsService.class);



    @Autowired
    public AuthentificationDetailsService(IUtilisateurDao pIUtilisateurDao) {
        this.IUtilisateurDao = pIUtilisateurDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          SecuriteUtilisateur user = IUtilisateurDao.findByEmail(username)
                .map(SecuriteUtilisateur::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
          SecuriteUtilisateur UserDetails = new SecuriteUtilisateur(user.getUsername(),user.getPassword() , user.getAuthorities());
          LOGGER.info("UserDetails : {}", UserDetails);
          return UserDetails;
    }
}
