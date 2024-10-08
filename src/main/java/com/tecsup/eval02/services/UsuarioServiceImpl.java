package com.tecsup.eval02.services;

import com.tecsup.eval02.domain.entities.Usuario;
import com.tecsup.eval02.domain.persistence.UsuarioDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioDao usuarioDao;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        return usuarioDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario buscar(int id) {
        return usuarioDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void grabar(Usuario usuario) {
        usuarioDao.save(usuario);
    }

    @Override
    @Transactional
    public void eliminar(int id) {
        usuarioDao.deleteById(id);
    }
}