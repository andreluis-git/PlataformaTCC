package com.projeto.plataforma.web.controllers;

import com.projeto.plataforma.persistence.model.Aluno;
import com.projeto.plataforma.persistence.dao.AlunoRepository;
import com.projeto.plataforma.web.util.CurrentUser;
import com.projeto.plataforma.web.util.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private CurrentUser currentUser;
    @Autowired
    private ValidationUtils validationUtils;

    @GetMapping("/buscarAluno")
    public ResponseEntity<Object> buscarAlunoPorId(@RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(alunoRepository.findById(currentUser.getCurrentUser(headers).getId()));
    }

    @PostMapping("/cadastrarAluno")
    public ResponseEntity<Object> cadastrarAluno(@RequestHeader(value=ValidationUtils.HEADER_INSTITUICAO) String instituicaoKey, @RequestBody Aluno aluno) {

        try {
            if(!validationUtils.validarInstituicao(instituicaoKey)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

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
            if(!validationUtils.validarUsuario(headers, aluno.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

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
}