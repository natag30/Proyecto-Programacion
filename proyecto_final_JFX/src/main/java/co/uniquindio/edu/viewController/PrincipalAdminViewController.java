package co.uniquindio.edu.viewController;

import co.uniquindio.edu.controller.EventoController;
import co.uniquindio.edu.controller.UsuarioController;
import co.uniquindio.edu.proyecto_final_jfx.Launcher;
import co.uniquindio.edu.proyecto_final_jfx.model.enums.Categoria;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Evento;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Recinto;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Zona;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.observer.PanelMetricas;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.creacionales.singleton.PlataformaEventos;
import co.uniquindio.edu.proyecto_final_jfx.model.usuario.Usuario;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.time.LocalDateTime;

public class PrincipalAdminViewController {

    @FXML private Label lblBienvenida;
    @FXML private AnchorPane contenidoCentral;

    private Launcher launcher;
    private final EventoController eventoController = new EventoController();
    private final UsuarioController usuarioController = new UsuarioController();

    public void setLauncher(Launcher launcher) {
        this.launcher = launcher;
    }

    public void inicializar() {
        if (usuarioController.estaAutenticado()) {
            lblBienvenida.setText("Bienvenido, " + usuarioController.getUsuarioActual().getNombre());
        }
        onVerEventos();
    }

    @FXML
    private void onCerrarSesion() {
        usuarioController.cerrarSesion();
        launcher.showLogin();
    }

