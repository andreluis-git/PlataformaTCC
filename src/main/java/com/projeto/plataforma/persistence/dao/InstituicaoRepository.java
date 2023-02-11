package com.projeto.plataforma.persistence.dao;

import com.projeto.plataforma.persistence.model.Instituicao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstituicaoRepository extends JpaRepository<Instituicao, Long> {
}
