package br.edu.ifto.pweb.ecommerce.model.repository;

import br.edu.ifto.pweb.ecommerce.model.entity.Produto;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    @Query("FROM Produto WHERE active = TRUE")
    List<Produto> findActive();

    @Query("FROM Produto WHERE active = TRUE")
    List<Produto> findActive(Sort sort);
}
