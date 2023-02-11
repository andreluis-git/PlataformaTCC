package com.projeto.plataforma.web.dto;

import com.projeto.plataforma.persistence.model.Curso;
import com.projeto.plataforma.persistence.model.Disciplina;
import com.projeto.plataforma.persistence.model.Tema;
import lombok.Data;

import java.util.List;

@Data
public class AlunoDTO {
    private String sobre;
    private String whatsapp;
    private String instagram;
    private String linkedin;
    private String facebook;
    private Curso cursoUsuario;
    private List<Tema> meusTemas;
    private Curso minhasCandidaturas;

    private List<Disciplina> disciplinasInteresse;
}
