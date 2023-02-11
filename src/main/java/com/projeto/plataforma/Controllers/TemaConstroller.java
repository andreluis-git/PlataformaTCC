package com.projeto.plataforma.Controllers;

import com.projeto.plataforma.Model.Aluno;
import com.projeto.plataforma.Model.Tema;
import com.projeto.plataforma.Repository.AlunoRepository;
import com.projeto.plataforma.Repository.TemaRepository;
import com.projeto.plataforma.Repository.UsuarioRepository;
import com.projeto.plataforma.Utils.CurrentUser;
import com.projeto.plataforma.Utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/tema")
public class TemaConstroller {

    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private TemaRepository temaRepository;
    @Autowired
    private CurrentUser currentUser;
    @Autowired
    private ValidationUtils validationUtils;

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

    @DeleteMapping("/deletarTema")
    public ResponseEntity<Object> deletarTema(@RequestHeader HttpHeaders headers, @RequestParam Long temaId) {
        temaRepository.deleteById(temaId);

        return ResponseEntity.ok().build();
    }
}
