package com.projeto.plataforma.persistence.dao;

import com.projeto.plataforma.persistence.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Optional<Aluno> findByEmail(String login);

    @Query(value = "select u from usuario u \n" +
            "JOIN FETCH u.disciplinasInteresse d \n" +
            "where u.id = :id")
    Aluno findAlunoQuery(@Param("id") Long id);

    @Query(value = "select * from usuario u \n" +
            "JOIN curso c on c.id = u.curso_aluno_id and c.instituicao_curso_id = :id ORDER BY u.nome ASC", nativeQuery = true)
    List<Aluno> findAlunosByInstituicaoIdQuery(@Param("id") Long id);

    @Query(value = "select * from usuario u \n" +
            "JOIN curso c on c.id = u.curso_aluno_id and c.instituicao_curso_id = :id \n" +
            "where (u.email LIKE CONCAT('%',:texto,'%'))", nativeQuery = true)
    List<Aluno> findAlunosByInstituicaoIdAndEmailQuery(@Param("id") Long id, @Param("texto") String texto);

    List<Aluno> findAllByCandidaturasAlunoIdAndAtivo(Long id, boolean ativo);

}
