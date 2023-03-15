package com.projeto.plataforma.web.controllers;

import com.projeto.plataforma.persistence.dao.AlunoRepository;
import com.projeto.plataforma.persistence.dao.TemaRepository;
import com.projeto.plataforma.persistence.model.Aluno;
import com.projeto.plataforma.persistence.model.Tema;
import com.projeto.plataforma.web.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tema")
public class TemaConstroller {

    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private TemaRepository temaRepository;
    @Autowired
    private CurrentUser currentUser;

    @GetMapping("/buscarTema")
    public ResponseEntity<Object> buscarTemaPorId(@RequestParam Long id) {

        try {
            return ResponseEntity.ok(temaRepository.findById(id).get());
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/buscarTemaPorNome")
    public ResponseEntity<Object> buscarTemaPorNome(@RequestParam String nome) {

        try {
            return ResponseEntity.ok(temaRepository.findByTitulo(nome).get());
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/listarTemas")
    public ResponseEntity<Object> listarTemas(@RequestHeader HttpHeaders headers) {

        try {
            Aluno aluno = alunoRepository.getById(currentUser.getCurrentUser(headers).getId());

            return ResponseEntity.ok(temaRepository.findByIdIn(aluno.getTemasAluno()));
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping("/gravarTema")
    public ResponseEntity<Object> gravarTema(@RequestBody Tema tema) {

        try {
            if(tema == null) {
                return ResponseEntity.badRequest().build();
            }
            tema = temaRepository.save(tema);

            return ResponseEntity.ok(tema);
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/editarTema")
    public ResponseEntity<Object> editarTema(@RequestBody Tema tema) {

        try {
            if(tema == null) {
                return ResponseEntity.badRequest().build();
            }
            tema = temaRepository.save(tema);

            return ResponseEntity.ok(tema);
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/deletarTema")
    public ResponseEntity<Object> deletarTema(@RequestHeader HttpHeaders headers, @RequestParam Long temaId) {
        temaRepository.deleteById(temaId);

        return ResponseEntity.ok().build();
    }
}
