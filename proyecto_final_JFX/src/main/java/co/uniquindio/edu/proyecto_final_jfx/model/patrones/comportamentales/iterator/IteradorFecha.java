package co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.iterator;

import co.uniquindio.edu.proyecto_final_jfx.model.evento.Evento;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class IteradorFecha implements IIteradorEventos{
    private List<Evento> eventos;
    private int posicion;

    /**
     * Crea un iterador que filtra los eventos dentro de un rango de fechas.
     *
     * @param eventos lista de eventos sobre la que se itera
     * @param desde   fecha de inicio del rango (inclusive)
     * @param hasta   fecha de fin del rango (inclusive)
     */
    public IteradorFecha(List<Evento> eventos, LocalDate desde, LocalDate hasta){
        this.eventos = eventos.stream()
                .filter(e -> !e.getFechaHora().toLocalDate().isBefore(desde)
                        && !e.getFechaHora().toLocalDate().isAfter(hasta))
                .collect(Collectors.toList());
        this.posicion = 0;

    }

    /**
     * Verifica si hay eventos por recorrer.
     *
     * @return true si aún quedan eventos, false si ya se llegó al final
     */
    public boolean hasNext(){
        return posicion < eventos.size();
    }

    /**
     * Retorna el siguiente evento y avanza la posición.
     *
     * @return el siguiente evento dentro del rango de fechas
     */
    public Evento next(){
        if(!hasNext()) throw new NoSuchElementException();
        return eventos.get(posicion++);
    }
}
