package com.projeto.plataforma.web.controllers;

import com.projeto.plataforma.persistence.dao.CursoRepository;
import com.projeto.plataforma.persistence.model.Curso;
import com.projeto.plataforma.web.dto.CursoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/curso")
public class CursoController {
    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping("/buscarCurso")
    public ResponseEntity<Object> buscarCursoPorId(@RequestParam Long id) {
        return ResponseEntity.ok(cursoRepository.findById(id).get());
    }

    @GetMapping("/buscarCursoPorNome")
    public ResponseEntity<Object> buscarCursoPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(cursoRepository.findByNome(nome).get());
    }

    @GetMapping("/listarCursos")
    public ResponseEntity<Object> listarCursos() {
        return ResponseEntity.ok(cursoRepository.findAll());
    }

    @PostMapping("/cadastrarCurso")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INSTITUICAO')")
    public ResponseEntity<Object> cadastrarCurso(@RequestBody CursoDTO cursoDTO) {
        try {
            Curso curso = new Curso();
            curso.setNome(cursoDTO.getNome());
            curso.setSigla(cursoDTO.getSigla());
            cursoRepository.save(curso);

            return ResponseEntity.ok().build();
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/editarCurso")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INSTITUICAO')")
    public ResponseEntity<Object> editarCurso(@RequestBody Curso curso) {
        try {
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
        catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/deletarCurso")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INSTITUICAO')")
    public ResponseEntity<Object> deletarCurso(@RequestParam Long cursoId) {
        cursoRepository.deleteById(cursoId);

        return  ResponseEntity.ok().build();
    }
}
