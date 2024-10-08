package com.tecsup.eval02.controllers;

import com.tecsup.eval02.domain.entities.Auditoria;
import com.tecsup.eval02.domain.entities.Usuario;
import com.tecsup.eval02.services.AuditoriaService;
import com.tecsup.eval02.services.UsuarioService;
import com.tecsup.eval02.views.UsuarioPdfView;
import com.tecsup.eval02.views.UsuarioXlsView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/usuarios")
@SessionAttributes("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService servicio;

    @Autowired
    private AuditoriaService auditoriaService;

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("titulo", "Listado de Usuarios");
        model.addAttribute("usuarios", servicio.listar());
        return "listarUsuarios";
    }

    @GetMapping("/form")
    public String crear(Map<String, Object> model) {
        Usuario usuario = new Usuario();
        model.put("usuario", usuario);
        model.put("titulo", "Formulario de Usuario");
        return "formUsuarios";
    }

    @GetMapping("/form/{id}")
    public String editar(@PathVariable(value = "id") Integer id, Map<String, Object> model) {
        Usuario usuario = null;
        if (id > 0) {
            usuario = servicio.buscar(id);
        } else {
            return "redirect:/usuarios/listar";
        }
        model.put("usuario", usuario);
        model.put("titulo", "Editar Usuario");
        return "formUsuarios";
    }

    @PostMapping("/form")
    public String guardar(@Valid Usuario usuario, BindingResult result, Model model, SessionStatus status) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Usuario");
            return "formUsuarios";
        }

        boolean esNuevo = (usuario.getIdUsuarios() == null || usuario.getIdUsuarios() == 0);
        servicio.grabar(usuario);

        String tipoAccion = esNuevo ? "CREAR" : "EDITAR";
        Auditoria auditoria = new Auditoria("usuarios", usuario.getIdUsuarios(), new Date(), "admin", tipoAccion);
        auditoriaService.registrarAuditoria(auditoria);

        status.setComplete();
        return "redirect:/usuarios/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Integer id) {
        if (id > 0) {
            servicio.eliminar(id);
            Auditoria auditoria = new Auditoria("usuarios", id, new Date(), "admin", "ELIMINAR");
            auditoriaService.registrarAuditoria(auditoria);
        }
        return "redirect:/usuarios/listar";
    }

    @GetMapping("/download/pdf")
    public ModelAndView verUsuariosPdf(Model model) {
        model.addAttribute("usuarios", servicio.listar());
        return new ModelAndView(new UsuarioPdfView(), model.asMap());
    }

    @GetMapping("/download/xls")
    public ModelAndView verUsuariosExcel(Model model) {
        model.addAttribute("usuarios", servicio.listar());
        return new ModelAndView(new UsuarioXlsView(), model.asMap());
    }
}
