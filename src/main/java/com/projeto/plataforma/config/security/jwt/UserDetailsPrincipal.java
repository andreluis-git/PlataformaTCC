package com.projeto.plataforma.config.security.jwt;

import com.projeto.plataforma.persistence.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

public class UserDetailsPrincipal implements UserDetails {

    private final Optional<Usuario> usuario;

    public UserDetailsPrincipal(Optional<Usuario> usuario) {
        this.usuario = usuario;
    }

    @Override
    public String getPassword() {
        return usuario.orElse(new Usuario()).getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.orElse(new Usuario()).getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.usuario.get().isEnabled();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.usuario.get().getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}
