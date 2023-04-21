package com.projeto.plataforma.web.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projeto.plataforma.persistence.model.Curso;
import com.projeto.plataforma.persistence.model.Disciplina;
import com.projeto.plataforma.persistence.model.Tema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlunoDTO extends UsuarioDTO {
    private String sobre;
    private String whatsapp;
    private String instagram;
    private String linkedin;
    private String facebook;
    private CursoDTO cursoAluno;
    private List<DisciplinaDTO> disciplinasInteresse;
    private List<TemaDTO> temasAluno;
    private List<TemaDTO> candidaturasAluno;
//    private boolean enable;
}
