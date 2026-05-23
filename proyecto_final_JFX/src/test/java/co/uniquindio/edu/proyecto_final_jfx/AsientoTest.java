package co.uniquindio.edu.proyecto_final_jfx;

import co.uniquindio.edu.proyecto_final_jfx.model.enums.EstadoAsiento;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Asiento;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Zona;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AsientoTest {

    private Zona zona;
    private Asiento asiento;

    @BeforeEach
    void setUp() {
        zona    = new Zona("General", 10, 50000);
        asiento = new Asiento("A", 1, zona);
    }

    @Test
    void reservar_exitoso() {
        assertTrue(asiento.reservar());
        assertEquals(EstadoAsiento.RESERVADO, asiento.getEstado());
    }

    @Test
    void reservar_fallaSiNoDisponible() {
        asiento.reservar();
        assertFalse(asiento.reservar());
    }

    @Test
    void vender_exitoso() {
        asiento.reservar();
        assertTrue(asiento.vender());
        assertEquals(EstadoAsiento.VENDIDO, asiento.getEstado());
    }

    @Test
    void vender_fallaSiNoReservado() {
        assertFalse(asiento.vender());
    }

    @Test
    void liberar_exitoso() {
        asiento.reservar();
        assertTrue(asiento.liberar());
        assertEquals(EstadoAsiento.DISPONIBLE, asiento.getEstado());
    }

    @Test
    void bloquear_exitoso() {
        assertTrue(asiento.bloquear());
        assertEquals(EstadoAsiento.BLOQUEADO, asiento.getEstado());
    }

    @Test
    void bloquear_fallaSiNoDisponible() {
        asiento.reservar();
        assertFalse(asiento.bloquear());
    }

    @Test
    void ocupar_exitoso() {
        assertTrue(asiento.ocupar());
        assertEquals(EstadoAsiento.VENDIDO, asiento.getEstado());
    }

    @Test
    void ocupar_fallaSiYaOcupado() {
        asiento.ocupar();
        assertFalse(asiento.ocupar());
    }
}
