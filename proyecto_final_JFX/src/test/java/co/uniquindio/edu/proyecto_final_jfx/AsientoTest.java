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

    /**
     * este test te ayuda a ubucar la zona y el asiento el cual vas a separar
     */
    @BeforeEach
    void setUp() {
        zona    = new Zona("General", 10, 50000);
        asiento = new Asiento("A", 1, zona);
    }

    /**
     * este tst es el encargado de realizar la reserva exitosa
     */
    @Test
    void reservar_exitoso() {
        assertTrue(asiento.reservar());
        assertEquals(EstadoAsiento.RESERVADO, asiento.getEstado());
    }

    /**
     * este tst es el encargado de realizar la reserva fallida
     */
    @Test
    void reservar_fallaSiNoDisponible() {
        asiento.reservar();
        assertFalse(asiento.reservar());
    }

    /**
     * este tst es el encargado de realizar la venta exitosa
     */
    @Test
    void vender_exitoso() {
        asiento.reservar();
        assertTrue(asiento.vender());
        assertEquals(EstadoAsiento.VENDIDO, asiento.getEstado());
    }

    /**
     * este tst es el encargado de realizar la venta fallida
     */
    @Test
    void vender_fallaSiNoReservado() {
        assertFalse(asiento.vender());
    }

    /**
     * este tst es el encargado de realizar la liberacion exitosa
     */
    @Test
    void liberar_exitoso() {
        asiento.reservar();
        assertTrue(asiento.liberar());
        assertEquals(EstadoAsiento.DISPONIBLE, asiento.getEstado());
    }

    /**
     * este tst es el encargado de realizar la liberacion fallida
     */
    @Test
    void bloquear_exitoso() {
        assertTrue(asiento.bloquear());
        assertEquals(EstadoAsiento.BLOQUEADO, asiento.getEstado());
    }

    /**
     * este tst es el encargado de realizar la liberacion fallida
     */
    @Test
    void bloquear_fallaSiNoDisponible() {
        asiento.reservar();
        assertFalse(asiento.bloquear());
    }

    /**
     * este tst es el encargado de realizar la ocupacion
     * exitosa frente al asiento separado
     */
    @Test
    void ocupar_exitoso() {
        assertTrue(asiento.ocupar());
        assertEquals(EstadoAsiento.VENDIDO, asiento.getEstado());
    }

    /**
     * este tst es el encargado de realizar la ocupacion fallida
     * toda vez que ya puede estar ocupado el asiento
     */
    @Test
    void ocupar_fallaSiYaOcupado() {
        asiento.ocupar();
        assertFalse(asiento.ocupar());
    }
}
