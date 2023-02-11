package com.projeto.plataforma.Repository;


import com.projeto.plataforma.Model.Disciplina;
import com.projeto.plataforma.Model.Tema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemaRepository extends JpaRepository <Tema, Long> {
    List<Tema> findByIdIn(List<Tema> ids);
}
