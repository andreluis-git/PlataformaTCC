package com.projeto.plataforma.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="Curso")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotNull
    private String nome;
    @NotNull
    private String sigla;

    @ManyToOne
    @NotNull
    private Instituicao instituicaoCurso;

    @OneToMany(mappedBy = "cursoDisciplina")
    @JsonIgnore
    private List<Disciplina> disciplinasCurso;

    @OneToMany(mappedBy = "cursoAluno")
    @JsonIgnore
    private List<Aluno> alunosCurso;

    @OneToMany(mappedBy = "cursoTema")
    @JsonIgnore
    private List<Tema> temasCurso;

}
