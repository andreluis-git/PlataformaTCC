package com.projeto.plataforma.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="Curso")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String sigla;
    private String nome;
    @OneToMany(mappedBy = "cursoTema")
    @JsonIgnore
    private List<Tema> temas;
    @OneToMany(mappedBy = "cursoUsuario")
    @JsonIgnore
    private List<Usuario> usuarios;
    @OneToMany(mappedBy = "cursoSemestre")
    @JsonIgnore
    private List<Semestre> semestres;
    @OneToMany(mappedBy = "cursoDisciplina")
    @JsonIgnore
    private List<Disciplina> disciplinas;
}
