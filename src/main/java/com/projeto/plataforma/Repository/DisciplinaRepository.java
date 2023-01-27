package com.projeto.plataforma.Repository;


import com.projeto.plataforma.Model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DisciplinaRepository extends JpaRepository <Disciplina, Long> {

    List<Disciplina> findAll();

}
