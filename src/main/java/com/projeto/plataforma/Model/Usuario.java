package com.projeto.plataforma.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(unique = true)
    private String login;
    private String password;
    @Column(unique = true)
    private String email;
    private Integer semestre;
    private String whatsapp;
    private String instagram;
    private String linkedin;
    private String facebook;
    @ManyToOne
    @JoinColumn(name = "curso_id")
    //    @ToString.Exclude
    @NotNull
    private Curso cursoUsuario;
    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<Tema> temas;

}
