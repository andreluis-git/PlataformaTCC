package com.projeto.plataforma.persistence.dao;

import com.projeto.plataforma.persistence.model.Instituicao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InstituicaoRepository extends JpaRepository<Instituicao, Long> {

    Optional<Instituicao> findByEmail(String email);
    List<Instituicao> findByNomeContainsIgnoreCaseOrderByNomeAsc(String nome);
    List<Instituicao> findAllByOrderByNomeAsc();
}
