package co.uniquindio.edu.proyecto_final_jfx.model.patrones.creacionales.factory;

import co.uniquindio.edu.proyecto_final_jfx.model.enums.Categoria;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Evento;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Recinto;

import java.time.LocalDateTime;

public class ConferenciaFactory extends EventoFactory {

    /**
     * Crea y retorna un evento de tipo CONFERENCIA con el nombre, recinto y fecha dados.
     *
     * @param nombre  nombre de la conferencia
     * @param recinto lugar donde se realiza la conferencia
     * @param fecha   fecha y hora del evento
     * @return nuevo evento con categoría CONFERENCIA
     */
    @Override
    public Evento crearEvento(String nombre, Recinto recinto, LocalDateTime fecha) {
        return new Evento(
                nombre,
                Categoria.CONFERENCIA,
                recinto.getCiudad(),
                fecha,
                recinto
        );
    }
}
