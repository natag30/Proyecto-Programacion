package co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.observer;

import co.uniquindio.edu.proyecto_final_jfx.model.evento.Evento;
import co.uniquindio.edu.proyecto_final_jfx.model.incidencia.Incidencia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RegistroIncidencias implements IObservador {

    private List<Incidencia> incidencias = new ArrayList<>();

    @Override
    public void actualizar(String tipoEvento, Object datos) {
        if (!tipoEvento.equals("EVENTO_CANCELADO")) return;

        String descripcion = (datos instanceof Evento evento)
                ? "Cancelación del evento: " + evento.getNombre()
                : "Evento cancelado sin detalles";

        incidencias.add(new Incidencia(incidencias.size() + 1, tipoEvento, descripcion, LocalDate.now()));
    }

    public List<Incidencia> getIncidencias() { return incidencias; }
}