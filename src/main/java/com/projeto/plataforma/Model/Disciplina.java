package com.projeto.plataforma.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

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

    @ManyToMany(mappedBy = "disciplinasRelacionadas")
    @JsonIgnore
    private List<Tema> temasDisciplina;

    @ManyToMany(mappedBy = "disciplinasInteresse")
    @JsonIgnore
    private List<Aluno> alunosDisciplina;

    @ManyToOne
    @NotNull
    @JsonIgnore
    private Curso cursoDisciplina;

}
