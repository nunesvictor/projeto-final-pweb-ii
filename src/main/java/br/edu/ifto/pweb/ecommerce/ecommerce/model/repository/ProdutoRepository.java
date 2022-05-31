package br.edu.ifto.pweb.ecommerce.ecommerce.model.repository;

import br.edu.ifto.pweb.ecommerce.ecommerce.model.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}