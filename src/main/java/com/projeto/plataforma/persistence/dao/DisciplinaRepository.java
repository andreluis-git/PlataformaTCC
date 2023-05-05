package com.projeto.plataforma.persistence.dao;


import com.projeto.plataforma.persistence.model.Disciplina;
import com.projeto.plataforma.persistence.model.Tema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DisciplinaRepository extends JpaRepository <Disciplina, Long> {
    List<Disciplina> findAllByCursoDisciplinaIdOrderByNomeAsc(Long cursoId);
    Optional<Disciplina> findByNome(String nome);
    Set<Disciplina> findAllByIdIn(List<Long> disciplinaIds);
    List<Disciplina> findAllByCursoDisciplinaIdAndNomeContainsIgnoreCase(Long cursoId, String nome);
}
