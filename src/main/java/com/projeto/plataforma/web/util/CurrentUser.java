package com.projeto.plataforma.web.util;

import com.google.gson.JsonParser;
import com.projeto.plataforma.persistence.model.Usuario;
import com.projeto.plataforma.persistence.dao.AlunoRepository;
import com.projeto.plataforma.persistence.dao.UsuarioRepository;
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

        Usuario user = alunoRepository.findByEmail(JsonParser.parseString(payload).getAsJsonObject().get("sub").getAsString()).get();

        return user;
    }


}
