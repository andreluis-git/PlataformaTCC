package com.projeto.plataforma.web.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.projeto.plataforma.persistence.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToMany;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrivilegeDTO {
    private Long id;
    private String name;
    private Collection<RoleDTO> roles;
}
