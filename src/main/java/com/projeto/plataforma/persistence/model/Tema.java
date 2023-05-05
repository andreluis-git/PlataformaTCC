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
//    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
//    @ToString.Exclude
//    @NotNull
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Disciplina> disciplinasRelacionadas;

    @ManyToOne
    @NotNull
    private Aluno criadorTema;
    @ManyToOne
    @NotNull
    @JsonBackReference
    private Curso cursoTema;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinTable(
            name = "temaCandidatos",
            joinColumns = @JoinColumn(name = "alunoId"),
            inverseJoinColumns = @JoinColumn(name = "temaId"))
//    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Aluno> candidatosTema;

    public void adicionarCandidatoTema(Aluno aluno) {
        List<Aluno> alunos = this.candidatosTema;
        alunos.add(aluno);
        this.candidatosTema = alunos;
    }

    public void removerCandidatoTema(Aluno aluno) {
        List<Aluno> alunos = this.candidatosTema;
        alunos.removeIf(e -> e.getId().equals(aluno.getId()));
        this.candidatosTema = alunos;
    }

}
