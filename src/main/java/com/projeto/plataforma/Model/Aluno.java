package com.projeto.plataforma.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="aluno")
public class Aluno extends Usuario {

    private String sobre;
    private String whatsapp;
    private String instagram;
    private String linkedin;
    private String facebook;

    @ManyToOne
    @NotNull
    private Instituicao instituicaoAluno;

    @ManyToOne
    @NotNull
    private Curso cursoAluno;

    @ManyToMany
    @JoinTable(
            name = "alunoDisciplina",
            joinColumns = @JoinColumn(name = "alunoId"),
            inverseJoinColumns = @JoinColumn(name = "disciplinaId"))
    private List<Disciplina> disciplinasInteresse;

    @OneToMany(mappedBy = "criadorTema")
    @JsonIgnore
    private List<Tema> temasAluno;

    @ManyToMany
    @JoinTable(
            name = "alunoCandidaturas",
            joinColumns = @JoinColumn(name = "alunoId"),
            inverseJoinColumns = @JoinColumn(name = "temaId"))
    private List<Tema> candidaturasAluno;

}
