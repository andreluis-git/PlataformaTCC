//package com.projeto.plataforma.web.util;
//
//import com.projeto.plataforma.Config.Properties.ConfigProps;
//import com.projeto.plataforma.persistence.model.Aluno;
//import com.projeto.plataforma.persistence.model.Usuario;
//import com.projeto.plataforma.persistence.dao.AlunoRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ValidationUtils {
//    public static final String HEADER_INSTITUICAO = "Instituicao-Key";
//    @Autowired
//    private ConfigProps configProps;
//    @Autowired
//    private CurrentUser currentUser;
//    @Autowired
//    private AlunoRepository alunoRepository;
//    public Boolean validarInstituicao(String instituicaoKey) {
//        try {
//            if(instituicaoKey.equals(configProps.getProp("dev.instituicaokey"))) {
//                return true;
//            }
//            return false;
//        } catch (Exception ex) {
//            return false;
//        }
//    }
//
//    public Boolean validarUsuario(HttpHeaders headers, Long usuarioId) {
//        try {
//            Usuario usuarioHeader = currentUser.getCurrentUser(headers);
//            Aluno aluno = alunoRepository.getById(usuarioId);
//            if (usuarioHeader.getId() == aluno.getId()) {
//                return true;
//            }
//            return false;
//        } catch (Exception ex) {
//            return false;
//        }
//    }
//}
