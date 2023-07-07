package springilmiofotoalbum.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import springilmiofotoalbum.model.Utente;
import springilmiofotoalbum.repository.UtenteRepository;

import java.util.Optional;

public class DatabaseUserDetailsService implements UserDetailsService {
    @Autowired
    UtenteRepository utenteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Utente> result = utenteRepository.findByEmail(username);
        if (result.isPresent()) {
            return new DatabaseUserDetails(result.get());
        } else {
            throw new UsernameNotFoundException("User with email " + username + " not found");
        }
    }
}
