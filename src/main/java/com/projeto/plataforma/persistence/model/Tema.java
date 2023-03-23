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
import java.util.Date;
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
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "temaDisciplina",
        joinColumns = @JoinColumn(name = "temaId"),
        inverseJoinColumns = @JoinColumn(name = "disciplinaId"))
    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
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
    @ManyToMany(mappedBy = "candidaturasAluno", cascade = CascadeType.ALL)
    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @JsonBackReference
    private List<Aluno> candidatosTema;

}
