package com.projeto.plataforma.web.dto;

import com.projeto.plataforma.persistence.model.Disciplina;
import com.projeto.plataforma.persistence.model.Instituicao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoDTO {
    private Long id;
    private String nome;
    private String sigla;
    private InstituicaoDTO instituicaoCurso;
    private List<DisciplinaDTO> disciplinasCurso;
    private List<AlunoDTO> alunosCurso;
    private List<TemaDTO> temasCurso;
}
