package com.projeto.plataforma.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private Set<Disciplina> disciplinasInteresse;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "criadorTema", cascade = CascadeType.MERGE, orphanRemoval = true)
    @JsonIgnore
    private List<Tema> temasAluno;

    @ManyToMany(mappedBy = "candidatosTema")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonBackReference
    private Set<Tema> candidaturasAluno;

    public void adicionarDisciplinaAluno(Disciplina disciplina) {
        Set<Disciplina> disciplinas = this.disciplinasInteresse;
        disciplinas.add(disciplina);
        this.disciplinasInteresse = disciplinas;
    }

    public void removerDisciplinaAluno(Disciplina disciplina) {
        Set<Disciplina> disciplinas = this.disciplinasInteresse;
        disciplinas.removeIf(e -> e.getId().equals(disciplina.getId()));
        this.disciplinasInteresse = disciplinas;
    }

}
