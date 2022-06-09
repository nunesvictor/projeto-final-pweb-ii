package br.edu.ifto.pweb.ecommerce.model.repository;

import br.edu.ifto.pweb.ecommerce.model.entity.Cliente;
import br.edu.ifto.pweb.ecommerce.model.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findAllByCliente(Cliente cliente);
}
