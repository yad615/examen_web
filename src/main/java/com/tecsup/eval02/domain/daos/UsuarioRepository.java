package com.tecsup.eval02.domain.daos;

import com.tecsup.eval02.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}