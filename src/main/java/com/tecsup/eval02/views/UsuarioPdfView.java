package com.tecsup.eval02.views;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.tecsup.eval02.domain.entities.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import java.util.List;
import java.util.Map;

@Component("usuario/ver")
public class UsuarioPdfView extends AbstractView {

    public UsuarioPdfView() {
        setContentType("application/pdf");
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        @SuppressWarnings("unchecked")
        List<Usuario> usuarios = (List<Usuario>) model.get("usuarios");

        response.setHeader("Content-Disposition", "attachment; filename=\"lista_usuarios.pdf\"");
        response.setContentType(getContentType());

        try (PdfWriter writer = new PdfWriter(response.getOutputStream());
             PdfDocument pdfDoc = new PdfDocument(writer);
             Document document = new Document(pdfDoc)) {

            document.add(new Paragraph("Lista de Usuarios"));

            Table table = new Table(4);
            table.addHeaderCell("ID");
            table.addHeaderCell("Nombre Completo");
            table.addHeaderCell("Email");
            table.addHeaderCell("Fecha de Registro");

            for (Usuario usuario : usuarios) {
                table.addCell(String.valueOf(usuario.getIdUsuarios()));
                table.addCell(usuario.getNombreCompleto());
                table.addCell(usuario.getEmail());
                table.addCell(usuario.getFechaRegistro().toString());
            }

            document.add(table);
        }
    }
}
