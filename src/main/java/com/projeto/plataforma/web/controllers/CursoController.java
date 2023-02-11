package com.projeto.plataforma.web.controllers;

import com.projeto.plataforma.web.dto.CursoDTO;
import com.projeto.plataforma.persistence.model.Curso;
import com.projeto.plataforma.persistence.dao.CursoRepository;
import com.projeto.plataforma.web.util.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/curso")
public class CursoController {
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ValidationUtils validationUtils;

    @GetMapping("/listarCursos")
    public ResponseEntity<Object> listarCursos() {
        return ResponseEntity.ok(cursoRepository.findAll());
    }

    @PostMapping("/cadastrarCurso")
    public ResponseEntity<Object> cadastrarCurso(@RequestBody CursoDTO cursoDTO, @RequestHeader(value=ValidationUtils.HEADER_INSTITUICAO) String instituicaoKey) {

        if(!validationUtils.validarInstituicao(instituicaoKey)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Curso curso = new Curso();
        curso.setNome(cursoDTO.getNome());
        curso.setSigla(cursoDTO.getSigla());
        cursoRepository.save(curso);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/editarCurso")
    public ResponseEntity<Object> editarCurso(@RequestBody Curso curso, @RequestHeader(value = ValidationUtils.HEADER_INSTITUICAO) String instituicaoKey) {

        if(!validationUtils.validarInstituicao(instituicaoKey)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<Curso> optCurso = cursoRepository.findById(curso.getId());
        if(optCurso.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Curso cursoDb = optCurso.get();
        cursoDb.setSigla(curso.getSigla());
        cursoDb.setNome(curso.getNome());

        cursoRepository.save(cursoDb);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deletarCurso")
    public ResponseEntity<Object> deletarCurso(@RequestParam Long cursoId, @RequestHeader(value = ValidationUtils.HEADER_INSTITUICAO) String instituicaoKey) {

        if(!validationUtils.validarInstituicao(instituicaoKey)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        cursoRepository.deleteById(cursoId);

        return  ResponseEntity.ok().build();
    }
}
