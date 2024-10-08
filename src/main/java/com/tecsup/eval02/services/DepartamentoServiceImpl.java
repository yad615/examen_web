package com.tecsup.eval02.services;

import com.tecsup.eval02.domain.entities.Departamento;
import com.tecsup.eval02.domain.persistence.DepartamentoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartamentoServiceImpl implements DepartamentoService {

    @Autowired
    private DepartamentoDao departamentoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Departamento> listar() {
        return departamentoDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Departamento buscar(int id) {
        return departamentoDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void grabar(Departamento departamento) {
        departamentoDao.save(departamento);
    }

    @Override
    @Transactional
    public void eliminar(int id) {
        departamentoDao.deleteById(id);
    }
}
