package com.projeto.plataforma.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String descricao;
    @Column(nullable = false)
    private Boolean ativo = true;
    @ManyToMany
    @JoinTable(
        name = "temaDisciplina",
        joinColumns = @JoinColumn(name = "temaId"),
        inverseJoinColumns = @JoinColumn(name = "disciplinaId"))
//    @ToString.Exclude
//    @NotNull
    private List<Disciplina> disciplinasRelacionadas;

    @ManyToOne
    @NotNull
    private Aluno criadorTema;
    @ManyToOne
    @NotNull
    private Curso cursoTema;
    @ManyToMany(mappedBy = "candidaturasAluno")
    @JsonIgnore
    private List<Aluno> candidatosTema;

}
