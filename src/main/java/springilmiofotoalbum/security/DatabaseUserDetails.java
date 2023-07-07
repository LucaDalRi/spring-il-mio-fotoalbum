package springilmiofotoalbum.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import springilmiofotoalbum.model.Ruolo;
import springilmiofotoalbum.model.Utente;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DatabaseUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final Set<GrantedAuthority> authorities;

    public DatabaseUserDetails(Utente utente) {
        this.username = utente.getEmail();
        this.password = utente.getPassword();

        this.authorities = new HashSet<>();
        for (Ruolo ruolo : utente.getRuoli()) {
            authorities.add(new SimpleGrantedAuthority(ruolo.getNome()));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
