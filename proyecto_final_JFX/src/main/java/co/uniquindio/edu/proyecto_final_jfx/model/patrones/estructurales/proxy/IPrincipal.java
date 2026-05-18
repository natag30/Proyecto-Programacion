package co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.proxy;

public interface IPrincipal {

    /**
     * Muestra la pantalla de métricas del sistema.
     */
    void verMetricas();

    /**
     * Abre el formulario para agregar un nuevo evento.
     */
    void agregarEvento();

    /**
     * Muestra la lista de eventos registrados.
     */
    void verEventos();

    /**
     * Muestra el historial de compras.
     */
    void verCompras();

    /**
     * Muestra la lista de usuarios del sistema.
     */
    void verUsuarios();
}
