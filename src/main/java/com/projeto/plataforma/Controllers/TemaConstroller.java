package com.projeto.plataforma.Controllers;

import com.projeto.plataforma.Model.Tema;
import com.projeto.plataforma.Model.Usuario;
import com.projeto.plataforma.Repository.TemaRepository;
import com.projeto.plataforma.Repository.UsuarioRepository;
import com.projeto.plataforma.Utils.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/tema")
public class TemaConstroller {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TemaRepository temaRepository;
    @Autowired
    private CurrentUser currentUser;

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
    public ResponseEntity<Object> listarTemas() {
        return ResponseEntity.ok(temaRepository.findAll());
    }

    @PutMapping("/deletarTema")
    public ResponseEntity<Object> deletarTema(@RequestHeader HttpHeaders headers, @RequestParam Long id) {
        Optional<Tema> optTema = temaRepository.findById(id);
        if(optTema.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Tema tema = optTema.get();

        Usuario usuario = usuarioRepository.getById(currentUser.getCurrentUser(headers).getId());

        if(tema.getUsuario() == usuario) {
            tema.setExcluido(true);
            temaRepository.save(tema);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }
}
