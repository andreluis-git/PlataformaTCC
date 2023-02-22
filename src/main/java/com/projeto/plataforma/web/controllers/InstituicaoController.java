package com.projeto.plataforma.web.controllers;

import com.projeto.plataforma.persistence.dao.*;
import com.projeto.plataforma.persistence.model.*;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/instituicao")
public class InstituicaoController {

    @Autowired
    private InstituicaoRepository instituicaoRepository;
    @Autowired
    private DisciplinaRepository disciplinaRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private TemaRepository temaRepository;
    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/cadastrarInstituicao")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> cadastrarInstituicao(@RequestBody Instituicao instituicao) {

        try {
            instituicao.setPassword(encoder.encode(instituicao.getPassword()));
            return ResponseEntity.ok(instituicaoRepository.save(instituicao));
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/editarInstituicao")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> editarInstituicao(@RequestBody Instituicao instituicao) {
        try {
            Optional<Instituicao> optionalInstituicao = instituicaoRepository.findById(instituicao.getId());
            if(!optionalInstituicao.isPresent()) {
                return ResponseEntity.badRequest().build();
            }
            instituicaoRepository.save(instituicao);

            return ResponseEntity.ok().build();
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/deletarInstituicao")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> deletarInstituicao(@RequestParam Long instituicaoId) {

        instituicaoRepository.deleteById(instituicaoId);

        return  ResponseEntity.ok().build();
    }
}
