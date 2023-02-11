package com.projeto.plataforma.Config.Security;

import com.projeto.plataforma.Model.Usuario;
import com.projeto.plataforma.Repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DetalheUsuarioServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public DetalheUsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Usuario> usuario = usuarioRepository.findByEmail(username);
        if (!usuario.isPresent()) {
            throw new UsernameNotFoundException("Usuário [" + username +"] não encontrado");
        }

        return new DetalheUsuarioData(usuario);
    }
}
