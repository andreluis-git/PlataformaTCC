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
    private String sigla;
    private String nome;
    @ManyToMany(mappedBy = "disciplinas")
    @JsonIgnore
    private List<Tema> temas;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    //    @ToString.Exclude
    @NotNull
    private Curso cursoDisciplina;
}
