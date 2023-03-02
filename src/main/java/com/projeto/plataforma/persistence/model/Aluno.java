package com.projeto.plataforma.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

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
    private Curso cursoAluno;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "alunoDisciplina",
            joinColumns = @JoinColumn(name = "alunoId"),
            inverseJoinColumns = @JoinColumn(name = "disciplinaId"))
    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private List<Disciplina> disciplinasInteresse;

    @OneToMany(mappedBy = "criadorTema", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Tema> temasAluno;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "alunoCandidaturas",
            joinColumns = @JoinColumn(name = "alunoId"),
            inverseJoinColumns = @JoinColumn(name = "temaId"))
    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private List<Tema> candidaturasAluno;

}
