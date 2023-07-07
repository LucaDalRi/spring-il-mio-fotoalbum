package springilmiofotoalbum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springilmiofotoalbum.model.Foto;

import java.util.List;

public interface FotoRepository extends JpaRepository<Foto, Integer> {
    List<Foto> findByTitolo(String titolo);

    List<Foto> findByTitoloContainingIgnoreCase(String title);

}
