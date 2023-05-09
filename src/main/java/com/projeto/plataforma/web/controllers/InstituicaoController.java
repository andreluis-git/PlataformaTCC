package com.projeto.plataforma.web.controllers;

import com.projeto.plataforma.persistence.dao.*;
import com.projeto.plataforma.persistence.model.*;
import com.projeto.plataforma.web.dto.InstituicaoDTO;
import com.projeto.plataforma.web.util.CurrentUser;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
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
    private RoleRepository roleRepository;
    @Autowired
    private TemaRepository temaRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private CurrentUser currentUser;

    @GetMapping("/buscarInstituicao")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INSTITUICAO')")
    public ResponseEntity<Object> buscarInstituicao(@RequestHeader HttpHeaders headers) {
        try {
            Usuario user = currentUser.getCurrentUser(headers);
            Instituicao instituicao = instituicaoRepository.findById(user.getId()).get();

            return ResponseEntity.ok(instituicao);
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getCause());
        }
    }

    @GetMapping("/buscarInstituicaoPorNome")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> buscarInstituicaoPorNome(@RequestParam String nome) {

        try {
            return ResponseEntity.ok(instituicaoRepository.findByNome(nome).get());
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/listarInstituicoes")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> listarInstituicoes() {

        try {
            return ResponseEntity.ok(instituicaoRepository.findAll());
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/cadastrarInstituicao", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> cadastrarInstituicao(@RequestBody InstituicaoDTO instituicaoDTO) {

        try {
            Instituicao instituicao = new Instituicao();
            instituicao.setNome(instituicaoDTO.getNome());
            instituicao.setPassword(encoder.encode(instituicaoDTO.getPassword()));
            instituicao.setEmail(instituicaoDTO.getEmail());
            instituicao.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByName("ROLE_INSTITUICAO"))));
            instituicao.setAtivo(true);
            return ResponseEntity.ok(instituicaoRepository.save(instituicao));
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/editarInstituicao", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INSTITUICAO')")
    public ResponseEntity<Object> editarInstituicao(@RequestBody InstituicaoDTO instituicaoDTO) {
        try {
            Optional<Instituicao> optionalInstituicao = instituicaoRepository.findById(instituicaoDTO.getId());
            if(!optionalInstituicao.isPresent()) {
                return ResponseEntity.badRequest().build();
            }

            Instituicao instituicaoBanco = optionalInstituicao.get();
            if(!instituicaoDTO.getNome().isEmpty()) {
                instituicaoBanco.setNome(instituicaoDTO.getNome());
            }

            if(!instituicaoDTO.getSenhaAtual().isEmpty()
                    && !instituicaoDTO.getSenhaNova().isEmpty()
                    && encoder.matches(instituicaoDTO.getSenhaAtual(), instituicaoBanco.getPassword())
            ) {
                instituicaoBanco.setPassword(encoder.encode(instituicaoDTO.getSenhaNova()));
            }

            instituicaoRepository.save(instituicaoBanco);

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