    @FXML
    private void onVerEventos() {
        VBox contenido = new VBox(10);
        contenido.setPadding(new Insets(10));

        Label titulo = new Label("Gestion de Eventos");
        titulo.setStyle("-fx-font-size: 17px; -fx-font-weight: bold;");

        TableView<Evento> tabla = new TableView<>();

        TableColumn<Evento, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getIdEvento()));
        colId.setPrefWidth(45);

        TableColumn<Evento, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getNombre()));
        colNombre.setPrefWidth(190);

        TableColumn<Evento, String> colCategoria = new TableColumn<>("Categoria");
        colCategoria.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getCategoria().name()));
        colCategoria.setPrefWidth(100);

        TableColumn<Evento, String> colCiudad = new TableColumn<>("Ciudad");
        colCiudad.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getCiudad()));
        colCiudad.setPrefWidth(90);

        TableColumn<Evento, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getEstado().name()));
        colEstado.setPrefWidth(100);

        TableColumn<Evento, Integer> colAforo = new TableColumn<>("Disponible");
        colAforo.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getAforoDisponible()));
        colAforo.setPrefWidth(75);

        tabla.getColumns().addAll(colId, colNombre, colCategoria, colCiudad, colEstado, colAforo);
        tabla.setItems(FXCollections.observableArrayList(eventoController.getEventos()));
        tabla.setPrefHeight(290);

        HBox botones = new HBox(8);
        Button btnPublicar  = crearBoton("Publicar",  "#27ae60");
        Button btnPausar    = crearBoton("Pausar",    "#f39c12");
        Button btnCancelar  = crearBoton("Cancelar",  "#e74c3c");
        Button btnFinalizar = crearBoton("Finalizar", "#8e44ad");

        btnPublicar.setOnAction(e -> {
            Evento sel = tabla.getSelectionModel().getSelectedItem();
            if (sel != null) { eventoController.publicarEvento(sel); tabla.refresh(); }
        });
        btnPausar.setOnAction(e -> {
            Evento sel = tabla.getSelectionModel().getSelectedItem();
            if (sel != null) { eventoController.pausarEvento(sel); tabla.refresh(); }
        });
        btnCancelar.setOnAction(e -> {
            Evento sel = tabla.getSelectionModel().getSelectedItem();
            if (sel != null) { eventoController.cancelarEvento(sel); tabla.refresh(); }
        });
        btnFinalizar.setOnAction(e -> {
            Evento sel = tabla.getSelectionModel().getSelectedItem();
            if (sel != null) { eventoController.finalizarEvento(sel); tabla.refresh(); }
        });

        botones.getChildren().addAll(btnPublicar, btnPausar, btnCancelar, btnFinalizar);
        contenido.getChildren().addAll(titulo, tabla, botones);
        setContenido(contenido);
    }

    @FXML
    private void onAgregarEvento() {
        VBox contenido = new VBox(10);
        contenido.setPadding(new Insets(10));

        Label titulo = new Label("Agregar Nuevo Evento");
        titulo.setStyle("-fx-font-size: 17px; -fx-font-weight: bold;");

        TextField tfNombre    = campo("Nombre del evento");
        TextField tfCiudad    = campo("Ciudad");
        TextField tfRecinto   = campo("Nombre del recinto");
        TextField tfDireccion = campo("Direccion del recinto");
        TextField tfCapacidad = campo("Capacidad (numero entero)");
        TextField tfPrecio    = campo("Precio base (numero)");

        ComboBox<Categoria> cbCategoria = new ComboBox<>();
        cbCategoria.getItems().addAll(Categoria.values());
        cbCategoria.setPromptText("Categoria");
        cbCategoria.setPrefWidth(280);

        DatePicker dpFecha = new DatePicker();
        dpFecha.setPromptText("Fecha del evento");
        dpFecha.setPrefWidth(280);

        Label lblMsg = new Label();

        Button btnGuardar = crearBoton("Guardar Evento", "#27ae60");
        btnGuardar.setOnAction(e -> {
            try {
                String nombre   = tfNombre.getText().trim();
                String ciudad   = tfCiudad.getText().trim();
                String recNom   = tfRecinto.getText().trim();
                String recDir   = tfDireccion.getText().trim();
                int capacidad   = Integer.parseInt(tfCapacidad.getText().trim());
                double precio   = Double.parseDouble(tfPrecio.getText().trim());
                Categoria cat   = cbCategoria.getValue();

                if (nombre.isEmpty() || ciudad.isEmpty() || recNom.isEmpty()
                        || cat == null || dpFecha.getValue() == null) {
                    estilo(lblMsg, "Completa todos los campos.", false);
                    return;
                }

                Recinto recinto = new Recinto(recNom, recDir, ciudad);
                recinto.agregarZona(new Zona("General", capacidad, precio));
                LocalDateTime fechaHora = dpFecha.getValue().atTime(20, 0);
                eventoController.agregarEvento(nombre, cat, ciudad, fechaHora, recinto);
                estilo(lblMsg, "Evento '" + nombre + "' agregado correctamente.", true);
            } catch (NumberFormatException ex) {
                estilo(lblMsg, "Capacidad y precio deben ser numeros validos.", false);
            }
        });

        contenido.getChildren().addAll(titulo, tfNombre, cbCategoria, tfCiudad,
                tfRecinto, tfDireccion, tfCapacidad, tfPrecio, dpFecha, btnGuardar, lblMsg);
        setContenido(contenido);
    }

    @FXML
    private void onVerUsuarios() {
        VBox contenido = new VBox(10);
        contenido.setPadding(new Insets(10));

        Label titulo = new Label("Usuarios Registrados");
        titulo.setStyle("-fx-font-size: 17px; -fx-font-weight: bold;");

        TableView<Usuario> tabla = new TableView<>();

        TableColumn<Usuario, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getIdUsuario()));
        colId.setPrefWidth(55);

        TableColumn<Usuario, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getNombre()));
        colNombre.setPrefWidth(200);

        TableColumn<Usuario, String> colCorreo = new TableColumn<>("Correo");
        colCorreo.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getCorreo()));
        colCorreo.setPrefWidth(210);

        TableColumn<Usuario, String> colRol = new TableColumn<>("Rol");
        colRol.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getRol().name()));
        colRol.setPrefWidth(100);

        tabla.getColumns().addAll(colId, colNombre, colCorreo, colRol);
        tabla.setItems(FXCollections.observableArrayList(usuarioController.getUsuarios()));
        tabla.setPrefHeight(380);

        contenido.getChildren().addAll(titulo, tabla);
        setContenido(contenido);
    }

    @FXML
    private void onVerMetricas() {
        VBox contenido = new VBox(14);
        contenido.setPadding(new Insets(20));

        Label titulo = new Label("Metricas del Sistema");
        titulo.setStyle("-fx-font-size: 17px; -fx-font-weight: bold;");

        PlataformaEventos plataforma = PlataformaEventos.getInstancia();
        PanelMetricas panel = plataforma.getPanelMetricas();

        contenido.getChildren().addAll(
                titulo,
                metrica("Eventos publicados",           String.valueOf(panel.getTotalPublicados())),
                metrica("Eventos cancelados",           String.valueOf(panel.getTotalCancelados())),
                metrica("Total eventos en plataforma",  String.valueOf(plataforma.getEventos().size())),
                metrica("Usuarios registrados",         String.valueOf(plataforma.getUsuarios().size())),
                metrica("Compras realizadas",           String.valueOf(plataforma.getCompras().size()))
        );
        setContenido(contenido);
    }

    // ─── utilidades ──────────────────────────────────────────────────────────

    private void setContenido(Node nodo) {
        contenidoCentral.getChildren().clear();
        AnchorPane.setTopAnchor(nodo, 0.0);
        AnchorPane.setLeftAnchor(nodo, 0.0);
        AnchorPane.setRightAnchor(nodo, 0.0);
        AnchorPane.setBottomAnchor(nodo, 0.0);
        contenidoCentral.getChildren().add(nodo);
    }

    private Button crearBoton(String texto, String color) {
        Button b = new Button(texto);
        b.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-cursor: hand;");
        return b;
    }

    private TextField campo(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setPrefWidth(280);
        return tf;
    }

    private void estilo(Label lbl, String msg, boolean ok) {
        lbl.setText(msg);
        lbl.setStyle("-fx-text-fill: " + (ok ? "#27ae60" : "#e74c3c") + ";");
    }

    private HBox metrica(String etiqueta, String valor) {
        Label lbl = new Label(etiqueta + ":  ");
        lbl.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");
        Label val = new Label(valor);
        val.setStyle("-fx-font-size: 13px;");
        HBox hb = new HBox(lbl, val);
        hb.setPadding(new Insets(4, 0, 4, 0));
        return hb;
    }
}