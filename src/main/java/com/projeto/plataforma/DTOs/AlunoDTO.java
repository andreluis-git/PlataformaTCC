package com.projeto.plataforma.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projeto.plataforma.Model.Curso;
import com.projeto.plataforma.Model.Disciplina;
import com.projeto.plataforma.Model.Tema;
import com.projeto.plataforma.Model.Usuario;
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
