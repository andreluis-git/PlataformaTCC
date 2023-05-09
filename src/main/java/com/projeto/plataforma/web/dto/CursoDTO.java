package com.projeto.plataforma.web.dto;

import com.projeto.plataforma.persistence.model.Disciplina;
import com.projeto.plataforma.persistence.model.Instituicao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoDTO {
    private Long id;
    @NotEmpty(message = "Campo nome obrigatório.")
    private String nome;
    @NotEmpty(message = "Campo sigla obrigatório.")
    private String sigla;
    private InstituicaoDTO instituicaoCurso;
    private List<DisciplinaDTO> disciplinasCurso;
    private List<AlunoDTO> alunosCurso;
    private List<TemaDTO> temasCurso;
}
