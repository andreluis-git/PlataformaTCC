package com.projeto.plataforma.Repository;

import com.projeto.plataforma.Model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
