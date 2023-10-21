package fr.sqli.formation.gamelife.entity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

public class SecurityUser implements UserDetails {

    private final UtilisateurEntity user;


    public SecurityUser(UtilisateurEntity user) {
        this.user = user;
    }

    public SecurityUser(String username, String encodedPassword, Collection<? extends GrantedAuthority> authorities) {
        this.user = new UtilisateurEntity();
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