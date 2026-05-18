package co.uniquindio.edu.proyecto_final_jfx;

import co.uniquindio.edu.proyecto_final_jfx.model.enums.Categoria;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Asiento;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Evento;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Recinto;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Zona;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.creacionales.singleton.PlataformaEventos;
import co.uniquindio.edu.proyecto_final_jfx.model.usuario.Administrador;
import co.uniquindio.edu.proyecto_final_jfx.model.usuario.Usuario;
import co.uniquindio.edu.viewController.LoginViewController;
import co.uniquindio.edu.viewController.PrincipalAdminViewController;
import co.uniquindio.edu.viewController.PrincipalUsuarioViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class Launcher extends Application {

    private Stage primaryStage;
    private final PlataformaEventos plataforma = PlataformaEventos.getInstancia();

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        inicializarData();
        showLogin();
    }

    private void inicializarData() {
        // Evento 1 – Rock en la Montaña (Armenia)
        Recinto r1 = new Recinto("Teatro Universitario", "Calle 12 #5-30", "Armenia");

        Zona z1General = new Zona("General", 100, 80000);
        generarAsientos(z1General, "A", 10);
        generarAsientos(z1General, "B", 10);
        generarAsientos(z1General, "C", 10);
        generarAsientos(z1General, "D", 10);
        generarAsientos(z1General, "E", 10);
        generarAsientos(z1General, "F", 10);
        generarAsientos(z1General, "G", 10);
        generarAsientos(z1General, "H", 10);
        generarAsientos(z1General, "I", 10);
        generarAsientos(z1General, "J", 10);
        r1.agregarZona(z1General);

        Zona z1Vip = new Zona("VIP", 20, 200000);
        generarAsientos(z1Vip, "V", 10);
        generarAsientos(z1Vip, "W", 10);
        r1.agregarZona(z1Vip);

        Evento e1 = new Evento("Rock en la Montana", Categoria.CONCIERTO, "Armenia",
                LocalDateTime.of(2026, 6, 15, 20, 0), r1);
        e1.publicar();
        plataforma.agregarEvento(e1);

        // Evento 2 – Hamlet en el Risaralda (Pereira)
        Recinto r2 = new Recinto("Teatro Municipal", "Av. Circunvalar #23-10", "Pereira");

        Zona z2Platea = new Zona("Platea", 80, 60000);
        generarAsientos(z2Platea, "A", 10);
        generarAsientos(z2Platea, "B", 10);
        generarAsientos(z2Platea, "C", 10);
        generarAsientos(z2Platea, "D", 10);
        generarAsientos(z2Platea, "E", 10);
        generarAsientos(z2Platea, "F", 10);
        generarAsientos(z2Platea, "G", 10);
        generarAsientos(z2Platea, "H", 10);
        r2.agregarZona(z2Platea);

        Zona z2Palco = new Zona("Palco", 30, 120000);
        generarAsientos(z2Palco, "P", 15);
        generarAsientos(z2Palco, "Q", 15);
        r2.agregarZona(z2Palco);

        Evento e2 = new Evento("Hamlet en el Risaralda", Categoria.TEATRO, "Pereira",
                LocalDateTime.of(2026, 7, 1, 19, 30), r2);
        e2.publicar();
        plataforma.agregarEvento(e2);

        // Evento 3 – Innovacion Tech 2026 (Manizales)
        Recinto r3 = new Recinto("Centro de Convenciones", "Cra 23 #62-15", "Manizales");

        Zona z3Auditorio = new Zona("Auditorio", 200, 30000);
        generarAsientos(z3Auditorio, "A", 20);
        generarAsientos(z3Auditorio, "B", 20);
        generarAsientos(z3Auditorio, "C", 20);
        generarAsientos(z3Auditorio, "D", 20);
        generarAsientos(z3Auditorio, "E", 20);
        generarAsientos(z3Auditorio, "F", 20);
        generarAsientos(z3Auditorio, "G", 20);
        generarAsientos(z3Auditorio, "H", 20);
        generarAsientos(z3Auditorio, "I", 20);
        generarAsientos(z3Auditorio, "J", 20);
        r3.agregarZona(z3Auditorio);

        Evento e3 = new Evento("Innovacion Tech 2026", Categoria.CONFERENCIA, "Manizales",
                LocalDateTime.of(2026, 8, 20, 8, 0), r3);
        e3.publicar();
        plataforma.agregarEvento(e3);

        plataforma.agregarAdministrador(
                new Administrador("Admin Sistema", "admin@sistema.com", 3000000, 1, "admin", "1234"));
        plataforma.agregarUsuario(new Usuario("Laura Gomez", 1, "laura@gmail.com", 310123456, "usuario1", "1234"));
        plataforma.agregarUsuario(new Usuario("Carlos Perez", 2, "carlos@gmail.com", 320987654, "usuario2", "1234"));
    }

    private void generarAsientos(Zona zona, String fila, int cantidad) {
        for (int i = 1; i <= cantidad; i++) {
            zona.agregarAsiento(new Asiento(fila, i, zona));
        }
    }

    public void showLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/co/uniquindio/edu/proyecto_final_jfx/login.fxml"));
            Parent root = loader.load();
            LoginViewController controller = loader.getController();
            controller.setLauncher(this);
            primaryStage.setTitle("Plataforma de Eventos - Ingresar");
            primaryStage.setScene(new Scene(root, 420, 520));
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openVistaPrincipalAdmin() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/co/uniquindio/edu/proyecto_final_jfx/principalAdmin.fxml"));
            Parent root = loader.load();
            PrincipalAdminViewController controller = loader.getController();
            controller.setLauncher(this);
            controller.inicializar();
            primaryStage.setTitle("Panel Administrador");
            primaryStage.setScene(new Scene(root, 900, 600));
            primaryStage.setResizable(true);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openVistaPrincipalUsuario() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/co/uniquindio/edu/proyecto_final_jfx/principalUsuario.fxml"));
            Parent root = loader.load();
            PrincipalUsuarioViewController controller = loader.getController();
            controller.setLauncher(this);
            controller.inicializar();
            primaryStage.setTitle("Panel Usuario");
            primaryStage.setScene(new Scene(root, 900, 600));
            primaryStage.setResizable(true);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() { return primaryStage; }

    public static void main(String[] args) {
        launch(args);
    }
}