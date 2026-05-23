package co.uniquindio.edu.proyecto_final_jfx.controller;

import co.uniquindio.edu.proyecto_final_jfx.model.compra.Compra;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Evento;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Zona;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.*;
import java.time.LocalDate;
import java.util.List;

public class GeneradorReportes {

    public static void exportarComprasCSV(List<Compra> compras, String rutaArchivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            bw.write("ID,Fecha,Estado,Usuario,Evento,Total,NumEntradas");
            bw.newLine();
            for (Compra c : compras) {
                bw.write(c.getIdCompra() + "," +
                    c.getFechaCreacion() + "," +
                    c.getEstado() + "," +
                    c.getUsuario().getNombre() + "," +
                    c.getEvento().getNombre() + "," +
                    c.getTotal() + "," +
                    c.getEntradas().size());
                bw.newLine();
            }
            System.out.println("CSV exportado: " + rutaArchivo);
        } catch (IOException e) {
            System.err.println("Error exportando CSV de compras: " + e.getMessage());
        }
    }

    public static void exportarComprasPDF(List<Compra> compras, String rutaArchivo) {
        try {
            PDDocument document = new PDDocument();
            PDType1Font fontBold = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
            PDType1Font font     = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream cs = new PDPageContentStream(document, page);

            float y          = 750;
            float margin     = 50;
            int   lineCount  = 0;
            int   MAX_LINES  = 40;

            cs.beginText();
            cs.setFont(fontBold, 14);
            cs.newLineAtOffset(margin, y);
            cs.showText("Reporte de Compras - EventosUQ");
            cs.endText();
            y -= 20; lineCount++;

            cs.beginText();
            cs.setFont(font, 10);
            cs.newLineAtOffset(margin, y);
            cs.showText("Fecha de generacion: " + LocalDate.now());
            cs.endText();
            y -= 30; lineCount += 2;

            for (Compra c : compras) {
                if (lineCount >= MAX_LINES) {
                    cs.close();
                    page = new PDPage();
                    document.addPage(page);
                    cs = new PDPageContentStream(document, page);
                    y = 750; lineCount = 0;
                }
                String linea = "ID:" + c.getIdCompra() +
                    " | " + c.getUsuario().getNombre() +
                    " | " + c.getEvento().getNombre() +
                    " | " + c.getEstado() +
                    " | $" + (int) c.getTotal();
                cs.beginText();
                cs.setFont(font, 9);
                cs.newLineAtOffset(margin, y);
                cs.showText(linea);
                cs.endText();
                y -= 16; lineCount++;
            }

            cs.close();
            document.save(rutaArchivo);
            document.close();
            System.out.println("PDF exportado: " + rutaArchivo);
        } catch (IOException e) {
            System.err.println("Error exportando PDF: " + e.getMessage());
        }
    }

    public static void exportarOcupacionCSV(List<Evento> eventos, String rutaArchivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            bw.write("Evento,Zona,Capacidad,Vendidos,Disponibles,PorcentajeOcupacion");
            bw.newLine();
            for (Evento e : eventos) {
                for (Zona z : e.getRecinto().getZonas()) {
                    int capacidad    = z.getCapacidad();
                    int vendidos     = z.getOcupacion();
                    int disponibles  = z.getAsientosDisponibles().size();
                    double porcentaje = capacidad > 0 ? (vendidos * 100.0 / capacidad) : 0;
                    bw.write(e.getNombre() + "," +
                        z.getNombre() + "," +
                        capacidad + "," +
                        vendidos + "," +
                        disponibles + "," +
                        String.format("%.1f", porcentaje));
                    bw.newLine();
                }
            }
            System.out.println("CSV exportado: " + rutaArchivo);
        } catch (IOException e) {
            System.err.println("Error exportando CSV de ocupacion: " + e.getMessage());
        }
    }
}
