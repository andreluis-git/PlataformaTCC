package com.projeto.plataforma.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="aluno")
public class Aluno extends Usuario {

    @Column(columnDefinition="TEXT")
    private String sobre;
    private String whatsapp;
    private String instagram;
    private String linkedin;
    private String facebook;

    @ManyToOne
    @NotNull
    private Curso cursoAluno;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "alunoDisciplina",
            joinColumns = @JoinColumn(name = "alunoId"),
            inverseJoinColumns = @JoinColumn(name = "disciplinaId"))
    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Disciplina> disciplinasInteresse;

    @OneToMany(mappedBy = "criadorTema", cascade = CascadeType.MERGE, orphanRemoval = true)
    @JsonIgnore
    private List<Tema> temasAluno;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "alunoCandidaturas",
            joinColumns = @JoinColumn(name = "alunoId"),
            inverseJoinColumns = @JoinColumn(name = "temaId"))
    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Tema> candidaturasAluno;

    public void setCandidaturasAluno(List<Tema> candidaturasAluno) {
        this.candidaturasAluno = candidaturasAluno;
    }

    public void setCandidaturasAluno(Tema tema) {
        List<Tema> temas = this.candidaturasAluno;
        temas.add(tema);
        this.candidaturasAluno = temas;
    }
}
