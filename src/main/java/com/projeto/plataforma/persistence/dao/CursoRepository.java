package com.projeto.plataforma.persistence.dao;

import com.projeto.plataforma.persistence.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
