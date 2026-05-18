package co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.iterator;

import co.uniquindio.edu.proyecto_final_jfx.model.evento.Evento;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class IteradorCiudad implements IIteradorEventos{
    private List<Evento> eventos;
    private int posicion;

    public IteradorCiudad(List<Evento> eventos, String ciudad){
        this.eventos = eventos.stream()
                .filter(e -> e.getCiudad().equalsIgnoreCase(ciudad))
                .collect(Collectors.toList());
        this.posicion = 0;
    }

    public boolean hasNext(){
        return posicion < eventos.size();
    }

    public Evento next(){
        if(!hasNext()) throw new NoSuchElementException();
        return eventos.get(posicion++);
    }
}
