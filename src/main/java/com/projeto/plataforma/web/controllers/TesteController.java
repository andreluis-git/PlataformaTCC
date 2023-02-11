package com.projeto.plataforma.web.controllers;

import com.projeto.plataforma.persistence.model.Instituicao;
import com.projeto.plataforma.persistence.dao.InstituicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teste")
public class TesteController {
    @Autowired
    InstituicaoRepository instituicaoRepository;

    @GetMapping("/buscarInsti")
    public ResponseEntity buscar() {
        Instituicao instituicao = instituicaoRepository.findById(1L).get();

        return ResponseEntity.ok().build();
    }
}
