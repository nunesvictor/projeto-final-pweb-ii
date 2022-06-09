package br.edu.ifto.pweb.ecommerce.model.repository;

import br.edu.ifto.pweb.ecommerce.model.entity.ClientePessoaFisica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface ClientePessoaFisicaRepository extends JpaRepository<ClientePessoaFisica, Long> {
    Optional<ClientePessoaFisica> findByUsuarioUsername(@NotNull(message = "Esse campo é obrigatório") String usuario_username);

    List<ClientePessoaFisica> findAllByUsuarioUsername(@NotNull(message = "Esse campo é obrigatório") String usuario_username);
}
