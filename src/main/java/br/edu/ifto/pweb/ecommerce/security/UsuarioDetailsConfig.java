package br.edu.ifto.pweb.ecommerce.security;

import br.edu.ifto.pweb.ecommerce.model.entity.Usuario;
import br.edu.ifto.pweb.ecommerce.model.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class UsuarioDetailsConfig implements UserDetailsService {
    final UsuarioRepository repository;

    public UsuarioDetailsConfig(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repository.findByUsername(username).orElseThrow();

        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.isEnabled(),
                usuario.isAccountNonExpired(),
                usuario.isCredentialsNonExpired(),
                usuario.isAccountNonLocked(),
                usuario.getAuthorities());
    }
}