package com.projeto.plataforma.persistence.dao;


import com.projeto.plataforma.persistence.model.Curso;
import com.projeto.plataforma.persistence.model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DisciplinaRepository extends JpaRepository <Disciplina, Long> {
    List<Disciplina> findAllByCursoDisciplinaId(Long cursoId);
    Optional<Disciplina> findByNome(String nome);
}
