package co.uniquindio.edu.proyecto_final_jfx.model.usuario;

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

}
