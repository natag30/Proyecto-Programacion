package co.uniquindio.edu.proyecto_final_jfx.model.usuario;

import co.uniquindio.edu.proyecto_final_jfx.model.enums.Rol;

public class Administrador {

    private int idAdministrador;
    private String usuario;
    private String contraseña;
    private Rol rol;

    /**
     * Crea un administrador con sus datos de acceso al sistema.
     * El rol se asigna como ADMINISTRADOR automaticamente.
     * @param nombre nombre completo del administrador
     * @param correo correo electronico del administrador
     * @param telefono numero de telefono del administrador
     * @param idAdministrador identificador unico del administrador
     * @param usuario nombre de usuario para entrar al sistema
     * @param contraseña contrasena de acceso
     */
    public Administrador(String nombre, String correo, int telefono,
                     int idAdministrador, String usuario, String contraseña){
        this.idAdministrador = idAdministrador;
        this.usuario         = usuario;
        this.contraseña      = contraseña;
        this.rol             = Rol.ADMINISTRADOR;
    }

    /**
     * Retorna el identificador del administrador.
     * @return id del administrador
     */
    public int getIdAdministrador() {
        return idAdministrador;
    }

    /**
     * Cambia el identificador del administrador.
     * @param idAdministrador nuevo identificador
     */
    public void setIdAdministrador(int idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    /**
     * Retorna el nombre de usuario para acceder al sistema.
     * @return nombre de usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Cambia el nombre de usuario de acceso.
     * @param usuario nuevo nombre de usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Retorna la contrasena del administrador.
     * @return contrasena de acceso
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * Cambia la contrasena de acceso.
     * @param contraseña nueva contrasena
     */
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    /**
     * Retorna el rol del administrador.
     * @return siempre retorna Rol.ADMINISTRADOR
     */
    public Rol getRol() {
        return rol;
    }
}
