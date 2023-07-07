package springilmiofotoalbum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springilmiofotoalbum.model.Utente;

import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Integer> {
    Optional<Utente> findByEmail(String email);
}
