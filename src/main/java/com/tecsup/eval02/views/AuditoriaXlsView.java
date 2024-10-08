package com.tecsup.eval02.views;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.tecsup.eval02.domain.entities.Auditoria;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import java.util.List;
import java.util.Map;

@Component("auditoria/xls")
public class AuditoriaXlsView extends AbstractView {

    public AuditoriaXlsView() {
        setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        @SuppressWarnings("unchecked")
        List<Auditoria> auditorias = (List<Auditoria>) model.get("auditorias");

        response.setHeader("Content-Disposition", "attachment; filename=\"lista_auditorias.xlsx\"");
        response.setContentType(getContentType());

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Auditorias");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Tabla");
            header.createCell(2).setCellValue("ID Registro");
            header.createCell(3).setCellValue("Fecha");
            header.createCell(4).setCellValue("Usuario");
            header.createCell(5).setCellValue("Tipo");

            int rowCount = 1;
            for (Auditoria auditoria : auditorias) {
                Row row = sheet.createRow(rowCount++);
                row.createCell(0).setCellValue(auditoria.getId());
                row.createCell(1).setCellValue(auditoria.getTabla());
                row.createCell(2).setCellValue(auditoria.getIdRegistro());
                row.createCell(3).setCellValue(auditoria.getFecha().toString());
                row.createCell(4).setCellValue(auditoria.getUsuario());
                row.createCell(5).setCellValue(auditoria.getTipo());
            }

            workbook.write(response.getOutputStream());
        }
    }
}
