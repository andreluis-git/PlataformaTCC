package com.projeto.plataforma.web.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.projeto.plataforma.persistence.model.Privilege;
import com.projeto.plataforma.persistence.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private Long id;
    private Collection<UsuarioDTO> users;
    private Collection<PrivilegeDTO> privileges;
    private String name;
}
