package co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.iterator;

import co.uniquindio.edu.proyecto_final_jfx.model.enums.Categoria;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Evento;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class IteradorCategoria implements IIteradorEventos{
    private List<Evento> eventos;
    private int posicion;

    /**
     * Crea un iterador que filtra los eventos por categoría.
     * Si el filtro es null, recorre todos los eventos sin filtrar.
     *
     * @param eventos lista de eventos sobre la que se itera
     * @param filtro  categoría por la que se filtra, o null para incluir todos
     */
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
     * @return el siguiente evento de la lista filtrada
     */
    public Evento next(){
        if(!hasNext()) throw new NoSuchElementException();
        return eventos.get(posicion++);
    }

}
