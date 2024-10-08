package com.tecsup.eval02.views;

import com.tecsup.eval02.domain.entities.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import java.util.List;
import java.util.Map;

@Component("usuario/verusuario.xlsx")
public class UsuarioXlsView extends AbstractView {

    public UsuarioXlsView() {
        setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        @SuppressWarnings("unchecked")
        List<Usuario> usuarios = (List<Usuario>) model.get("usuarios");

        try (Workbook workbook = WorkbookFactory.create(true)) {
            Sheet sheet = workbook.createSheet("Lista de Usuarios");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Nombre Completo");
            headerRow.createCell(2).setCellValue("Email");
            headerRow.createCell(3).setCellValue("Fecha de Registro");

            int rowNum = 1;
            for (Usuario usuario : usuarios) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(usuario.getIdUsuarios());
                row.createCell(1).setCellValue(usuario.getNombreCompleto());
                row.createCell(2).setCellValue(usuario.getEmail());
                row.createCell(3).setCellValue(usuario.getFechaRegistro().toString());
            }

            response.setHeader("Content-Disposition", "attachment; filename=\"lista_usuarios.xlsx\"");
            response.setContentType(getContentType());
            workbook.write(response.getOutputStream());
        }
    }
}
