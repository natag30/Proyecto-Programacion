package co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.iterator;

import co.uniquindio.edu.proyecto_final_jfx.model.enums.Categoria;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Evento;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class IteradorCategoria implements IIteradorEventos{
    private List<Evento> eventos;
    private int posicion;

    public IteradorCategoria(List<Evento> eventos, Categoria filtro){
        if (filtro == null) {
            this.eventos = eventos;
        } else {
            this.eventos = eventos.stream()
                    .filter(e -> e.getCategoria() == filtro)
                    .collect(Collectors.toList());
        }
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
