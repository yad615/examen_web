package com.tecsup.eval02.services;

import com.tecsup.eval02.domain.entities.Departamento;

import java.util.List;

public interface DepartamentoService {
    List<Departamento> listar();
    Departamento buscar(int id);
    void grabar(Departamento departamento);
    void eliminar(int id);
}
