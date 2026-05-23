package co.uniquindio.edu.controller;

import co.uniquindio.edu.proyecto_final_jfx.model.enums.Rol;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.creacionales.singleton.PlataformaEventos;
import co.uniquindio.edu.proyecto_final_jfx.model.usuario.Administrador;
import co.uniquindio.edu.proyecto_final_jfx.model.usuario.SesionActual;
import co.uniquindio.edu.proyecto_final_jfx.model.usuario.Usuario;

import java.util.List;

public class UsuarioController {

    private final PlataformaEventos plataforma = PlataformaEventos.getInstancia();
    private final SesionActual sesion = SesionActual.getInstancia();

    /**
     * Intenta iniciar sesión como administrador. Si las credenciales son correctas,
     * crea una sesión con rol ADMINISTRADOR y retorna true.
     *
     * @param usuario   nombre de usuario del administrador
     * @param contrasena contraseña de acceso
     * @return true si el login fue exitoso, false si las credenciales son incorrectas
     */
    public boolean loginAdministrador(String usuario, String contrasena) {
        Administrador admin = plataforma.buscarAdministrador(usuario, contrasena);
        if (admin == null) return false;
        Usuario proxy = new Usuario(admin.getUsuario(), admin.getIdAdministrador(),
                usuario, 0, admin.getUsuario(), contrasena);
        proxy.setRol(Rol.ADMINISTRADOR);
        sesion.iniciarSesion(proxy);
        return true;
    }

    /**
     * se encarga de ingresar el usuario con contraseña para poder acceder si el usuario y contraseña es corecto
     * @param identificador identificar el usuario correcto
     * @param contrasena identificar contraseña correcta
     * @return
     */
    public boolean loginUsuario(String identificador, String contrasena) {
        Usuario usuario = plataforma.buscarUsuarioPorCredenciales(identificador, contrasena);
        if (usuario == null) return false;
        usuario.setRol(Rol.USUARIO);
        sesion.iniciarSesion(usuario);
        return true;
    }

    /**
     * Cierra la sesión activa del usuario actual.
     */
    public void cerrarSesion() {
        sesion.cerrarSesion();
    }

    /**
     * Retorna el usuario que tiene la sesión activa en este momento.
     *
     * @return usuario actual, o null si no hay sesión activa
     */
    public Usuario getUsuarioActual() {
        return sesion.getUsuarioActual();
    }

    /**
     * Verifica si hay un usuario con sesión abierta.
     *
     * @return true si hay sesión activa
     */
    public boolean estaAutenticado() {
        return sesion.estaAutenticado();
    }

    /**
     * Verifica si el usuario activo es administrador.
     *
     * @return true si el usuario es administrador
     */
    public boolean esAdministrador() {
        return sesion.esAdministrador();
    }

    /**
     * Retorna la lista de todos los usuarios registrados.
     *
     * @return lista de usuarios
     */
    public List<Usuario> getUsuarios() {
        return plataforma.getUsuarios();
    }

    /**
     * Registra un nuevo usuario en la plataforma.
     *
     * @param usuario usuario a registrar
     */
    public void agregarUsuario(Usuario usuario) {
        plataforma.agregarUsuario(usuario);
    }
}
