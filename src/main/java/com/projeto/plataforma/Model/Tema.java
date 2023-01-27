package com.projeto.plataforma.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
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
    private String titulo;
    private String descricao;
    @Column(nullable = false)
    private Boolean excluido = false;
    @ManyToMany
    @JoinTable(
        name = "tema_disciplina",
        joinColumns = @JoinColumn(name = "tema_id"),
        inverseJoinColumns = @JoinColumn(name = "disciplina_id"))
//    @ToString.Exclude
    private List<Disciplina> disciplinasList;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
//    @ToString.Exclude
    private Usuario usuario;
}
