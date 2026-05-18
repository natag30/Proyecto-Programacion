package co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.observer;

public interface IObservador {

    /**
     * Recibe la notificación de un cambio y reacciona según el tipo de evento.
     *
     * @param tipoEvento texto que indica qué tipo de cambio ocurrió
     * @param datos      objeto con la información del cambio
     */
    void actualizar(String tipoEvento, Object datos);
}
