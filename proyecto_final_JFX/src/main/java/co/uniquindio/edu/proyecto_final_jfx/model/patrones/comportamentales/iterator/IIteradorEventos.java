package co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.iterator;

import co.uniquindio.edu.proyecto_final_jfx.model.evento.Evento;

public interface IIteradorEventos {

    /**
     * Verifica si todavía hay eventos por recorrer.
     *
     * @return true si hay al menos un evento más, false si ya se terminaron
     */
    boolean hasNext();

    /**
     * Retorna el siguiente evento de la iteración y avanza la posición.
     *
     * @return el siguiente evento
     */
    Evento next();

}
