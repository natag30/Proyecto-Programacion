package co.uniquindio.edu.proyecto_final_jfx;

import co.uniquindio.edu.proyecto_final_jfx.controller.GeneradorReportes;
import co.uniquindio.edu.proyecto_final_jfx.model.compra.Compra;
import co.uniquindio.edu.proyecto_final_jfx.model.enums.Categoria;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Asiento;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Evento;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Recinto;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Zona;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.decorator.EntradaBase;
import co.uniquindio.edu.proyecto_final_jfx.model.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeneradorReportesTest {

    private List<Compra>  compras;
    private List<Evento>  eventos;


    /**
     * este test es el encargado de realizar todas las compras, separacion de asiento, de lugar.
     */

    @BeforeEach
    void setUp() {
        Usuario u = new Usuario("Tester", 1, "t@t.com", 123L, "test", "1234");

        Recinto r = new Recinto("Recinto", "Dir", "Ciudad");
        Zona z = new Zona("General", 5, 50000);
        for (int i = 1; i <= 5; i++) z.agregarAsiento(new Asiento("A", i, z));
        r.agregarZona(z);
        Evento e = new Evento("Evento Test", Categoria.CONCIERTO, "Ciudad",
                LocalDateTime.now().plusDays(5), r);

        compras = new ArrayList<>();
        compras.add(new Compra.Builder()
                .conUsuario(u).conEvento(e)
                .conEntrada(new EntradaBase("A1", 50000))
                .build());
        compras.add(new Compra.Builder()
                .conUsuario(u).conEvento(e)
                .conEntrada(new EntradaBase("A2", 50000))
                .build());

        eventos = new ArrayList<>();
        eventos.add(e);
    }

    /**
     * este test es el encargado de generar el reporte de la compra exitosa
     * @throws Exception
     */
    @Test
    void exportarCSV_creaArchivo() throws Exception {
        String ruta = Files.createTempFile("test_compras", ".csv").toString();
        GeneradorReportes.exportarComprasCSV(compras, ruta);
        File f = new File(ruta);
        assertTrue(f.exists());
        assertTrue(f.length() > 0);
    }

    /**
     * este test es el encargado de generar el reporte de la compra exitosa
     * @throws Exception
     */
    @Test
    void exportarCSV_contieneEncabezado() throws Exception {
        String ruta = Files.createTempFile("test_header", ".csv").toString();
        GeneradorReportes.exportarComprasCSV(compras, ruta);
        String contenido = Files.readString(new File(ruta).toPath());
        assertTrue(contenido.contains("ID"));
    }

    /**
     * este test es el encargado de generar el pdf de la compra exitosa
     * @throws Exception
     */
    @Test
    void exportarPDF_creaArchivo() throws Exception {
        String ruta = Files.createTempFile("test_compras", ".pdf").toString();
        GeneradorReportes.exportarComprasPDF(compras, ruta);
        File f = new File(ruta);
        assertTrue(f.exists());
        assertTrue(f.length() > 0);
    }

    /**
     * este test es el encargado de generar el reporte de ocupacion exitosa sin errores
     * @throws Exception
     */
    @Test
    void exportarOcupacionCSV_creaArchivo() throws Exception {
        String ruta = Files.createTempFile("test_ocupacion", ".csv").toString();
        GeneradorReportes.exportarOcupacionCSV(eventos, ruta);
        File f = new File(ruta);
        assertTrue(f.exists());
        assertTrue(f.length() > 0);
    }
}
