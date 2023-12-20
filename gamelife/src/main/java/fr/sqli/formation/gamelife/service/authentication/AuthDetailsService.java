package fr.sqli.formation.gamelife.service.authentication;

import fr.sqli.formation.gamelife.entityGen.SecurityUser;
import fr.sqli.formation.gamelife.repository.UtilisateurRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthDetailsService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;
    private static final Logger LOG = LogManager.getLogger();



    @Autowired
    public AuthDetailsService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          SecurityUser user = utilisateurRepository.findByEmail(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
          return new SecurityUser(user.getUsername(),user.getPassword() , user.getAuthorities());
    }
}
