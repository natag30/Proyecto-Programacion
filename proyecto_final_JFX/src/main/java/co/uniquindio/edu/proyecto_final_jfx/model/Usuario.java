package co.uniquindio.edu.proyecto_final_jfx.model;

public class Usuario {

    private String nombre;
    private int idUsuario;
    private String correo;
    private int telefono;

    public Usuario(String nombre, int idUsuario, String correo, int telefono){
        this.nombre = nombre;
        this.idUsuario = idUsuario;
        this.correo = correo;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
}
