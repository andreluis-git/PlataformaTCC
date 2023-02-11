package com.projeto.plataforma.persistence.dao;


import com.projeto.plataforma.persistence.model.Tema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemaRepository extends JpaRepository <Tema, Long> {
    List<Tema> findByIdIn(List<Tema> ids);
}
