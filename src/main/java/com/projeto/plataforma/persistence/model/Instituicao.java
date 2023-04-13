package com.projeto.plataforma.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="Instituicao")
public class Instituicao extends Usuario {
    @OneToMany(mappedBy = "instituicaoCurso", cascade = CascadeType.MERGE, orphanRemoval = true)
    @JsonIgnore
    @JsonManagedReference
    List<Curso> cursosInstituicao;

}
