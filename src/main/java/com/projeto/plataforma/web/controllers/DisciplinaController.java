package com.projeto.plataforma.web.controllers;

import com.projeto.plataforma.persistence.model.Disciplina;
import com.projeto.plataforma.persistence.dao.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/disciplina")
public class DisciplinaController {

    @Autowired
    private DisciplinaRepository disciplinaRepository;


    @GetMapping("/listarDisciplinas")
    public ResponseEntity<Object> listarDisciplinas() {
        List<Disciplina> listaDisciplinas = disciplinaRepository.findAll();
        return ResponseEntity.ok(listaDisciplinas);
    }

}
