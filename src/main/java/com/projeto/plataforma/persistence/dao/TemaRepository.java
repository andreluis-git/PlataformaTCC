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
    @Transactional
    @Query(value="SELECT * FROM (select DISTINCT t.* from tema t JOIN tema_disciplina td ON td.tema_id = t.id WHERE td.disciplina_id IN (:ids) AND t.criador_tema_id != :criadorId AND t.ativo = true AND t.curso_tema_id = :cursoId ORDER BY id DESC) tb1\n" +
            "UNION ALL\n" +
            "SELECT * FROM (select DISTINCT t.* from tema t LEFT JOIN tema_disciplina td ON td.tema_id = t.id WHERE t.id NOT IN (" +
            "select DISTINCT t.id from tema t JOIN tema_disciplina td ON td.tema_id = t.id WHERE td.disciplina_id IN (:ids) AND t.criador_tema_id != :criadorId AND t.ativo = true AND t.curso_tema_id = :cursoId ORDER BY id DESC" +
            ") AND t.criador_tema_id != :criadorId AND t.ativo = true AND t.curso_tema_id = :cursoId ORDER BY id DESC) tb2\n", nativeQuery = true)
    List<Tema> findAllTemasQuery(@Param("ids") List<Long> ids, @Param("criadorId") Long id, @Param("cursoId") Long cursoId);
    List<Tema> findByCriadorTemaId(Long id);
    List<Aluno> findAllCandidatosTemaById(Long temaId);
    Optional<Tema> findByTitulo(String titulo);
    List<Tema> findAllByTituloContainsOrDescricaoContainsOrderByIdDesc(String titulo, String descricao);
    @Transactional
    @Query(value="SELECT * FROM tema t WHERE (t.titulo LIKE CONCAT('%',:texto,'%') OR t.descricao LIKE CONCAT('%',:texto,'%')) AND t.criador_tema_id != :criadorId ORDER BY t.id DESC", nativeQuery = true)
    List<Tema> findAllByTituloAndDescricaoQuery(@Param("texto") String texto,@Param("criadorId") Long criadorId);
    @Transactional
    @Query(value="select * from tema t JOIN aluno_candidaturas ac on ac.tema_id = t.id WHERE ac.aluno_id = :alunoId AND (t.titulo LIKE CONCAT('%',:texto,'%') OR t.descricao LIKE CONCAT('%',:texto,'%')) ORDER BY t.id DESC", nativeQuery = true)
    List<Tema> findAllCandidaturasByTituloAndDescricaoQuery(@Param("texto") String texto,@Param("alunoId") Long alunoId);
    @Transactional
    @Query(value="select * from tema t where t.criador_tema_id = :alunoId AND (t.titulo LIKE CONCAT('%',:texto,'%') OR t.descricao LIKE CONCAT('%',:texto,'%')) ORDER BY t.id DESC", nativeQuery = true)
    List<Tema> findAllAnunciadosByTituloAndDescricaoQuery(@Param("texto") String texto,@Param("alunoId") Long alunoId);
//    @Query("SELECT t FROM Tema t JOIN FETCH t.cursoTema WHERE t.cursoTema.id = :curso")
    @Transactional
    @Query(value="select * from tema t", nativeQuery = true)
    List<Tema> findAllQuery(@Param("curso") Long curso);

    List<Tema> findAllByCandidatosTemaIdOrderByIdDesc(Long id);

}
