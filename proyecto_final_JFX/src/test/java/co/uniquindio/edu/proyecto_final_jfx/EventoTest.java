package co.uniquindio.edu.proyecto_final_jfx;

import co.uniquindio.edu.proyecto_final_jfx.model.enums.Categoria;
import co.uniquindio.edu.proyecto_final_jfx.model.enums.EstadoEvento;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Asiento;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Evento;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Recinto;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Zona;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.observer.IObservador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class EventoTest {

    private Evento evento;

    /**
     * este test se encarga de realizar la confirmacion del recinto del evento
     */
    @BeforeEach
    void setUp() {
        Recinto recinto = new Recinto("Coliseo", "Calle 1", "Armenia");
        Zona zona = new Zona("General", 10, 50000);
        for (int i = 1; i <= 10; i++) zona.agregarAsiento(new Asiento("A", i, zona));
        recinto.agregarZona(zona);
        evento = new Evento("Concierto Test", Categoria.CONCIERTO, "Armenia",
                LocalDateTime.now().plusDays(10), recinto);
    }

    /**
     * este test se encarga de realizar la publicacion exitosa
     */
    @Test
    void publicar_exitoso() {
        assertTrue(evento.publicar());
        assertEquals(EstadoEvento.PUBLICADO, evento.getEstado());
    }

    /**
     * este test se encarga de realizar la publicacion fallida si el evento ya esta publicado
     */
    @Test
    void publicar_fallaSiYaPublicado() {
        evento.publicar();
        assertFalse(evento.publicar());
    }

    /**
     * este test se encarga de realizar el pauso exitoso del evento
     */
    @Test
    void pausar_exitoso() {
        evento.publicar();
        assertTrue(evento.pausar());
        assertEquals(EstadoEvento.PAUSADO, evento.getEstado());
    }

    /**
     *este test se encarga de realizar el pauso fallido del evento ya que no se encuentra publicado
     */
    @Test
    void pausar_fallaSiBorrador() {
        assertFalse(evento.pausar());
    }

    /**
     * este test se encarga de realizar la cancelacion exitoso del evento
     */
    @Test
    void cancelar_exitoso() {
        evento.publicar();
        assertTrue(evento.cancelar());
        assertEquals(EstadoEvento.CANCELADO, evento.getEstado());
    }

    @Test
    void cancelar_fallaSiFinalizado() {
        evento.publicar();
        evento.finalizar();
        assertFalse(evento.cancelar());
    }

    @Test
    void finalizar_exitoso() {
        evento.publicar();
        assertTrue(evento.finalizar());
        assertEquals(EstadoEvento.FINALIZADO, evento.getEstado());
    }

    @Test
    void reservarLugar_reduceAforo() {
        evento.publicar();
        int aforoAntes = evento.getAforoDisponible();
        assertTrue(evento.reservarLugar());
        assertEquals(aforoAntes - 1, evento.getAforoDisponible());
    }

    @Test
    void hayDisponibilidad_true() {
        assertTrue(evento.hayDisponibilidad());
    }

    @Test
    void notificar_llamaObservador() {
        AtomicInteger contador = new AtomicInteger(0);
        IObservador obs = (tipoEvento, datos) -> contador.incrementAndGet();
        evento.suscribir(obs);
        evento.publicar();
        assertEquals(1, contador.get());
    }
}
