package com.projeto.plataforma.web.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projeto.plataforma.persistence.model.Aluno;
import com.projeto.plataforma.persistence.model.Curso;
import com.projeto.plataforma.persistence.model.Tema;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisciplinaDTO {
    private Long id;
    private String sigla;
    private String nome;
    private List<TemaDTO> temasDisciplina;
//    private List<Aluno> alunosDisciplina;
    private Curso cursoDisciplina;
}
