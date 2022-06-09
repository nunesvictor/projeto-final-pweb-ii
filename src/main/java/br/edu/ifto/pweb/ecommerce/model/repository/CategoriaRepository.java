package br.edu.ifto.pweb.ecommerce.model.repository;

import br.edu.ifto.pweb.ecommerce.model.entity.Categoria;
import br.edu.ifto.pweb.ecommerce.model.entity.Marca;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    @Query("FROM Categoria WHERE active = TRUE")
    List<Categoria> findActive();

    @Query("FROM Categoria WHERE active = TRUE")
    List<Categoria> findActive(Sort sort);
}
