package com.projeto.plataforma.Controllers;

import com.projeto.plataforma.Repository.PeriodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/periodo")
public class PeriodoController {

    @Autowired
    PeriodoRepository periodoRepository;

    @GetMapping("/listarPeriodos")
    public ResponseEntity<Object> listarPeriodos() {
        return ResponseEntity.ok(periodoRepository.findAll());
    }
}
