package co.uniquindio.edu.proyecto_final_jfx;

import co.uniquindio.edu.proyecto_final_jfx.model.enums.Categoria;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Asiento;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Evento;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Recinto;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Zona;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.iterator.IIteradorEventos;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.iterator.IteradorCategoria;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.iterator.IteradorCiudad;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.iterator.IteradorFecha;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IteradorTest {

    private List<Evento> lista;

    private Evento crearEvento(String nombre, Categoria cat, String ciudad, LocalDateTime fecha) {
        Recinto r = new Recinto("R", "Dir", ciudad);
        Zona z = new Zona("General", 5, 50000);
        for (int i = 1; i <= 5; i++) z.agregarAsiento(new Asiento("A", i, z));
        r.agregarZona(z);
        return new Evento(nombre, cat, ciudad, fecha, r);
    }

    /**
     * este test es el encargado de generar la lista de eventos
     */
    @BeforeEach
    void setUp() {
        lista = new ArrayList<>();
        LocalDate hoy = LocalDate.now();
        lista.add(crearEvento("e1", Categoria.CONCIERTO,   "Armenia",   LocalDateTime.now().plusDays(7)));
        lista.add(crearEvento("e2", Categoria.TEATRO,      "Pereira",   LocalDateTime.now().plusDays(30)));
        lista.add(crearEvento("e3", Categoria.CONCIERTO,   "Armenia",   LocalDateTime.now().plusDays(180)));
        lista.add(crearEvento("e4", Categoria.CONFERENCIA, "Manizales", LocalDateTime.now().minusDays(1)));
    }

    // ── IteradorCategoria ──────────────────────────────────────────────

    /**
     * este test es el encargado de filtrar solo los conciertos
     */
    @Test
    void filtra_soloConciertos() {
        IIteradorEventos it = new IteradorCategoria(lista, Categoria.CONCIERTO);
        int count = 0;
        while (it.hasNext()) { it.next(); count++; }
        assertEquals(2, count);
    }

    /**
     * este test es el encargado de no realizar los filtros y observar todos los eventos
     */
    @Test
    void filtra_sinFiltro_retornaTodos() {
        IIteradorEventos it = new IteradorCategoria(lista, null);
        int count = 0;
        while (it.hasNext()) { it.next(); count++; }
        assertEquals(4, count);
    }

    /**
     * este test se encarga de no ubicar la lista vacia
     */
    @Test
    void hasNext_falseListaVacia() {
        IIteradorEventos it = new IteradorCategoria(new ArrayList<>(), null);
        assertFalse(it.hasNext());
    }

    // ── IteradorCiudad ─────────────────────────────────────────────────

    /**
     * este test es el encargado de filtrar solo las ciudades
     */
    @Test
    void filtra_soloArmenia() {
        IIteradorEventos it = new IteradorCiudad(lista, "Armenia");
        int count = 0;
        while (it.hasNext()) { it.next(); count++; }
        assertEquals(2, count);
    }

    /**
     * este test es el encargado de ubicar la ciudad del evento
     */
    @Test
    void filtra_ciudadInexistente() {
        IIteradorEventos it = new IteradorCiudad(lista, "Bogota");
        assertFalse(it.hasNext());
    }

    // ── IteradorFecha ──────────────────────────────────────────────────

    /**
     * este test es el encargado de filtrar las fechas
     */
    @Test
    void filtra_rangoQueIncluye2() {
        LocalDate desde = LocalDate.now().plusDays(1);
        LocalDate hasta = LocalDate.now().plusDays(35);
        IIteradorEventos it = new IteradorFecha(lista, desde, hasta);
        int count = 0;
        while (it.hasNext()) { it.next(); count++; }
        assertEquals(2, count);
    }

    /**
     * este test es el encargado de filtrar las fechas que estan aignadas los fechas de compras de los eventos
     */
    @Test
    void filtra_rangoVacio() {
        LocalDate desde = LocalDate.of(2020, 1, 1);
        LocalDate hasta = LocalDate.of(2020, 1, 31);
        IIteradorEventos it = new IteradorFecha(lista, desde, hasta);
        assertFalse(it.hasNext());
    }
}
