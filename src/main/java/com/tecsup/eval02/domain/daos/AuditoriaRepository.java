package com.tecsup.eval02.domain.daos;

import com.tecsup.eval02.domain.entities.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditoriaRepository extends JpaRepository<Auditoria, Integer> {
}