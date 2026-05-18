package co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.observer;

import co.uniquindio.edu.proyecto_final_jfx.model.evento.Evento;

public class NotificacionCorreo implements IObservador {

    @Override
    public void actualizar(String tipoEvento, Object datos) {
        if (datos instanceof Evento evento) {
            System.out.println("Correo enviado: El evento '" + evento.getNombre() +
                    "' ha registrado el cambio de estado: " + tipoEvento);
        }
    }
}