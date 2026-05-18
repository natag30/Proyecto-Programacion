package co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.observer;

import co.uniquindio.edu.proyecto_final_jfx.model.evento.Evento;
import co.uniquindio.edu.proyecto_final_jfx.model.incidencia.Incidencia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RegistroIncidencias implements IObservador {

    private List<Incidencia> incidencias = new ArrayList<>();

    /**
     * Recibe el aviso de un cambio y, si el evento fue cancelado, guarda una nueva incidencia.
     *
     * @param tipoEvento texto que indica qué cambio ocurrió
     * @param datos      objeto con la información del evento que cambió
     */
    @Override
    public void actualizar(String tipoEvento, Object datos) {
        if (!tipoEvento.equals("EVENTO_CANCELADO")) return;

        String descripcion = (datos instanceof Evento evento)
                ? "Cancelación del evento: " + evento.getNombre()
                : "Evento cancelado sin detalles";

        incidencias.add(new Incidencia(incidencias.size() + 1, tipoEvento, descripcion, LocalDate.now()));
    }

    /**
     * Retorna la lista de incidencias registradas.
     *
     * @return lista de incidencias
     */
    public List<Incidencia> getIncidencias() { return incidencias; }
}
