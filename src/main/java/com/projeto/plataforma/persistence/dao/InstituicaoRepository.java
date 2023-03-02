package com.projeto.plataforma.persistence.dao;

import com.projeto.plataforma.persistence.model.Instituicao;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstituicaoRepository extends JpaRepository<Instituicao, Long> {

    Optional<Instituicao> findByEmail(String email);
    Optional<Instituicao> findByNome(String nome);

}
