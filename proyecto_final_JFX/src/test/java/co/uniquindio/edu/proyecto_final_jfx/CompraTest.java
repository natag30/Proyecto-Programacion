package co.uniquindio.edu.proyecto_final_jfx;

import co.uniquindio.edu.proyecto_final_jfx.model.compra.Compra;
import co.uniquindio.edu.proyecto_final_jfx.model.enums.Categoria;
import co.uniquindio.edu.proyecto_final_jfx.model.enums.EstadoCompra;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Asiento;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Evento;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Recinto;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Zona;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.decorator.EntradaBase;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.decorator.IEntrada;
import co.uniquindio.edu.proyecto_final_jfx.model.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CompraTest {

    private Usuario usuario;
    private Evento  evento;
    private IEntrada entrada;

    @BeforeEach
    void setUp() {
        usuario = new Usuario("Test", 1, "t@t.com", 123L, "test", "1234");

        Recinto recinto = new Recinto("R", "Dir", "Ciudad");
        Zona zona = new Zona("General", 10, 50000);
        recinto.agregarZona(zona);
        Asiento asiento = new Asiento("A", 1, zona);
        zona.agregarAsiento(asiento);

        evento  = new Evento("E", Categoria.CONCIERTO, "Ciudad",
                LocalDateTime.now().plusDays(1), recinto);
        entrada = new EntradaBase("Asiento A1", 50000);
    }

    @Test
    void builder_creaCompraCorrectamente() {
        Compra compra = new Compra.Builder()
                .conUsuario(usuario)
                .conEvento(evento)
                .conEntrada(entrada)
                .build();

        assertNotNull(compra);
        assertEquals(EstadoCompra.CREADA, compra.getEstado());
        assertEquals(50000.0, compra.getTotal(), 0.01);
    }

    @Test
    void builder_fallaSinUsuario() {
        assertThrows(IllegalStateException.class, () ->
                new Compra.Builder().conEvento(evento).conEntrada(entrada).build());
    }

    @Test
    void builder_fallaSinEvento() {
        assertThrows(IllegalStateException.class, () ->
                new Compra.Builder().conUsuario(usuario).conEntrada(entrada).build());
    }

    @Test
    void builder_fallaSinEntradas() {
        assertThrows(IllegalStateException.class, () ->
                new Compra.Builder().conUsuario(usuario).conEvento(evento).build());
    }

    @Test
    void cancelar_exitoso() {
        Compra compra = new Compra.Builder()
                .conUsuario(usuario).conEvento(evento).conEntrada(entrada).build();
        assertTrue(compra.cancelar());
        assertEquals(EstadoCompra.CANCELADA, compra.getEstado());
    }

    @Test
    void cancelar_fallaSiConfirmada() {
        Compra compra = new Compra.Builder()
                .conUsuario(usuario).conEvento(evento).conEntrada(entrada).build();
        compra.setEstado(EstadoCompra.CONFIRMADA);
        assertFalse(compra.cancelar());
    }

    @Test
    void getTotal_calculaCorrectamente() {
        Compra compra = new Compra.Builder()
                .conUsuario(usuario).conEvento(evento).conEntrada(entrada).build();
        assertEquals(50000.0, compra.getTotal(), 0.01);
    }
}
