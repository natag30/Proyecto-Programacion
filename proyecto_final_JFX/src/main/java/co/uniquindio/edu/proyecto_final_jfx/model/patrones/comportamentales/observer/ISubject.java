package co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.observer;

public interface ISubject {

    /**
     * Agrega un observador a la lista de escucha del sujeto.
     *
     * @param o observador que se quiere registrar
     */
    void suscribir(IObservador o);

    /**
     * Elimina un observador de la lista de escucha del sujeto.
     *
     * @param o observador que se quiere quitar
     */
    void desuscribir(IObservador o);

    /**
     * Avisa a todos los observadores registrados que ocurrió un evento.
     *
     * @param tipoEvento texto que describe qué tipo de cambio ocurrió
     */
    void notificar(String tipoEvento);
}
