package co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.proxy;

import co.uniquindio.edu.proyecto_final_jfx.model.usuario.SesionActual;

public class ProxyAcceso implements IPrincipal {

    private final PrincipalReal real = new PrincipalReal();
    private final SesionActual sesion = SesionActual.getInstancia();

    /**
     * Muestra las métricas solo si el usuario tiene rol de administrador.
     */
    @Override
    public void verMetricas() {
        if (soloAdmin()) real.verMetricas();
    }

    /**
     * Abre el formulario de nuevo evento solo si el usuario es administrador.
     */
    @Override
    public void agregarEvento() {
        if (soloAdmin()) real.agregarEvento();
    }

    /**
     * Muestra la lista de eventos si el usuario está autenticado.
     */
    @Override
    public void verEventos() {
        if (autenticado()) real.verEventos();
    }

    /**
     * Muestra el historial de compras si el usuario está autenticado.
     */
    @Override
    public void verCompras() {
        if (autenticado()) real.verCompras();
    }

    /**
     * Muestra la lista de usuarios solo si el usuario es administrador.
     */
    @Override
    public void verUsuarios() {
        if (soloAdmin()) real.verUsuarios();
    }

    // verifica si hay una sesión activa
    private boolean autenticado() {
        if (!sesion.estaAutenticado()) {
            System.out.println("Acceso denegado: debe iniciar sesión.");
            return false;
        }
        return true;
    }

    // verifica si hay sesión activa y si el usuario es administrador
    private boolean soloAdmin() {
        if (!autenticado()) return false;
        if (!sesion.esAdministrador()) {
            System.out.println("Acceso denegado: se requiere rol de administrador.");
            return false;
        }
        return true;
    }
}
