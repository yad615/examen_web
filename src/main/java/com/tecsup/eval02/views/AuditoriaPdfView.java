package com.tecsup.eval02.views;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.tecsup.eval02.domain.entities.Auditoria;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import java.util.List;
import java.util.Map;

@Component("auditoria/pdf")
public class AuditoriaPdfView extends AbstractView {

    public AuditoriaPdfView() {
        setContentType("application/pdf");
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        @SuppressWarnings("unchecked")
        List<Auditoria> auditorias = (List<Auditoria>) model.get("auditorias");

        response.setHeader("Content-Disposition", "attachment; filename=\"lista_auditorias.pdf\"");
        response.setContentType(getContentType());

        try (PdfWriter writer = new PdfWriter(response.getOutputStream());
             PdfDocument pdfDoc = new PdfDocument(writer);
             Document document = new Document(pdfDoc)) {

            document.add(new Paragraph("Lista de Auditorías"));

            Table table = new Table(6);
            table.addHeaderCell("ID");
            table.addHeaderCell("Tabla");
            table.addHeaderCell("ID Registro");
            table.addHeaderCell("Fecha");
            table.addHeaderCell("Usuario");
            table.addHeaderCell("Tipo");

            for (Auditoria auditoria : auditorias) {
                table.addCell(String.valueOf(auditoria.getId()));
                table.addCell(auditoria.getTabla());
                table.addCell(String.valueOf(auditoria.getIdRegistro()));
                table.addCell(auditoria.getFecha().toString());
                table.addCell(auditoria.getUsuario());
                table.addCell(auditoria.getTipo());
            }

            document.add(table);
        }
    }
}
