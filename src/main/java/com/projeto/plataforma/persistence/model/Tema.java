package com.projeto.plataforma.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="Tema")
public class Tema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotNull
    private String titulo;
    @NotNull
    @Column(columnDefinition="TEXT")
    private String descricao;
    @Column(nullable = false)
    private Boolean ativo = true;
    @NotNull
    private String dataCriacao;
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
        name = "temaDisciplina",
        joinColumns = @JoinColumn(name = "temaId"),
        inverseJoinColumns = @JoinColumn(name = "disciplinaId"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Disciplina> disciplinasRelacionadas;

    @ManyToOne
    @NotNull
    private Aluno criadorTema;
    @ManyToOne
    @NotNull
    @JsonBackReference
    private Curso cursoTema;
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "temaCandidatos",
            joinColumns = @JoinColumn(name = "temaId"),
            inverseJoinColumns = @JoinColumn(name = "alunoId"))
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Aluno> candidatosTema;

    public void adicionarCandidatoTema(Aluno aluno) {
        Set<Aluno> alunos = this.candidatosTema;
        alunos.add(aluno);
        this.candidatosTema = alunos;
    }

    public void removerCandidatoTema(Aluno aluno) {
        Set<Aluno> alunos = this.candidatosTema;
        alunos.removeIf(e -> e.getId().equals(aluno.getId()));
        this.candidatosTema = alunos;
    }

    public void adicionarDisciplinaTema(Disciplina disciplina) {
        Set<Disciplina> disciplinas = this.disciplinasRelacionadas;
        disciplinas.add(disciplina);
        this.disciplinasRelacionadas = disciplinas;
    }

    public void removerDisciplinaTema(Disciplina disciplina) {
        Set<Disciplina> disciplinas = this.disciplinasRelacionadas;
        disciplinas.removeIf(e -> e.getId().equals(disciplina.getId()));
        this.disciplinasRelacionadas = disciplinas;
    }
}
