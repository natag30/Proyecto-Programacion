package co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.proxy;

public class PrincipalReal implements IPrincipal {

    /**
     * Carga y muestra las métricas del sistema en consola.
     */
    @Override
    public void verMetricas() {
        System.out.println("Cargando métricas...");
    }

    /**
     * Abre el formulario para crear un nuevo evento.
     */
    @Override
    public void agregarEvento() {
        System.out.println("Abriendo formulario de nuevo evento...");
    }

    /**
     * Carga y muestra la lista de eventos registrados.
     */
    @Override
    public void verEventos() {
        System.out.println("Cargando lista de eventos...");
    }

    /**
     * Carga y muestra el historial de compras.
     */
    @Override
    public void verCompras() {
        System.out.println("Cargando historial de compras...");
    }

    /**
     * Carga y muestra la lista de usuarios del sistema.
     */
    @Override
    public void verUsuarios() {
        System.out.println("Cargando lista de usuarios...");
    }
}
