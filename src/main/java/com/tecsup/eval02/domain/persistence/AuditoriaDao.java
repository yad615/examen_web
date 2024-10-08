package com.tecsup.eval02.domain.persistence;

import com.tecsup.eval02.domain.entities.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditoriaDao extends JpaRepository<Auditoria, Integer> {
}
