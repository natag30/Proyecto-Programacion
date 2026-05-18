package co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.observer;

public class PanelMetricas implements IObservador {

    private int totalCancelados;
    private int totalPublicados;

    @Override
    public void actualizar(String tipoEvento, Object datos) {
        switch (tipoEvento) {
            case "EVENTO_CANCELADO" -> totalCancelados++;
            case "EVENTO_PUBLICADO" -> totalPublicados++;
            default -> {}
        }
    }

    public int getTotalCancelados() { return totalCancelados; }
    public int getTotalPublicados() { return totalPublicados; }
}