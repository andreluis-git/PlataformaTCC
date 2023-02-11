package com.projeto.plataforma.Utils;

import com.projeto.plataforma.Model.Aluno;
import com.projeto.plataforma.Model.Usuario;
import com.projeto.plataforma.Repository.AlunoRepository;
import com.projeto.plataforma.Repository.UsuarioRepository;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class CurrentUser {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario getCurrentUser(HttpHeaders headers) {
        String token = headers.getFirst(HttpHeaders.AUTHORIZATION);
        assert token != null;
        String[] chunks = token.split("\\.");

        Base64.Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(chunks[1]));

        return alunoRepository.findByEmail(JsonParser.parseString(payload).getAsJsonObject().get("sub").getAsString()).get();
    }


}
