package com.projeto.plataforma.web.controllers;

import com.projeto.plataforma.persistence.model.Curso;
import com.projeto.plataforma.persistence.model.Disciplina;
import com.projeto.plataforma.persistence.dao.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping("/cadastrarDisciplina")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INSTITUICAO')")
    public ResponseEntity<Object> cadastrarDisciplina(@RequestBody Disciplina disciplina) {
        try {
            return ResponseEntity.ok(disciplinaRepository.save(disciplina));
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/editarDisciplina")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INSTITUICAO')")
    public ResponseEntity<Object> editarCurso(@RequestBody Disciplina disciplina) {
        try {
            Optional<Disciplina> optionalDisciplina = disciplinaRepository.findById(disciplina.getId());
            if(!optionalDisciplina.isPresent()) {
                return ResponseEntity.badRequest().build();
            }
            disciplinaRepository.save(disciplina);

            return ResponseEntity.ok().build();
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/deletarDisciplina")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INSTITUICAO')")
    public ResponseEntity<Object> deletarDisciplina(@RequestParam Long disciplinaId) {
        disciplinaRepository.deleteById(disciplinaId);

        return  ResponseEntity.ok().build();
    }
}
