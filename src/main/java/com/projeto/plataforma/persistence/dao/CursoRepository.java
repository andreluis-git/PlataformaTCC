package com.projeto.plataforma.persistence.dao;

import com.projeto.plataforma.persistence.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    List<Curso> findAllByInstituicaoCursoIdOrderByNomeAsc(Long instituicaoId);
    List<Curso> findAllByInstituicaoCursoIdAndNomeContainsIgnoreCaseOrderByNomeAsc(Long instituicaoId, String nome);
    Optional<Curso> findByNome(String nome);
}
