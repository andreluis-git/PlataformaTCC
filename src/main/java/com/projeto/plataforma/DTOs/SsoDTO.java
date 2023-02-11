package com.projeto.plataforma.DTOs;


import com.projeto.plataforma.Model.Aluno;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;

public class SsoDTO {

//    private Long id;
//    private String nome;
//    private String login;
//    private String email;

    private String access_token;
    private String token_type;
    private Aluno me;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public Aluno getMe() {
        return me;
    }

    public void setMe(Aluno me) {
        this.me = me;
    }

    public static SsoDTO toDTO(Aluno user, String token) {
        ModelMapper modelMapper = new ModelMapper();
        SsoDTO dto = modelMapper.map(user, SsoDTO.class);
        dto.access_token = token;
        return dto;
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper m = new ObjectMapper();
        return m.writeValueAsString(this);
    }

}
