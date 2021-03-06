package br.edu.ifto.pweb.ecommerce.model.repository;

import br.edu.ifto.pweb.ecommerce.model.entity.Preco;
import br.edu.ifto.pweb.ecommerce.model.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PrecoRepository extends JpaRepository<Preco, Long> {
    Optional<Preco> findFirstByActiveTrueAndProdutoOrderByUpdatedAtDesc(Produto produto);

    List<Preco> findAllByProduto_Categorias_Id(Long produto_categorias_id);
}
