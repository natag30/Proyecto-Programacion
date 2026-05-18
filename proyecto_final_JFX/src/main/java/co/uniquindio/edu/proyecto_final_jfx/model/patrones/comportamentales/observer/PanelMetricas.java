package co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.observer;

public class PanelMetricas implements IObservador {

    private int totalCancelados;
    private int totalPublicados;

    /**
     * Recibe el aviso de un cambio y actualiza los contadores de eventos cancelados o publicados.
     *
     * @param tipoEvento texto que indica qué cambio ocurrió
     * @param datos      objeto con la información del cambio (no se usa en este observador)
     */
    @Override
    public void actualizar(String tipoEvento, Object datos) {
        switch (tipoEvento) {
            case "EVENTO_CANCELADO" -> totalCancelados++;
            case "EVENTO_PUBLICADO" -> totalPublicados++;
            default -> {}
        }
    }

    /**
     * Retorna la cantidad de eventos cancelados registrados.
     *
     * @return total de eventos cancelados
     */
    public int getTotalCancelados() { return totalCancelados; }

    /**
     * Retorna la cantidad de eventos publicados registrados.
     *
     * @return total de eventos publicados
     */
    public int getTotalPublicados() { return totalPublicados; }
}
