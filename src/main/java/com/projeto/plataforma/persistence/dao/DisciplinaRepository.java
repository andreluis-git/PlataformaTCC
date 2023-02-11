package com.projeto.plataforma.persistence.dao;


import com.projeto.plataforma.persistence.model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplinaRepository extends JpaRepository <Disciplina, Long> {
}
