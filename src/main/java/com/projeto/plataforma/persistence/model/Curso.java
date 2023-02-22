package com.projeto.plataforma.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
//@JsonIgnoreProperties({"instituicaoCurso"})
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
    @JsonBackReference
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