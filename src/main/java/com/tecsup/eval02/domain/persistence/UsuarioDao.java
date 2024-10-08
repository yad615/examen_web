package com.tecsup.eval02.domain.persistence;

import com.tecsup.eval02.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioDao extends JpaRepository<Usuario, Integer> {
}
