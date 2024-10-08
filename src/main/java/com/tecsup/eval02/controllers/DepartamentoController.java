package com.tecsup.eval02.controllers;

import com.tecsup.eval02.domain.entities.Auditoria;
import com.tecsup.eval02.domain.entities.Departamento;
import com.tecsup.eval02.services.AuditoriaService;
import com.tecsup.eval02.services.DepartamentoService;
import com.tecsup.eval02.views.DepartamentoPdfView;
import com.tecsup.eval02.views.DepartamentoXlsView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/departamentos")
@SessionAttributes("departamento")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;

    @Autowired
    private AuditoriaService auditoriaService;

    @GetMapping("/listar")
    public String listarDepartamentos(Model model) {
        model.addAttribute("titulo", "Listado de Departamentos");
        model.addAttribute("departamentos", departamentoService.listar());
        return "listarDepartamentos"; // Nombre de la vista para listar departamentos
    }

    @GetMapping("/form")
    public String crearDepartamento(Map<String, Object> model) {
        Departamento departamento = new Departamento();
        model.put("departamento", departamento);
        model.put("titulo", "Formulario de Nuevo Departamento");
        return "formDepartamentos";
    }

    @GetMapping("/form/{id}")
    public String editarDepartamento(@PathVariable(value = "id") Integer id, Map<String, Object> model) {
        Departamento departamento = null;
        if (id > 0) {
            departamento = departamentoService.buscar(id);
        } else {
            return "redirect:/departamentos/listar";
        }
        model.put("departamento", departamento);
        model.put("titulo", "Editar Departamento");
        return "formDepartamentos";
    }

    @PostMapping("/form")
    public String guardarDepartamento(@Valid Departamento departamento, BindingResult result, Model model, SessionStatus status) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Departamento");
            return "formDepartamentos";
        }

        boolean esNuevo = (departamento.getIdDepartamentos() == 0);
        departamentoService.grabar(departamento);

        // Registro de auditoría
        String tipoAccion = esNuevo ? "CREAR" : "EDITAR";
        Auditoria auditoria = new Auditoria("departamentos", departamento.getIdDepartamentos(), new Date(), "admin", tipoAccion);
        auditoriaService.registrarAuditoria(auditoria);

        status.setComplete();
        return "redirect:/departamentos/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarDepartamento(@PathVariable(value = "id") Integer id) {
        if (id > 0) {
            departamentoService.eliminar(id);

            Auditoria auditoria = new Auditoria("departamentos", id, new Date(), "admin", "ELIMINAR");
            auditoriaService.registrarAuditoria(auditoria);
        }
        return "redirect:/departamentos/listar";
    }

    @GetMapping("/verpdf")
    public ModelAndView verDepartamentosPdf(Model model) {
        List<Departamento> departamentos = departamentoService.listar();
        model.addAttribute("departamentos", departamentos);
        return new ModelAndView(new DepartamentoPdfView(), model.asMap());
    }

    @GetMapping("/verdepartamento.xlsx")
    public ModelAndView verDepartamentosExcel(Model model) {
        List<Departamento> departamentos = departamentoService.listar();
        model.addAttribute("departamentos", departamentos);
        return new ModelAndView(new DepartamentoXlsView(), model.asMap());
    }
}
