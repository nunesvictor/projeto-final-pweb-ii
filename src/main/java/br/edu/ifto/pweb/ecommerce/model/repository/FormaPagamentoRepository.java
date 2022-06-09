package br.edu.ifto.pweb.ecommerce.model.repository;

import br.edu.ifto.pweb.ecommerce.model.entity.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {
    @Query("FROM FormaPagamento WHERE slug = :slug")
    Optional<FormaPagamento> findBySlug(String slug);
}
