package com.projeto.plataforma.persistence.dao;


import com.projeto.plataforma.persistence.model.Aluno;
import com.projeto.plataforma.persistence.model.Tema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TemaRepository extends JpaRepository <Tema, Long> {
    List<Tema> findByIdIn(List<Tema> ids);
    List<Tema> findAllByCriadorTemaIdOrderByIdDesc(Long id);
    List<Tema> findAllByCriadorTemaIdAndTituloOrderByIdDesc(Long id, String titulo);
    List<Tema> findAllByDisciplinasRelacionadasIdInOrderByIdDesc(List<Long> ids);
    List<Tema> findByCriadorTemaId(Long id);
    List<Aluno> findAllCandidatosTemaById(Long temaId);
    Optional<Tema> findByTitulo(String titulo);
    List<Tema> findAllByTituloContainsOrderByIdDesc(String titulo);

//    @Query("SELECT t FROM Tema t JOIN FETCH t.cursoTema WHERE t.cursoTema.id = :curso")
    @Transactional
    @Query(value="select * from tema t", nativeQuery = true)
    List<Tema> findAllQuery(@Param("curso") Long curso);

    List<Tema> findAllByCandidatosTemaIdOrderByIdDesc(Long id);

}
