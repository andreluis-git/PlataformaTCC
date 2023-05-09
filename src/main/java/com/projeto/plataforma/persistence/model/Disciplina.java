package com.projeto.plataforma.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="Disciplina")
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotNull
    private String sigla;
    @NotNull
    private String nome;

    @ManyToMany(mappedBy = "disciplinasRelacionadas", cascade = {CascadeType.MERGE})
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonBackReference
    private Set<Tema> temasDisciplina;

    @ManyToMany(mappedBy = "disciplinasInteresse", cascade = {CascadeType.ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonBackReference
    private Set<Aluno> alunosDisciplina;

    @ManyToOne
    @NotNull
    @JsonBackReference
    private Curso cursoDisciplina;

}
