package com.projeto.plataforma.Config.Security.JWT;

import com.projeto.plataforma.Config.Security.Permissoes.MyUserDetailService;
import com.projeto.plataforma.persistence.model.Usuario;
import com.projeto.plataforma.persistence.dao.UsuarioRepository;
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

        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(username);
        if (!usuarioOptional.isPresent()) {
            throw new UsernameNotFoundException("Usuário [" + username +"] não encontrado");
        }

//        Usuario user = usuarioOptional.get();

        return new DetalheUsuarioData(usuarioOptional);
//        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getAuthorities());
    }
}
