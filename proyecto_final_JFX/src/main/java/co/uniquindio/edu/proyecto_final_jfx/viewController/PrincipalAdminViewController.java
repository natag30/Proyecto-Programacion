package co.uniquindio.edu.proyecto_final_jfx.viewController;

import co.uniquindio.edu.proyecto_final_jfx.Launcher;
import co.uniquindio.edu.proyecto_final_jfx.controller.PersistenciaCompras;
import co.uniquindio.edu.proyecto_final_jfx.model.compra.Compra;
import co.uniquindio.edu.proyecto_final_jfx.model.enums.Categoria;
import co.uniquindio.edu.proyecto_final_jfx.model.enums.EstadoCompra;
import co.uniquindio.edu.proyecto_final_jfx.model.enums.EstadoEvento;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Asiento;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Evento;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Recinto;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Zona;
import co.uniquindio.edu.proyecto_final_jfx.model.usuario.SesionActual;
import co.uniquindio.edu.proyecto_final_jfx.model.usuario.Usuario;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class PrincipalAdminViewController {

    @FXML private Label      lblBienvenida;
    @FXML private AnchorPane contenidoCentral;
    @FXML private Button     btnEventos;
    @FXML private Button     btnAgregarEvento;
    @FXML private Button     btnUsuarios;
    @FXML private Button     btnCompras;
    @FXML private Button     btnIncidencias;
    @FXML private Button     btnMetricas;

    private Launcher launcher;
    private Stage    stage;
    private List<Button> navBtns;

    public void setLauncher(Launcher l) { this.launcher = l; }
    public void setStage(Stage s)       { this.stage = s; }

    @FXML
    public void initialize() {
        navBtns = Arrays.asList(
            btnEventos, btnAgregarEvento, btnUsuarios,
            btnCompras, btnIncidencias, btnMetricas);

        lblBienvenida.setText("Bienvenido, " +
            SesionActual.getInstancia().getUsuarioActual().getNombre());
        onVerEventos();
    }

    // ── Navegación ──────────────────────────────────────────────────

    private void resaltarBoton(Button activo) {
        for (Button b : navBtns) b.setStyle(Estilos.SIDEBAR_BTN);
        activo.setStyle(Estilos.SIDEBAR_BTN_ACTIVO_ADMIN);
    }

    private void setContenido(Node nodo) {
        contenidoCentral.getChildren().setAll(nodo);
        AnchorPane.setTopAnchor(nodo, 0.0);
        AnchorPane.setLeftAnchor(nodo, 0.0);
        AnchorPane.setRightAnchor(nodo, 0.0);
        AnchorPane.setBottomAnchor(nodo, 0.0);
    }

    // ── Eventos ─────────────────────────────────────────────────────

    @FXML
    public void onVerEventos() {
        resaltarBoton(btnEventos);

        long activos    = Launcher.eventos.stream().filter(e -> e.getEstado() == EstadoEvento.PUBLICADO).count();
        long vendidas   = Launcher.compras.stream().mapToLong(c -> c.getEntradas().size()).sum();
        int  disponibles = Launcher.eventos.stream().mapToInt(Evento::getAforoDisponible).sum();
        long cancelados = Launcher.eventos.stream().filter(e -> e.getEstado() == EstadoEvento.CANCELADO).count();

        ScrollPane scroll = new ScrollPane();
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: #f5f5f5; -fx-background: #f5f5f5;");

        VBox root = new VBox(14);
        root.setStyle("-fx-padding: 16; -fx-background-color: #f5f5f5;");

        // Cabecera
        HBox cabecera = new HBox();
        cabecera.setAlignment(Pos.CENTER_LEFT);
        VBox tituloBox = new VBox(2);
        Label titulo = new Label("Gestion de eventos");
        titulo.setStyle(Estilos.LABEL_TITULO);
        Label sub = new Label(Launcher.eventos.size() + " evento(s) registrado(s)");
        sub.setStyle(Estilos.LABEL_SUB);
        tituloBox.getChildren().addAll(titulo, sub);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Button btnNuevo = new Button("Nuevo evento");
        btnNuevo.setStyle(Estilos.BTN_PRIMARIO_ADMIN);
        btnNuevo.setOnAction(e -> onAgregarEvento());
        cabecera.getChildren().addAll(tituloBox, spacer, btnNuevo);

        // Stats
        HBox statsBox = new HBox(10);
        statsBox.getChildren().addAll(
            crearStat("ACTIVOS",     String.valueOf(activos)),
            crearStat("VENDIDAS",    String.valueOf(vendidas)),
            crearStat("DISPONIBLES", String.valueOf(disponibles)),
            crearStat("CANCELADOS",  String.valueOf(cancelados))
        );

        // Tabla
        TableView<Evento> tabla = new TableView<>();
        tabla.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0;" +
                       "-fx-border-radius: 8; -fx-background-radius: 8;");
        VBox.setVgrow(tabla, Priority.ALWAYS);

        TableColumn<Evento, String> colNombre = new TableColumn<>("NOMBRE");
        colNombre.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getNombre()));
        colNombre.setPrefWidth(200);

        TableColumn<Evento, String> colFecha = new TableColumn<>("FECHA");
        colFecha.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(
            d.getValue().getFechaHora().toLocalDate().toString()));
        colFecha.setPrefWidth(100);

        TableColumn<Evento, String> colCiudad = new TableColumn<>("CIUDAD");
        colCiudad.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getCiudad()));
        colCiudad.setPrefWidth(90);

        TableColumn<Evento, String> colCat = new TableColumn<>("CATEGORIA");
        colCat.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getCategoria().toString()));
        colCat.setPrefWidth(110);

        TableColumn<Evento, EstadoEvento> colEstado = new TableColumn<>("ESTADO");
        colEstado.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getEstado()));
        colEstado.setPrefWidth(110);
        colEstado.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(EstadoEvento estado, boolean empty) {
                super.updateItem(estado, empty);
                if (empty || estado == null) { setGraphic(null); return; }
                Label badge = new Label(estado.toString());
                badge.setStyle(badgeEvento(estado));
                setGraphic(badge);
            }
        });

        TableColumn<Evento, Void> colAcc = new TableColumn<>("ACCIONES");
        colAcc.setPrefWidth(160);
        colAcc.setCellFactory(col -> new TableCell<>() {
            final Button btnEditar  = new Button("Editar");
            final Button btnEstado  = new Button("Estado");
            final HBox   hbox       = new HBox(6, btnEditar, btnEstado);
            {
                btnEditar.setStyle(Estilos.BTN_SECUNDARIO);
                btnEstado.setStyle(Estilos.BTN_SECUNDARIO);
                btnEditar.setOnAction(e -> {
                    Evento ev = getTableView().getItems().get(getIndex());
                    onEditarEvento(ev);
                });
                btnEstado.setOnAction(e -> {
                    Evento ev = getTableView().getItems().get(getIndex());
                    if (ev.getEstado() == EstadoEvento.PUBLICADO) ev.pausar();
                    else ev.publicar();
                    getTableView().refresh();
                });
            }
            @Override protected void updateItem(Void v, boolean empty) {
                super.updateItem(v, empty);
                setGraphic(empty ? null : hbox);
            }
        });

        tabla.getColumns().addAll(colNombre, colFecha, colCiudad, colCat, colEstado, colAcc);
        tabla.setItems(FXCollections.observableArrayList(Launcher.eventos));

        root.getChildren().addAll(cabecera, statsBox, tabla);
        scroll.setContent(root);
        setContenido(scroll);
    }

    private VBox crearStat(String etiqueta, String valor) {
        VBox card = new VBox(4);
        card.setStyle(Estilos.STAT_CARD);
        HBox.setHgrow(card, Priority.ALWAYS);
        Label lbl = new Label(etiqueta);
        lbl.setStyle(Estilos.LABEL_HINT);
        Label val = new Label(valor);
        val.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #111111;");
        card.getChildren().addAll(lbl, val);
        return card;
    }

    private String badgeEvento(EstadoEvento estado) {
        return switch (estado) {
            case PUBLICADO  -> Estilos.BADGE_VERDE;
            case BORRADOR   -> Estilos.BADGE_AMBER;
            case CANCELADO  -> Estilos.BADGE_ROJO;
            default         -> Estilos.BADGE_GRIS;
        };
    }

    // ── Agregar Evento ───────────────────────────────────────────────

    @FXML
    public void onAgregarEvento() {
        resaltarBoton(btnAgregarEvento);
        mostrarFormularioEvento(null);
    }

    private void onEditarEvento(Evento evento) {
        resaltarBoton(btnEventos);
        mostrarFormularioEvento(evento);
    }

    private void mostrarFormularioEvento(Evento eventoEditar) {
        boolean editar = eventoEditar != null;

        ScrollPane scroll = new ScrollPane();
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: #f5f5f5; -fx-background: #f5f5f5;");

        VBox root = new VBox(16);
        root.setStyle("-fx-padding: 16; -fx-background-color: #f5f5f5;");

        Label titulo = new Label(editar ? "Editar evento" : "Crear nuevo evento");
        titulo.setStyle(Estilos.LABEL_TITULO);

        VBox form = new VBox(12);
        form.setStyle(Estilos.CARD);
        form.setMaxWidth(580);

        GridPane grid = new GridPane();
        grid.setHgap(14); grid.setVgap(10);
        ColumnConstraints c0 = new ColumnConstraints(120);
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(c0, c1);

        TextField tfNombre    = campo("Nombre del evento");
        TextField tfCiudad    = campo("Ciudad");
        ComboBox<Categoria> cbCat = new ComboBox<>();
        cbCat.getItems().addAll(Categoria.values());
        cbCat.setPromptText("Categoría");
        cbCat.setMaxWidth(Double.MAX_VALUE);
        cbCat.setStyle(Estilos.CAMPO_TEXTO);
        DatePicker dpFecha    = new DatePicker();
        dpFecha.setPromptText("Fecha");
        dpFecha.setMaxWidth(Double.MAX_VALUE);
        dpFecha.setStyle(Estilos.CAMPO_TEXTO);
        TextField tfHora      = campo("HH:MM (ej. 20:00)");
        TextArea  taDesc      = new TextArea();
        taDesc.setStyle(Estilos.CAMPO_TEXTO);
        taDesc.setPromptText("Descripción del evento");
        taDesc.setPrefRowCount(3);
        TextField tfRecinto   = campo("Nombre del recinto");
        TextField tfDireccion = campo("Dirección del recinto");

        if (editar) {
            tfNombre.setText(eventoEditar.getNombre());
            tfCiudad.setText(eventoEditar.getCiudad());
            cbCat.setValue(eventoEditar.getCategoria());
            dpFecha.setValue(eventoEditar.getFechaHora().toLocalDate());
            tfHora.setText(eventoEditar.getFechaHora().getHour() + ":" +
                String.format("%02d", eventoEditar.getFechaHora().getMinute()));
            if (eventoEditar.getDescripcion() != null)
                taDesc.setText(eventoEditar.getDescripcion());
            tfRecinto.setText(eventoEditar.getRecinto().getNombre());
            tfDireccion.setText(eventoEditar.getRecinto().getDireccion());
        }

        addFila(grid, "Nombre:",     tfNombre,    0);
        addFila(grid, "Ciudad:",     tfCiudad,    1);
        addFila(grid, "Categoría:",  cbCat,       2);
        addFila(grid, "Fecha:",      dpFecha,     3);
        addFila(grid, "Hora:",       tfHora,      4);
        addFila(grid, "Descripción:",taDesc,      5);
        addFila(grid, "Recinto:",    tfRecinto,   6);
        addFila(grid, "Dirección:",  tfDireccion, 7);

        Label lblMsg = new Label();
        Button btnGuardar  = new Button(editar ? "Actualizar" : "Guardar evento");
        btnGuardar.setStyle(Estilos.BTN_PRIMARIO_ADMIN);
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle(Estilos.BTN_SECUNDARIO);
        btnCancelar.setOnAction(e -> onVerEventos());

        btnGuardar.setOnAction(e -> {
            String nombre = tfNombre.getText().trim();
            String ciudad = tfCiudad.getText().trim();
            Categoria cat = cbCat.getValue();
            LocalDate fecha = dpFecha.getValue();
            if (nombre.isEmpty() || ciudad.isEmpty() || cat == null || fecha == null) {
                lblMsg.setText("Completa nombre, ciudad, categoría y fecha.");
                lblMsg.setStyle("-fx-text-fill: #A32D2D; -fx-font-size: 12px;");
                return;
            }
            int hora = 20, min = 0;
            try {
                String[] hm = tfHora.getText().trim().split(":");
                hora = Integer.parseInt(hm[0]);
                min  = hm.length > 1 ? Integer.parseInt(hm[1]) : 0;
            } catch (Exception ex) { /* usa defaults */ }
            LocalDateTime fechaHora = fecha.atTime(hora, min);

            if (editar) {
                eventoEditar.setNombre(nombre);
                eventoEditar.setCiudad(ciudad);
                eventoEditar.setFechaHora(fechaHora);
                if (!taDesc.getText().isBlank())
                    eventoEditar.setDescripcion(taDesc.getText().trim());
                lblMsg.setText("Evento actualizado.");
                lblMsg.setStyle("-fx-text-fill: #0F6E56; -fx-font-size: 12px;");
            } else {
                String nomR = tfRecinto.getText().isBlank() ? "Recinto" : tfRecinto.getText().trim();
                String dirR = tfDireccion.getText().isBlank() ? "Sin dirección" : tfDireccion.getText().trim();
                Recinto recinto = new Recinto(nomR, dirR, ciudad);
                Zona general = new Zona("General", 30, 50000);
                for (String f : new String[]{"A","B","C"})
                    for (int j = 1; j <= 10; j++)
                        general.agregarAsiento(new Asiento(f, j, general));
                recinto.agregarZona(general);
                Zona vip = new Zona("VIP", 10, 120000);
                for (int j = 1; j <= 10; j++)
                    vip.agregarAsiento(new Asiento("V", j, vip));
                recinto.agregarZona(vip);
                Evento nuevo = new Evento(nombre, cat, ciudad, fechaHora, recinto);
                if (!taDesc.getText().isBlank()) nuevo.setDescripcion(taDesc.getText().trim());
                Launcher.eventos.add(nuevo);
                lblMsg.setText("Evento '" + nombre + "' creado.");
                lblMsg.setStyle("-fx-text-fill: #0F6E56; -fx-font-size: 12px;");
            }
        });

        HBox botones = new HBox(8, btnGuardar, btnCancelar);
        form.getChildren().addAll(grid, botones, lblMsg);
        root.getChildren().addAll(titulo, form);
        scroll.setContent(root);
        setContenido(scroll);
    }

    private TextField campo(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setStyle(Estilos.CAMPO_TEXTO);
        return tf;
    }

    private void addFila(GridPane grid, String etiqueta, Node control, int row) {
        Label lbl = new Label(etiqueta);
        lbl.setStyle(Estilos.LABEL_CAMPO);
        GridPane.setConstraints(lbl, 0, row);
        GridPane.setConstraints(control, 1, row);
        GridPane.setHgrow(control, Priority.ALWAYS);
        grid.getChildren().addAll(lbl, control);
    }

    // ── Usuarios ─────────────────────────────────────────────────────

    @FXML
    public void onVerUsuarios() {
        resaltarBoton(btnUsuarios);

        VBox root = new VBox(14);
        root.setStyle("-fx-padding: 16; -fx-background-color: #f5f5f5;");

        Label titulo = new Label("Usuarios registrados");
        titulo.setStyle(Estilos.LABEL_TITULO);

        TableView<Usuario> tabla = new TableView<>();
        tabla.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0;");
        VBox.setVgrow(tabla, Priority.ALWAYS);

        TableColumn<Usuario, String> colNombre = new TableColumn<>("NOMBRE");
        colNombre.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getNombre()));
        colNombre.setPrefWidth(220);

        TableColumn<Usuario, String> colCorreo = new TableColumn<>("CORREO");
        colCorreo.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getCorreo()));
        colCorreo.setPrefWidth(220);

        TableColumn<Usuario, String> colRol = new TableColumn<>("ROL");
        colRol.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getRol().toString()));
        colRol.setPrefWidth(120);

        tabla.getColumns().addAll(colNombre, colCorreo, colRol);
        tabla.setItems(FXCollections.observableArrayList(Launcher.usuarios));

        root.getChildren().addAll(titulo, tabla);
        setContenido(root);
    }

    // ── Compras ──────────────────────────────────────────────────────

    @FXML
    public void onVerCompras() {
        resaltarBoton(btnCompras);

        VBox root = new VBox(14);
        root.setStyle("-fx-padding: 16; -fx-background-color: #f5f5f5;");

        Label titulo = new Label("Compras realizadas");
        titulo.setStyle(Estilos.LABEL_TITULO);

        TableView<Compra> tabla = new TableView<>();
        tabla.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0;");
        VBox.setVgrow(tabla, Priority.ALWAYS);

        TableColumn<Compra, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getIdCompra()));
        colId.setPrefWidth(50);

        TableColumn<Compra, String> colUser = new TableColumn<>("USUARIO");
        colUser.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getUsuario().getNombre()));
        colUser.setPrefWidth(150);

        TableColumn<Compra, String> colEvento = new TableColumn<>("EVENTO");
        colEvento.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getEvento().getNombre()));
        colEvento.setPrefWidth(180);

        TableColumn<Compra, String> colFecha = new TableColumn<>("FECHA");
        colFecha.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(
            d.getValue().getFechaCreacion().toString()));
        colFecha.setPrefWidth(100);

        TableColumn<Compra, Double> colTotal = new TableColumn<>("TOTAL $");
        colTotal.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getTotal()));
        colTotal.setPrefWidth(90);

        TableColumn<Compra, EstadoCompra> colEstado = new TableColumn<>("ESTADO");
        colEstado.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getEstado()));
        colEstado.setPrefWidth(100);
        colEstado.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(EstadoCompra estado, boolean empty) {
                super.updateItem(estado, empty);
                if (empty || estado == null) { setGraphic(null); return; }
                Label badge = new Label(estado.toString());
                badge.setStyle(badgeCompra(estado));
                setGraphic(badge);
            }
        });

        TableColumn<Compra, Void> colAcc = new TableColumn<>("ACCIONES");
        colAcc.setPrefWidth(100);
        colAcc.setCellFactory(col -> new TableCell<>() {
            final Button btnCancelar = new Button("Cancelar");
            { btnCancelar.setStyle(Estilos.BTN_SECUNDARIO); }
            @Override protected void updateItem(Void v, boolean empty) {
                super.updateItem(v, empty);
                if (empty) { setGraphic(null); return; }
                Compra c = getTableView().getItems().get(getIndex());
                if (c.getEstado() == EstadoCompra.CREADA) {
                    btnCancelar.setOnAction(e -> {
                        c.cancelar();
                        PersistenciaCompras.guardarCompras(Launcher.compras);
                        getTableView().refresh();
                    });
                    setGraphic(btnCancelar);
                } else {
                    setGraphic(null);
                }
            }
        });

        tabla.getColumns().addAll(colId, colUser, colEvento, colFecha, colTotal, colEstado, colAcc);
        tabla.setItems(FXCollections.observableArrayList(Launcher.compras));

        root.getChildren().addAll(titulo, tabla);
        setContenido(root);
    }

    private String badgeCompra(EstadoCompra estado) {
        return switch (estado) {
            case PAGADA, CONFIRMADA -> Estilos.BADGE_VERDE;
            case CREADA             -> Estilos.BADGE_AMBER;
            case CANCELADA          -> Estilos.BADGE_ROJO;
            default                 -> Estilos.BADGE_GRIS;
        };
    }

    // ── Incidencias ──────────────────────────────────────────────────

    @FXML
    public void onVerIncidencias() {
        resaltarBoton(btnIncidencias);

        VBox root = new VBox(14);
        root.setStyle("-fx-padding: 16; -fx-background-color: #f5f5f5;");
        root.setAlignment(Pos.CENTER);

        Label titulo = new Label("Incidencias");
        titulo.setStyle(Estilos.LABEL_TITULO);
        Label msg = new Label("No hay incidencias registradas.");
        msg.setStyle(Estilos.LABEL_SUB);

        root.getChildren().addAll(titulo, msg);
        setContenido(root);
    }

    // ── Metricas ─────────────────────────────────────────────────────

    @FXML
    public void onVerMetricas() {
        resaltarBoton(btnMetricas);

        long publicados = Launcher.eventos.stream()
            .filter(e -> e.getEstado() == EstadoEvento.PUBLICADO).count();
        long cancelados = Launcher.eventos.stream()
            .filter(e -> e.getEstado() == EstadoEvento.CANCELADO).count();

        ScrollPane scroll = new ScrollPane();
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: #f5f5f5; -fx-background: #f5f5f5;");

        VBox root = new VBox(16);
        root.setStyle("-fx-padding: 16; -fx-background-color: #f5f5f5;");

        Label titulo = new Label("Metricas del sistema");
        titulo.setStyle(Estilos.LABEL_TITULO);

        HBox cards = new HBox(10);
        cards.getChildren().addAll(
            crearStat("TOTAL EVENTOS",     String.valueOf(Launcher.eventos.size())),
            crearStat("PUBLICADOS",        String.valueOf(publicados)),
            crearStat("CANCELADOS",        String.valueOf(cancelados)),
            crearStat("USUARIOS",          String.valueOf(Launcher.usuarios.size())),
            crearStat("COMPRAS",           String.valueOf(Launcher.compras.size()))
        );

        root.getChildren().addAll(titulo, cards);
        scroll.setContent(root);
        setContenido(scroll);
    }

    // ── Sesion ───────────────────────────────────────────────────────

    @FXML
    public void onCerrarSesion() {
        SesionActual.getInstancia().cerrarSesion();
        launcher.showLogin();
    }
}
