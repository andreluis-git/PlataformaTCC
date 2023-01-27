package com.projeto.plataforma.Repository;

import com.projeto.plataforma.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CadastroRepository extends JpaRepository<Usuario, Long> {
}
