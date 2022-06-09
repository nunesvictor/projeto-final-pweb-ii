package br.edu.ifto.pweb.ecommerce.model.repository;

import br.edu.ifto.pweb.ecommerce.model.entity.Categoria;
import br.edu.ifto.pweb.ecommerce.model.entity.Marca;
import br.edu.ifto.pweb.ecommerce.model.entity.Produto;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findAllByActiveTrue();

    List<Produto> findAllByActiveTrue(Sort sort);

    List<Produto> findAllByActiveTrueAndCategoriasId(Long categorias_id, Sort sort);

    List<Produto> findAllByActiveTrueAndMarcaId(Long marca_id, Sort sort);

    List<Produto> findAllByActiveTrueAndPrecos_PromocaoTrue(Sort sort);
}
