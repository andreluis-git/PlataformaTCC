package com.projeto.plataforma.web.dto;

import com.projeto.plataforma.persistence.model.Curso;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstituicaoDTO {
    List<Curso> cursosInstituicao;
}
