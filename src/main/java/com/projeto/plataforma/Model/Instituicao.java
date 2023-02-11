package com.projeto.plataforma.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="Instituicao")
public class Instituicao extends Usuario{
    @OneToMany(mappedBy = "instituicaoCurso")
    @JsonIgnore
    List<Curso> cursosInstituicao;

    @OneToMany(mappedBy = "instituicaoAluno")
    @JsonIgnore
    List<Aluno> alunosInstituicao;
}
