package fr.sqli.formation.gamelife.entite;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

//todo: rename
public class SecuriteUtilisateur implements UserDetails {

    private final UtilisateurEntite user;


    public UUID getId() {
        if (user != null) {
            return user.getId();
        } else {
            return null;
        }
    }



    public SecuriteUtilisateur(UtilisateurEntite user) {
        this.user = user;
    }

    public SecuriteUtilisateur(String username, String encodedPassword, Collection<? extends GrantedAuthority> authorities) {
        this.user = new UtilisateurEntite();
        this.user.setEmail(username);
        this.user.setMdp(encodedPassword);
        this.user.setRole(authorities.stream()
                .map(GrantedAuthority::getAuthority)
        .reduce("", String::concat));
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public String getPassword() {
        return user.getMdp();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(user
                        .getRole()
                        .split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}