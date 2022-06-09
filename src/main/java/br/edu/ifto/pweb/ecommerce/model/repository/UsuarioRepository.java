package br.edu.ifto.pweb.ecommerce.model.repository;

import br.edu.ifto.pweb.ecommerce.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(@NotNull(message = "Esse campo é obrigatório") String username);
}
