package co.uniquindio.edu.controller;

import co.uniquindio.edu.proyecto_final_jfx.model.enums.Categoria;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Evento;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Recinto;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.iterator.IIteradorEventos;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.creacionales.singleton.PlataformaEventos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventoController {

    private final PlataformaEventos plataforma = PlataformaEventos.getInstancia();

    /**
     * Retorna todos los eventos registrados en la plataforma.
     *
     * @return lista de todos los eventos
     */
    public List<Evento> getEventos() {
        return plataforma.getEventos();
    }

    /**
     * Registra un nuevo evento en la plataforma.
     *
     * @param nombre    nombre del evento
     * @param categoria tipo de evento
     * @param ciudad    ciudad donde ocurre
     * @param fechaHora fecha y hora del evento
     * @param recinto   lugar donde se realiza
     * @return el evento creado
     */
    public Evento agregarEvento(String nombre, Categoria categoria, String ciudad,
                                LocalDateTime fechaHora, Recinto recinto) {
        Evento evento = new Evento(nombre, categoria, ciudad, fechaHora, recinto);
        plataforma.agregarEvento(evento);
        return evento;
    }

    /**
     * Cambia el estado del evento a PUBLICADO.
     *
     * @param evento evento a publicar
     * @return true si el cambio fue exitoso
     */
    public boolean publicarEvento(Evento evento) {
        return evento.publicar();
    }

    /**
     * Cambia el estado del evento a PAUSADO.
     *
     * @param evento evento a pausar
     * @return true si el cambio fue exitoso
     */
    public boolean pausarEvento(Evento evento) {
        return evento.pausar();
    }

    /**
     * Cambia el estado del evento a CANCELADO.
     *
     * @param evento evento a cancelar
     * @return true si el cambio fue exitoso
     */
    public boolean cancelarEvento(Evento evento) {
        return evento.cancelar();
    }

    /**
     * Cambia el estado del evento a FINALIZADO.
     *
     * @param evento evento a finalizar
     * @return true si el cambio fue exitoso
     */
    public boolean finalizarEvento(Evento evento) {
        return evento.finalizar();
    }

    /**
     * Busca eventos filtrando por categoría usando el Iterator.
     *
     * @param categoria categoría a filtrar
     * @return lista de eventos de esa categoría
     */
    public List<Evento> buscarPorCategoria(Categoria categoria) {
        return iterar(plataforma.crearIteradorPorCategoria(categoria));
    }

    /**
     * Busca eventos filtrando por ciudad usando el Iterator.
     *
     * @param ciudad ciudad a filtrar
     * @return lista de eventos de esa ciudad
     */
    public List<Evento> buscarPorCiudad(String ciudad) {
        return iterar(plataforma.crearIteradorPorCiudad(ciudad));
    }

    /**
     * Busca eventos dentro de un rango de fechas usando el Iterator.
     *
     * @param desde fecha de inicio del rango
     * @param hasta fecha de fin del rango
     * @return lista de eventos en ese rango de fechas
     */
    public List<Evento> buscarPorFecha(LocalDate desde, LocalDate hasta) {
        return iterar(plataforma.crearIteradorPorFecha(desde, hasta));
    }

    private List<Evento> iterar(IIteradorEventos iter) {
        List<Evento> resultado = new ArrayList<>();
        while (iter.hasNext()) resultado.add(iter.next());
        return resultado;
    }
}
