package com.projeto.plataforma.web.controllers;

import com.projeto.plataforma.persistence.dao.AlunoRepository;
import com.projeto.plataforma.persistence.dao.CursoRepository;
import com.projeto.plataforma.persistence.dao.TemaRepository;
import com.projeto.plataforma.persistence.model.Aluno;
import com.projeto.plataforma.persistence.model.Disciplina;
import com.projeto.plataforma.persistence.dao.DisciplinaRepository;
import com.projeto.plataforma.persistence.model.Tema;
import com.projeto.plataforma.persistence.model.Usuario;
import com.projeto.plataforma.web.dto.DisciplinaDTO;
import com.projeto.plataforma.web.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/disciplina")
public class DisciplinaController {

    @Autowired
    private DisciplinaRepository disciplinaRepository;
    @Autowired
    private TemaRepository temaRepository;
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private CursoRepository cursoRepository;
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

    @GetMapping("/buscarDisciplinaCursoPorNome")
    public ResponseEntity<Object> buscarDisciplinaCursoPorNome(@RequestParam Long id, @RequestParam String nome) {
        return ResponseEntity.ok(disciplinaRepository.findAllByCursoDisciplinaIdAndNomeContainsIgnoreCase(id, nome));
    }

    @GetMapping("/listarDisciplinas")
    public ResponseEntity<Object> listarDisciplinas() {
        return ResponseEntity.ok(disciplinaRepository.findAll());
    }

    @GetMapping("/listarDisciplinasPorCursoId/{id}")
    public ResponseEntity<Object> listarDisciplinasPorCursoId(@PathVariable Long id) {
        return ResponseEntity.ok(disciplinaRepository.findAllByCursoDisciplinaIdOrderByNomeAsc(id));
    }

    @GetMapping("/listarDisciplinasPorCurso")
    public ResponseEntity<Object> listarDisciplinasPorCurso(@RequestHeader HttpHeaders headers) {
        Usuario user = currentUser.getCurrentUser(headers);
        Aluno aluno = alunoRepository.findById(user.getId()).get();

        return ResponseEntity.ok(disciplinaRepository.findAllByCursoDisciplinaIdOrderByNomeAsc(aluno.getCursoAluno().getId()));
    }

    @PostMapping(value = "/cadastrarDisciplina", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INSTITUICAO')")
    public ResponseEntity<Object> cadastrarDisciplina(@RequestBody DisciplinaDTO disciplinaDTO) {
        try {
            Disciplina disciplina = new Disciplina();
            disciplina.setNome(disciplinaDTO.getNome());
            disciplina.setSigla(disciplinaDTO.getSigla());
            disciplina.setCursoDisciplina(cursoRepository.getById(disciplinaDTO.getCursoDisciplina().getId()));

            return ResponseEntity.ok(disciplinaRepository.save(disciplina));
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value="/editarDisciplina", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INSTITUICAO')")
    public ResponseEntity<Object> editarDisciplina(@RequestBody DisciplinaDTO disciplinaDTO) {
        try {
            Optional<Disciplina> optionalDisciplina = disciplinaRepository.findById(disciplinaDTO.getId());
            if(!optionalDisciplina.isPresent()) {
                return ResponseEntity.badRequest().build();
            }
            Disciplina disciplina = optionalDisciplina.get();
            disciplina.setNome(disciplinaDTO.getNome());
            disciplina.setSigla(disciplinaDTO.getSigla());

            disciplinaRepository.save(disciplina);

            return ResponseEntity.ok().build();
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/deletarDisciplina/{disciplinaId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INSTITUICAO')")
    public ResponseEntity<Object> deletarDisciplina(@PathVariable Long disciplinaId) {
        try {
            Disciplina disciplina = disciplinaRepository.findById(disciplinaId).get();
            for (Tema tema : disciplina.getTemasDisciplina()) {
                tema.removerDisciplinaTema(disciplina);
                temaRepository.save(tema);
            }

            for (Aluno aluno : disciplina.getAlunosDisciplina()) {
                aluno.removerDisciplinaAluno(disciplina);
                alunoRepository.save(aluno);
            }

            disciplinaRepository.deleteById(disciplinaId);

            return  ResponseEntity.ok().build();
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
