package com.projeto.plataforma.web.controllers;

import com.projeto.plataforma.persistence.dao.CursoRepository;
import com.projeto.plataforma.persistence.dao.InstituicaoRepository;
import com.projeto.plataforma.persistence.model.Curso;
import com.projeto.plataforma.persistence.model.Instituicao;
import com.projeto.plataforma.persistence.model.Usuario;
import com.projeto.plataforma.web.dto.CursoDTO;
import com.projeto.plataforma.web.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/curso")
public class CursoController {
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private InstituicaoRepository instituicaoRepository;
    @Autowired
    private CurrentUser currentUser;

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

    @GetMapping("/listarCursosPorInstituicao")
    public ResponseEntity<Object> listarCursosPorInstituicao(@RequestHeader HttpHeaders headers) {
        Usuario user = currentUser.getCurrentUser(headers);
        List<Curso> cursos = cursoRepository.findAllByInstituicaoCursoIdOrderByNomeAsc(user.getId());
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/listarCursosPorInstituicaoAndNome")
    public ResponseEntity<Object> listarCursosPorInstituicaoAndNome(@RequestHeader HttpHeaders headers, @RequestParam String nome) {
        Usuario user = currentUser.getCurrentUser(headers);
        List<Curso> cursos = cursoRepository.findAllByInstituicaoCursoIdAndNomeContainsIgnoreCaseOrderByNomeAsc(user.getId(), nome);
        return ResponseEntity.ok(cursos);
    }

    @PostMapping(value = "/cadastrarCurso", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INSTITUICAO')")
    public ResponseEntity<Object> cadastrarCurso(@RequestHeader HttpHeaders headers, @Valid @RequestBody CursoDTO cursoDTO) {
        try {
            Instituicao instituicao = instituicaoRepository.findById(currentUser.getCurrentUser(headers).getId()).get();
            Curso curso = new Curso();
            curso.setNome(cursoDTO.getNome());
            curso.setSigla(cursoDTO.getSigla());
            curso.setInstituicaoCurso(instituicao);
            curso = cursoRepository.save(curso);

            return ResponseEntity.ok(curso);
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/editarCurso", consumes = MediaType.APPLICATION_JSON_VALUE)
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

    @DeleteMapping("/deletarCurso/{cursoId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INSTITUICAO')")
    public ResponseEntity<Object> deletarCurso(@PathVariable Long cursoId) {
        try {
            cursoRepository.deleteById(cursoId);

            return  ResponseEntity.ok().build();
        } catch(Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
