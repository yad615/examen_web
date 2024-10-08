package com.tecsup.eval02.services;

import com.tecsup.eval02.domain.entities.Usuario;

import java.util.List;

public interface UsuarioService {
    List<Usuario> listar();
    Usuario buscar(int id);
    void grabar(Usuario usuario);
    void eliminar(int id);
}
