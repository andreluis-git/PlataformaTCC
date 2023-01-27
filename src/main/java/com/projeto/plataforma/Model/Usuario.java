package com.projeto.plataforma.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<Tema> temaList;

//    @OneToMany(mappedBy = "usuario")
//    private List<Loja> lojas;

}
