package com.projeto.plataforma.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

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

    @ManyToMany(mappedBy = "disciplinasRelacionadas", cascade = CascadeType.MERGE)
    @JsonBackReference
    private List<Tema> temasDisciplina;

    @ManyToMany(mappedBy = "disciplinasInteresse", cascade = CascadeType.MERGE)
    @JsonBackReference
    private List<Aluno> alunosDisciplina;

    @ManyToOne
    @NotNull
    @JsonBackReference
    private Curso cursoDisciplina;

}
