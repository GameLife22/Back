package fr.sqli.formation.gamelife.service;

import fr.sqli.formation.gamelife.repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationDetailsService implements UserDetailsService {

    private final IUserRepository IUserRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationDetailsService.class);

    @Autowired
    public AuthenticationDetailsService(IUserRepository pIUserRepository) {
        this.IUserRepository = pIUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          UserSecurityService user = IUserRepository.findByEmail(username)
                .map(UserSecurityService::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
          return new UserSecurityService(user.getUsername(),user.getPassword() , user.getAuthorities());
    }
}
