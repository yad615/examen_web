package com.tecsup.eval02.services;

import com.tecsup.eval02.domain.entities.Auditoria;

import java.util.List;

public interface AuditoriaService {
    void registrarAuditoria(Auditoria auditoria);
    List<Auditoria> listar();
}
