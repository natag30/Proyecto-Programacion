package co.uniquindio.edu.proyecto_final_jfx.model.usuario;

import co.uniquindio.edu.proyecto_final_jfx.model.enums.Rol;

public class SesionActual {

   private static SesionActual instancia;

   private Usuario usuarioActual;

   private SesionActual() {}

    /**
     * Retorna la única instancia de la sesión. Si no existe, la crea.
     *
     * @return la instancia compartida de SesionActual
     */
    public static SesionActual getInstancia() {
        if (instancia == null) {
            instancia = new SesionActual();
        }
        return instancia;
    }

    /**
     * Guarda el usuario que acaba de iniciar sesión.
     *
     * @param u el usuario que entró al sistema
     */
    public void iniciarSesion(Usuario u) {
        this.usuarioActual = u;
    }

    /**
     * Elimina el usuario de la sesión, dejándola vacía.
     */
    public void cerrarSesion() {
        this.usuarioActual = null;
    }

    /**
     * Retorna el usuario que está usando la aplicación en este momento.
     *
     * @return el usuario actual, o null si no hay sesión activa
     */
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * Verifica si hay un usuario con sesión abierta.
     *
     * @return true si hay un usuario activo, false si no hay sesión
     */
    public boolean estaAutenticado() {
        return usuarioActual != null;
    }

    /**
     * Verifica si el usuario actual tiene el rol de administrador.
     *
     * @return true si el usuario existe y es administrador, false en cualquier otro caso
     */
    public boolean esAdministrador() {
        return usuarioActual != null && usuarioActual.getRol() == Rol.ADMINISTRADOR;
    }
}