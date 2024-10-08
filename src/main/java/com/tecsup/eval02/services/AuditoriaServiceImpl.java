package com.tecsup.eval02.services;

import com.tecsup.eval02.domain.entities.Auditoria;
import com.tecsup.eval02.domain.persistence.AuditoriaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuditoriaServiceImpl implements AuditoriaService { // Implementa la interfaz AuditoriaService

    @Autowired
    private AuditoriaDao auditoriaDao;

    @Override
    @Transactional(readOnly = true)
    public List<Auditoria> listar() {
        return auditoriaDao.findAll();
    }

    @Override
    @Transactional
    public void registrarAuditoria(Auditoria auditoria) {
        auditoriaDao.save(auditoria);
    }
}
