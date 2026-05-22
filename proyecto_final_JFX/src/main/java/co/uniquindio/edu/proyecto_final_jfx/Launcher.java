package co.uniquindio.edu.proyecto_final_jfx;

import co.uniquindio.edu.proyecto_final_jfx.controller.PersistenciaCompras;
import co.uniquindio.edu.proyecto_final_jfx.model.compra.Compra;
import co.uniquindio.edu.proyecto_final_jfx.model.enums.Categoria;
import co.uniquindio.edu.proyecto_final_jfx.model.enums.Rol;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Asiento;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Evento;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Recinto;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Zona;
import co.uniquindio.edu.proyecto_final_jfx.model.usuario.Usuario;
import co.uniquindio.edu.proyecto_final_jfx.viewController.LoginViewController;
import co.uniquindio.edu.proyecto_final_jfx.viewController.PrincipalAdminViewController;
import co.uniquindio.edu.proyecto_final_jfx.viewController.PrincipalUsuarioViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Launcher extends Application {

    public static List<Evento>  eventos  = new ArrayList<>();
    public static List<Usuario> usuarios = new ArrayList<>();
    public static List<Compra>  compras  = new ArrayList<>();

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        inicializarData();
        showLogin();
    }

    private void inicializarData() {
        // ── Evento 1: Rock en la Montana – Armenia ──
        Recinto r1 = new Recinto("Coliseo del Quindio", "Av. Bolívar #15-20", "Armenia");
        Zona r1g = new Zona("General", 30, 50000);
        generarAsientos(r1g, "A", 10);
        generarAsientos(r1g, "B", 10);
        generarAsientos(r1g, "C", 10);
        Zona r1v = new Zona("VIP", 10, 120000);
        generarAsientos(r1v, "V", 10);
        r1.agregarZona(r1g);
        r1.agregarZona(r1v);
        Evento e1 = new Evento("Rock en la Montana", Categoria.CONCIERTO, "Armenia",
                LocalDateTime.of(2026, 8, 15, 20, 0), r1);
        e1.publicar();
        eventos.add(e1);

        // ── Evento 2: Hamlet en el Risaralda – Pereira ──
        Recinto r2 = new Recinto("Teatro Municipal", "Cra 8 #23-10", "Pereira");
        Zona r2g = new Zona("General", 30, 50000);
        generarAsientos(r2g, "A", 10);
        generarAsientos(r2g, "B", 10);
        generarAsientos(r2g, "C", 10);
        Zona r2v = new Zona("VIP", 10, 120000);
        generarAsientos(r2v, "V", 10);
        r2.agregarZona(r2g);
        r2.agregarZona(r2v);
        Evento e2 = new Evento("Hamlet en el Risaralda", Categoria.TEATRO, "Pereira",
                LocalDateTime.of(2026, 9, 20, 19, 30), r2);
        e2.publicar();
        eventos.add(e2);

        // ── Evento 3: Innovacion Tech 2026 – Manizales ──
        Recinto r3 = new Recinto("Centro de Convenciones", "Calle 50 #12-05", "Manizales");
        Zona r3g = new Zona("General", 30, 50000);
        generarAsientos(r3g, "A", 10);
        generarAsientos(r3g, "B", 10);
        generarAsientos(r3g, "C", 10);
        Zona r3v = new Zona("VIP", 10, 120000);
        generarAsientos(r3v, "V", 10);
        r3.agregarZona(r3g);
        r3.agregarZona(r3v);
        Evento e3 = new Evento("Innovacion Tech 2026", Categoria.CONFERENCIA, "Manizales",
                LocalDateTime.of(2026, 10, 5, 8, 0), r3);
        e3.publicar();
        eventos.add(e3);

        // ── Admin ──
        Usuario admin = new Usuario("Admin", 0, "admin@uq.edu", 0L, "admin", "1234");
        admin.setRol(Rol.ADMINISTRADOR);
        usuarios.add(admin);

        // ── Usuarios regulares ──
        usuarios.add(new Usuario("Laura Gomez", 1, "laura@uq.edu", 3101234560L, "laura", "1234"));
        usuarios.add(new Usuario("Carlos Perez", 2, "carlos@uq.edu", 3209876540L, "carlos", "1234"));

        PersistenciaCompras.cargarCompras(compras, eventos, usuarios);
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
            LoginViewController ctrl = loader.getController();
            ctrl.setLauncher(this);
            primaryStage.setTitle("EventosUQ");
            primaryStage.setScene(new Scene(root, 420, 500));
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
            PrincipalAdminViewController ctrl = loader.getController();
            ctrl.setLauncher(this);
            ctrl.setStage(primaryStage);
            primaryStage.setTitle("EventosUQ – Administrador");
            primaryStage.setScene(new Scene(root, 950, 620));
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
            PrincipalUsuarioViewController ctrl = loader.getController();
            ctrl.setLauncher(this);
            ctrl.setStage(primaryStage);
            primaryStage.setTitle("EventosUQ – Usuario");
            primaryStage.setScene(new Scene(root, 950, 620));
            primaryStage.setResizable(true);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
