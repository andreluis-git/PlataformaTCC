package com.projeto.plataforma.web.controllers;

import com.projeto.plataforma.persistence.model.Instituicao;
import com.projeto.plataforma.persistence.dao.InstituicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instituicao")
public class InstituicaoController {

    @Autowired
    private InstituicaoRepository instituicaoRepository;
    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/cadastrarInstituicao")
    public ResponseEntity<Object> cadastrarUsuario(@RequestBody Instituicao instituicao) {

        try {
            instituicao.setPassword(encoder.encode(instituicao.getPassword()));
            return ResponseEntity.ok(instituicaoRepository.save(instituicao));
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }

}
