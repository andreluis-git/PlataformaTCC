package com.projeto.plataforma.web.controllers;

import com.projeto.plataforma.persistence.dao.AlunoRepository;
import com.projeto.plataforma.persistence.model.Aluno;
import com.projeto.plataforma.persistence.model.Disciplina;
import com.projeto.plataforma.persistence.model.Tema;
import com.projeto.plataforma.persistence.model.Usuario;
import com.projeto.plataforma.web.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private CurrentUser currentUser;


    @GetMapping("/buscarAluno/{id}")
    public ResponseEntity<Object> buscarAlunoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(alunoRepository.findById(id).get());
    }

    @GetMapping("/buscarAlunoPorNome/{id}")
    public ResponseEntity<Object> buscarAlunoPorEmail(@PathVariable String email) {
        return ResponseEntity.ok(alunoRepository.findByEmail(email).get());
    }

    @GetMapping("/buscarAluno")
    public ResponseEntity<Object> buscarAlunoPorHeader(@RequestHeader HttpHeaders headers) {
        try {
            Usuario user = currentUser.getCurrentUser(headers);
            Aluno aluno = alunoRepository.findById(user.getId()).get();

            return ResponseEntity.ok(aluno);
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getCause());
        }
    }

    @PostMapping("/cadastrarAluno")
    public ResponseEntity<Object> cadastrarAluno(@RequestBody Aluno aluno) {

        try {
            aluno.setPassword(encoder.encode(aluno.getPassword()));
            return ResponseEntity.ok(alunoRepository.save(aluno));
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/editarAluno")
    public ResponseEntity<Object> editarAluno(@RequestHeader HttpHeaders headers, @RequestBody Aluno aluno) {
        try {
            Optional<Aluno> optAluno = alunoRepository.findById(aluno.getId());
            if(optAluno.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Aluno alunoDb = optAluno.get();

            alunoDb.setDisciplinasInteresse(aluno.getDisciplinasInteresse());
            alunoDb.setSobre(aluno.getSobre());
            alunoDb.setLinkedin(aluno.getLinkedin());
            alunoDb.setInstagram(aluno.getInstagram());
            alunoDb.setFacebook(aluno.getFacebook());
            alunoDb.setWhatsapp(aluno.getWhatsapp());

            alunoRepository.save(alunoDb);

            return ResponseEntity.ok().build();
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: " + ex.getMessage().toString());
        }
    }

    @DeleteMapping("/deletarCurso")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INSTITUICAO')")
    public ResponseEntity<Object> deletarCurso(@RequestParam Long alunoId) {
        alunoRepository.deleteById(alunoId);

        return  ResponseEntity.ok().build();
    }
}
