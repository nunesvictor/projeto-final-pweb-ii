package br.edu.ifto.pweb.ecommerce.model.repository;

import br.edu.ifto.pweb.ecommerce.model.entity.Preco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrecoRepository extends JpaRepository<Preco, Long> {
    @Override
    @Query("FROM Preco ORDER BY produto.descricao ASC, updatedAt DESC")
    List<Preco> findAll();
}
