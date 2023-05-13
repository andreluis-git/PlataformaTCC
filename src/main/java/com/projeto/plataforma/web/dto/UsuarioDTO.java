package com.projeto.plataforma.web.dto;

import com.projeto.plataforma.persistence.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email;
    private String password;
    private boolean ativo;
    private Collection<RoleDTO> roles;
    private String senhaAtual = "";
    private String senhaNova = "";
}
