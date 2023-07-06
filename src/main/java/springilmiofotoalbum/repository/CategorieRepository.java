package springilmiofotoalbum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springilmiofotoalbum.model.Categoria;

public interface CategorieRepository extends JpaRepository<Categoria, Integer> {
}
