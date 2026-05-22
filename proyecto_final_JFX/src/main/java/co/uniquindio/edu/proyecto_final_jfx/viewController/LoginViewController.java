package co.uniquindio.edu.proyecto_final_jfx.viewController;

import co.uniquindio.edu.proyecto_final_jfx.Launcher;
import co.uniquindio.edu.proyecto_final_jfx.model.enums.Rol;
import co.uniquindio.edu.proyecto_final_jfx.model.usuario.SesionActual;
import co.uniquindio.edu.proyecto_final_jfx.model.usuario.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginViewController {

    @FXML private TextField     txtUsuario;
    @FXML private PasswordField pfContrasena;
    @FXML private ComboBox<Rol> cbRol;
    @FXML private Label         lblError;

    private Launcher launcher;

    public void setLauncher(Launcher launcher) {
        this.launcher = launcher;
    }

    @FXML
    public void initialize() {
        for (Rol r : Rol.values()) cbRol.getItems().add(r);
        cbRol.setValue(Rol.USUARIO);
    }

    @FXML
    private void onLogin() {
        lblError.setText("");
        String correo     = txtUsuario.getText().trim();
        String contrasena = pfContrasena.getText().trim();
        Rol    rol        = cbRol.getValue();

        if (correo.isEmpty() || contrasena.isEmpty() || rol == null) {
            lblError.setText("Completa todos los campos.");
            return;
        }

        Usuario encontrado = null;
        for (Usuario u : Launcher.usuarios) {
            if (u.getCorreo().equals(correo)
                    && u.getContraseña().equals(contrasena)
                    && u.getRol() == rol) {
                encontrado = u;
                break;
            }
        }

        if (encontrado != null) {
            SesionActual.getInstancia().iniciarSesion(encontrado);
            if (rol == Rol.ADMINISTRADOR) {
                launcher.openVistaPrincipalAdmin();
            } else {
                launcher.openVistaPrincipalUsuario();
            }
        } else {
            lblError.setText("Usuario o contraseña incorrectos.");
        }
    }
}
