package com.tecsup.eval02.views;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.tecsup.eval02.domain.entities.Departamento;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import java.util.List;
import java.util.Map;

@Component("departamento/pdf")
public class DepartamentoPdfView extends AbstractView {

    public DepartamentoPdfView() {
        setContentType("application/pdf");
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        @SuppressWarnings("unchecked")
        List<Departamento> departamentos = (List<Departamento>) model.get("departamentos");

        response.setHeader("Content-Disposition", "attachment; filename=\"lista_departamentos.pdf\"");
        response.setContentType(getContentType());

        try (PdfWriter writer = new PdfWriter(response.getOutputStream());
             PdfDocument pdfDoc = new PdfDocument(writer);
             Document document = new Document(pdfDoc)) {

            document.add(new Paragraph("Lista de Departamentos"));

            Table table = new Table(2);
            table.addHeaderCell("ID");
            table.addHeaderCell("Nombre del Departamento");

            for (Departamento departamento : departamentos) {
                table.addCell(String.valueOf(departamento.getIdDepartamentos()));
                table.addCell(departamento.getNombreDepartamentos());
            }

            document.add(table);
        }
    }
}
