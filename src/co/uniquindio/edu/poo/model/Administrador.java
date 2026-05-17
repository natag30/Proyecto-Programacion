package co.uniquindio.edu.poo.model;

public class Administrador extends Usuario{
    private int idAdministrador;
    private String usuario;
    private String contraseña;

public Administrador(String nombre, int idUsuario, String correo, int telefono,
                     int idAdministrador, String usuario, String contraseña){
    super(nombre,idUsuario, correo, telefono);
    this.idAdministrador = idAdministrador;
    this.usuario = usuario;
    this.contraseña = contraseña;
}

    public int getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(int idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
