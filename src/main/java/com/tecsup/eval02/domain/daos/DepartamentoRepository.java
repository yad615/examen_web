package com.tecsup.eval02.domain.daos;

import com.tecsup.eval02.domain.entities.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentoRepository extends JpaRepository<Departamento, Integer> {
}