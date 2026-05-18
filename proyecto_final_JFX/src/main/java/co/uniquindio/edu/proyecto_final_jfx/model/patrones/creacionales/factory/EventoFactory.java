package co.uniquindio.edu.proyecto_final_jfx.model.patrones.creacionales.factory;

import co.uniquindio.edu.proyecto_final_jfx.model.evento.Evento;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Recinto;

import java.time.LocalDateTime;

public abstract class EventoFactory {

    /**
     * Crea y retorna un evento con el nombre, recinto y fecha indicados.
     *
     * @param nombre   nombre del evento
     * @param recinto  lugar donde se realiza el evento
     * @param fecha    fecha y hora del evento
     * @return nuevo evento creado por la factory concreta
     */
    public abstract Evento crearEvento(
            String nombre,
            Recinto recinto,
           LocalDateTime fecha
    );

}
