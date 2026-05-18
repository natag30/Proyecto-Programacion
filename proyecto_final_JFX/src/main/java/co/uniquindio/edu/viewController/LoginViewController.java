package co.uniquindio.edu.viewController;

import co.uniquindio.edu.controller.UsuarioController;
import co.uniquindio.edu.proyecto_final_jfx.Launcher;
import co.uniquindio.edu.proyecto_final_jfx.model.enums.Rol;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginViewController {

    @FXML private TextField tfUsuario;
    @FXML private PasswordField pfContrasena;
    @FXML private ComboBox<Rol> cbRol;
    @FXML private Label lblError;

    private Launcher launcher;
    private final UsuarioController usuarioController = new UsuarioController();

    public void setLauncher(Launcher launcher) {
        this.launcher = launcher;
    }

    @FXML
    public void initialize() {
        cbRol.getItems().addAll(Rol.values());
        cbRol.setValue(Rol.USUARIO);
    }

    @FXML
    private void onLogin() {
        String inputUsuario    = tfUsuario.getText().trim();
        String inputContrasena = pfContrasena.getText().trim();
        Rol rolSeleccionado    = cbRol.getValue();

        lblError.setText("");

        if (inputUsuario.isEmpty() || inputContrasena.isEmpty() || rolSeleccionado == null) {
            lblError.setText("Completa todos los campos.");
            return;
        }

        if (rolSeleccionado == Rol.ADMINISTRADOR) {
            if (!usuarioController.loginAdministrador(inputUsuario, inputContrasena)) {
                lblError.setText("Credenciales de administrador incorrectas.");
                return;
            }
            launcher.openVistaPrincipalAdmin();
        } else {
            if (!usuarioController.loginUsuario(inputUsuario, inputContrasena)) {
                lblError.setText("Credenciales de usuario incorrectas.");
                return;
            }
            launcher.openVistaPrincipalUsuario();
        }
    }
}