package com.tecsup.eval02.controllers;

import com.tecsup.eval02.domain.entities.Auditoria;
import com.tecsup.eval02.services.AuditoriaService;
import com.tecsup.eval02.views.AuditoriaPdfView;
import com.tecsup.eval02.views.AuditoriaXlsView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/auditoria")
public class AuditoriaController {

    @Autowired
    private AuditoriaService servicio;

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("titulo", "Listado de Auditorías");
        model.addAttribute("auditorias", servicio.listar());
        return "listarAuditorias";
    }

    @GetMapping("/verpdf")
    public ModelAndView verAuditoriasPdf(Model model) {
        List<Auditoria> auditorias = servicio.listar();
        model.addAttribute("auditorias", auditorias);
        return new ModelAndView(new AuditoriaPdfView(), model.asMap());  // Genera el reporte PDF
    }

    @GetMapping("/verauditoria.xlsx")
    public ModelAndView verAuditoriasExcel(Model model) {
        List<Auditoria> auditorias = servicio.listar();
        model.addAttribute("auditorias", auditorias);
        return new ModelAndView(new AuditoriaXlsView(), model.asMap());  // Genera el reporte Excel
    }
}
