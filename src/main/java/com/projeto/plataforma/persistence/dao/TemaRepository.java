package com.projeto.plataforma.persistence.dao;


import com.projeto.plataforma.persistence.model.Aluno;
import com.projeto.plataforma.persistence.model.Disciplina;
import com.projeto.plataforma.persistence.model.Tema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TemaRepository extends JpaRepository <Tema, Long> {
    List<Tema> findByIdIn(List<Tema> ids);
    List<Tema> findAllByCriadorTemaIdIn(List<Aluno> ids);
    List<Tema> findAllByDisciplinasRelacionadasIdIn(List<Disciplina> ids);
    List<Tema> findByCriadorTemaId(Long id);
    List<Aluno> findAllCandidatosTemaById(Long temaId);
    Optional<Tema> findByNome(String nome);
}
