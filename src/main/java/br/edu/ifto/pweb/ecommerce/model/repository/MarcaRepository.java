package br.edu.ifto.pweb.ecommerce.model.repository;

import br.edu.ifto.pweb.ecommerce.model.entity.Marca;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
    @Query("FROM Marca WHERE active = TRUE")
    List<Marca> findActive();

    @Query("FROM Marca WHERE active = TRUE")
    List<Marca> findActive(Sort sort);
}
