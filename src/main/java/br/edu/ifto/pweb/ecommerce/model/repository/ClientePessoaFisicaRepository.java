package br.edu.ifto.pweb.ecommerce.model.repository;

import br.edu.ifto.pweb.ecommerce.model.entity.ClientePessoaFisica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClientePessoaFisicaRepository extends JpaRepository<ClientePessoaFisica, Long> {
    @Query("FROM ClientePessoaFisica WHERE usuario.username = :username")
    List<ClientePessoaFisica> findAllByUsername(String username);

    @Query("FROM ClientePessoaFisica WHERE usuario.username = :username")
    Optional<ClientePessoaFisica> findByUsername(String username);
}
