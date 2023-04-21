package com.projeto.plataforma.web.controllers;

import com.projeto.plataforma.persistence.dao.AlunoRepository;
import com.projeto.plataforma.persistence.dao.TemaRepository;
import com.projeto.plataforma.persistence.model.Aluno;
import com.projeto.plataforma.persistence.model.Curso;
import com.projeto.plataforma.persistence.model.Disciplina;
import com.projeto.plataforma.persistence.dao.DisciplinaRepository;
import com.projeto.plataforma.persistence.model.Usuario;
import com.projeto.plataforma.web.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private CurrentUser currentUser;

    @GetMapping("/buscarDisciplina")
    public ResponseEntity<Object> buscarDisciplinaPorId(@RequestParam Long id) {
        return ResponseEntity.ok(disciplinaRepository.findById(id).get());
    }

    @GetMapping("/buscarDisciplinaPorNome")
    public ResponseEntity<Object> buscarDisciplinaPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(disciplinaRepository.findByNome(nome).get());
    }

    @GetMapping("/listarDisciplinas")
    public ResponseEntity<Object> listarDisciplinas() {
        return ResponseEntity.ok(disciplinaRepository.findAll());
    }

    @GetMapping("/listarDisciplinasPorCurso")
    public ResponseEntity<Object> listarDisciplinasPorCurso(@RequestHeader HttpHeaders headers) {
        Usuario user = currentUser.getCurrentUser(headers);
        Aluno aluno = alunoRepository.findById(user.getId()).get();

        return ResponseEntity.ok(disciplinaRepository.findAllByCursoDisciplinaId(aluno.getCursoAluno().getId()));
    }

    @PostMapping(value = "/cadastrarDisciplina", consumes = MediaType.APPLICATION_JSON_VALUE)
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
