package com.tecsup.eval02.views;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.tecsup.eval02.domain.entities.Departamento;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import java.util.List;
import java.util.Map;

@Component("departamento/xls")
public class DepartamentoXlsView extends AbstractView {

    public DepartamentoXlsView() {
        setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        @SuppressWarnings("unchecked")
        List<Departamento> departamentos = (List<Departamento>) model.get("departamentos");

        response.setHeader("Content-Disposition", "attachment; filename=\"lista_departamentos.xlsx\"");
        response.setContentType(getContentType());

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Departamentos");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Nombre del Departamento");

            int rowCount = 1;
            for (Departamento departamento : departamentos) {
                Row row = sheet.createRow(rowCount++);
                row.createCell(0).setCellValue(departamento.getIdDepartamentos());
                row.createCell(1).setCellValue(departamento.getNombreDepartamentos());
            }

            workbook.write(response.getOutputStream());
        }
    }
}
