package co.uniquindio.edu.proyecto_final_jfx.model.usuario;

import co.uniquindio.edu.proyecto_final_jfx.model.enums.Rol;

public class Usuario {

 
    private String nombre;
     private int idUsuario;
    private String correo;
    private long telefono;
    private String usuario;
    private String contrasena;
    private Rol rol;

    /**
     * Crea un nuevo usuario con sus datos personales.
     * El rol se asigna como USUARIO por defecto.
     * @param nombre nombre completo del usuario
     * @param idUsuario identificador unico del usuario
     * @param correo correo electronico del usuario
     * @param telefono numero de telefono del usuario
     */
    public Usuario(String nombre, int idUsuario, String correo, long telefono, String usuario, String contrasena) {
        this.nombre    = nombre;
        this.idUsuario = idUsuario;
        this.correo    = correo;
        this.telefono  = telefono;
        this.usuario   = usuario;
        this.contrasena = contrasena;
        this.rol       = Rol.USUARIO;
    }

    /**
     * Retorna el nombre del usuario.
     * @return nombre completo
     */
    public String getNombre()  { return nombre; }

    /**
     * Cambia el nombre del usuario.
     * @param nombre nuevo nombre completo
     */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /**
     * Retorna el identificador del usuario.
     * @return id del usuario
     */
    public int getIdUsuario()  { return idUsuario; }

    /**
     * Cambia el identificador del usuario.
     * @param idUsuario nuevo identificador
     */
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    /**
     * Retorna el correo electronico del usuario.
     * @return correo electronico
     */
    public String getCorreo()  { return correo; }

    /**
     * Cambia el correo electronico del usuario.
     * @param correo nuevo correo electronico
     */
    public void setCorreo(String correo) { this.correo = correo; }

    /**
     * Retorna el numero de telefono del usuario.
     * @return numero de telefono
     */
    public long getTelefono()   { return telefono; }

    /**
     * Cambia el numero de telefono del usuario.
     * @param telefono nuevo numero de telefono
     */
    public void setTelefono(long telefono) { this.telefono = telefono; }

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
        return contrasena;
    }

    /**
     * Cambia la contrasena de acceso.
     * @param contraseña nueva contrasena
     */
    public void setContraseña(String contraseña) {
        this.contrasena = contraseña;
    }

    /**
     * Retorna el rol del usuario en el sistema.
     * @return rol asignado (USUARIO o ADMINISTRADOR)
     */
    public Rol getRol()        { return rol; }

    /**
     * Cambia el rol del usuario.
     * @param rol nuevo rol a asignar
     */
    public void setRol(Rol rol){ this.rol = rol; }
}