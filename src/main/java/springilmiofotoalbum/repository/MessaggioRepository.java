package springilmiofotoalbum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springilmiofotoalbum.model.Messaggio;

import java.util.List;

public interface MessaggioRepository extends JpaRepository<Messaggio, Integer> {
    List<Messaggio> findByEmail(String email);
}
