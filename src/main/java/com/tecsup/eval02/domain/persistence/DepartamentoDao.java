package com.tecsup.eval02.domain.persistence;

import com.tecsup.eval02.domain.entities.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentoDao extends JpaRepository<Departamento, Integer> {
}
