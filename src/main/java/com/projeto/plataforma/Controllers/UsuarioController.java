package com.projeto.plataforma.Controllers;

import com.projeto.plataforma.Model.Usuario;
import com.projeto.plataforma.Repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;

    public UsuarioController(UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
    }

//    @GetMapping("/listar-todos")
    public ResponseEntity<List<Usuario>> listarTodos() {

        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    @PostMapping("/cadastrar_usuario")
    public ResponseEntity<Object> cadastrarUsuario(@RequestBody Usuario usuario) {

        try {
            usuario.setPassword(encoder.encode(usuario.getPassword()));
            return ResponseEntity.ok(usuarioRepository.save(usuario));
        }
        catch (Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }

//    @GetMapping("/validar-senha")
    public ResponseEntity<Object> validarSenha(@RequestParam String login, @RequestParam String password) {

        Optional<Usuario> usuarioOpt = usuarioRepository.findByLogin(login);
        if(!usuarioOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

        boolean valid = encoder.matches(password, usuarioOpt.get().getPassword());

        HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;

        return ResponseEntity.status(status).body(valid);
    }
}
