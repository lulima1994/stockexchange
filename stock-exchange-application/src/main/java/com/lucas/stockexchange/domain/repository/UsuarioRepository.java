package com.lucas.stockexchange.domain.repository;

import com.lucas.stockexchange.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
