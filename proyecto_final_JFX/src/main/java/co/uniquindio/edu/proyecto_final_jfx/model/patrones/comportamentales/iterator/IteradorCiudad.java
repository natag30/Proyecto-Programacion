package co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.iterator;

import co.uniquindio.edu.proyecto_final_jfx.model.evento.Evento;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class IteradorCiudad implements IIteradorEventos{
    private List<Evento> eventos;
    private int posicion;

    /**
     * Crea un iterador que filtra los eventos por ciudad.
     *
     * @param eventos lista de eventos sobre la que se itera
     * @param ciudad  nombre de la ciudad por la que se filtra
     */
    public IteradorCiudad(List<Evento> eventos, String ciudad){
        this.eventos = eventos.stream()
                .filter(e -> e.getCiudad().equalsIgnoreCase(ciudad))
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
     * @return el siguiente evento de la lista filtrada por ciudad
     */
    public Evento next(){
        if(!hasNext()) throw new NoSuchElementException();
        return eventos.get(posicion++);
    }
}
