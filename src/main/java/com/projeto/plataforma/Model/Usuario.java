package com.projeto.plataforma.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
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

//    @OneToMany(mappedBy = "usuario")
//    private List<Loja> lojas;

}
