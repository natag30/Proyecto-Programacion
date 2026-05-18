package co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.observer;

import co.uniquindio.edu.proyecto_final_jfx.model.evento.Evento;

public class NotificacionCorreo implements IObservador {

    /**
     * Recibe el aviso de un cambio en un evento y simula el envío de un correo con los detalles.
     *
     * @param tipoEvento texto que indica qué cambio ocurrió en el evento
     * @param datos      objeto que contiene la información del evento modificado
     */
    @Override
    public void actualizar(String tipoEvento, Object datos) {
        if (datos instanceof Evento evento) {
            System.out.println("Correo enviado: El evento '" + evento.getNombre() +
                    "' ha registrado el cambio de estado: " + tipoEvento);
        }
    }
}
