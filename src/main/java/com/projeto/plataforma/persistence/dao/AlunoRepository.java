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

}
