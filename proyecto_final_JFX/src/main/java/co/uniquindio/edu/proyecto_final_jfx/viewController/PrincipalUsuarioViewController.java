package co.uniquindio.edu.proyecto_final_jfx.viewController;

import co.uniquindio.edu.proyecto_final_jfx.Launcher;
import co.uniquindio.edu.proyecto_final_jfx.controller.PersistenciaCompras;
import co.uniquindio.edu.proyecto_final_jfx.model.compra.Compra;
import co.uniquindio.edu.proyecto_final_jfx.model.compra.Pago;
import co.uniquindio.edu.proyecto_final_jfx.model.enums.Categoria;
import co.uniquindio.edu.proyecto_final_jfx.model.enums.EstadoCompra;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Asiento;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Evento;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Zona;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.iterator.IIteradorEventos;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.iterator.IteradorCategoria;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.iterator.IteradorCiudad;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.iterator.IteradorFecha;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.adapter.IMetodoPago;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.adapter.PSE;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.adapter.PSEAdapter;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.adapter.TarjetaAdapter;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.adapter.TarjetaCredito;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.decorator.EntradaBase;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.decorator.IEntrada;
import co.uniquindio.edu.proyecto_final_jfx.model.usuario.SesionActual;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PrincipalUsuarioViewController {

    @FXML private Label      lblBienvenida;
    @FXML private AnchorPane contenidoCentral;
    @FXML private Button     btnExplorar;
    @FXML private Button     btnComprar;
    @FXML private Button     btnMisCompras;
    @FXML private Button     btnPerfil;

    private Launcher     launcher;
    private Stage        stage;
    private List<Button> navBtns;

    private Evento                    eventoActual          = null;
    private final List<Asiento>       asientosSeleccionados = new ArrayList<>();
    private final Map<Asiento, Button> botonesAsiento       = new HashMap<>();
    private TableView<Evento>         tablaEventos;

    private VBox             vboxLineas;
    private Label            lblTotal;
    private Label            lblMsgCompra;
    private ComboBox<String> cbMetodo;

    public void setLauncher(Launcher l) { this.launcher = l; }
    public void setStage(Stage s)       { this.stage = s; }

    @FXML
    public void initialize() {
        navBtns = Arrays.asList(btnExplorar, btnComprar, btnMisCompras, btnPerfil);
        lblBienvenida.setText("Bienvenido, " +
            SesionActual.getInstancia().getUsuarioActual().getNombre());
        onExplorarEventos();
    }

    // ── Navegación ──────────────────────────────────────────────────

    private void resaltarBoton(Button activo) {
        for (Button b : navBtns) b.setStyle(Estilos.SIDEBAR_BTN);
        activo.setStyle(Estilos.SIDEBAR_BTN_ACTIVO_USER);
    }

    private void setContenido(Node nodo) {
        contenidoCentral.getChildren().setAll(nodo);
        AnchorPane.setTopAnchor(nodo, 0.0);
        AnchorPane.setLeftAnchor(nodo, 0.0);
        AnchorPane.setRightAnchor(nodo, 0.0);
        AnchorPane.setBottomAnchor(nodo, 0.0);
    }

    // ── Explorar Eventos ─────────────────────────────────────────────

    @FXML
    public void onExplorarEventos() {
        resaltarBoton(btnExplorar);

        ScrollPane scroll = new ScrollPane();
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: #f5f5f5; -fx-background: #f5f5f5;");

        VBox root = new VBox(14);
        root.setStyle("-fx-padding: 16; -fx-background-color: #f5f5f5;");

        VBox tituloBox = new VBox(2);
        Label titulo = new Label("Explorar Eventos");
        titulo.setStyle(Estilos.SECTION_TITLE);
        Label sub = new Label("Armenia, Quindío");
        sub.setStyle(Estilos.SECTION_SUB);
        tituloBox.getChildren().addAll(titulo, sub);

        TextField txtBuscar = new TextField();
        txtBuscar.setPromptText("Buscar evento...");
        txtBuscar.setStyle(Estilos.INPUT_STYLE);
        txtBuscar.setPrefWidth(200);

        ComboBox<String> cbCategoria = new ComboBox<>();
        cbCategoria.getItems().add("Todas");
        for (Categoria c : Categoria.values()) cbCategoria.getItems().add(c.toString());
        cbCategoria.setValue("Todas");
        cbCategoria.setStyle(Estilos.INPUT_STYLE);

        TextField txtCiudadFiltro = new TextField();
        txtCiudadFiltro.setPromptText("Ciudad...");
        txtCiudadFiltro.setStyle(Estilos.INPUT_STYLE);
        txtCiudadFiltro.setPrefWidth(120);

        DatePicker dpDesde = new DatePicker();
        dpDesde.setPromptText("Desde");
        dpDesde.setStyle(Estilos.INPUT_STYLE);
        dpDesde.setPrefWidth(140);

        DatePicker dpHasta = new DatePicker();
        dpHasta.setPromptText("Hasta");
        dpHasta.setStyle(Estilos.INPUT_STYLE);
        dpHasta.setPrefWidth(140);

        Button btnBuscar = new Button("Buscar");
        btnBuscar.setStyle(Estilos.BTN_PRIMARY_BLUE);

        HBox filtros = new HBox(8, txtBuscar, cbCategoria, txtCiudadFiltro, dpDesde, dpHasta, btnBuscar);
        filtros.setAlignment(Pos.CENTER_LEFT);

        tablaEventos = new TableView<>();
        tablaEventos.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0;");
        VBox.setVgrow(tablaEventos, Priority.ALWAYS);

        TableColumn<Evento, String> colNombre = new TableColumn<>("NOMBRE");
        colNombre.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getNombre()));
        colNombre.setPrefWidth(200);

        TableColumn<Evento, String> colCat = new TableColumn<>("CATEGORÍA");
        colCat.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getCategoria().toString()));
        colCat.setPrefWidth(110);

        TableColumn<Evento, String> colCiudad = new TableColumn<>("CIUDAD");
        colCiudad.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getCiudad()));
        colCiudad.setPrefWidth(90);

        TableColumn<Evento, String> colFecha = new TableColumn<>("FECHA");
        colFecha.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(
            d.getValue().getFechaHora().toLocalDate().toString()));
        colFecha.setPrefWidth(100);

        TableColumn<Evento, Integer> colDisp = new TableColumn<>("DISPONIBLE");
        colDisp.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getAforoDisponible()));
        colDisp.setPrefWidth(90);

        TableColumn<Evento, String> colEstado = new TableColumn<>("ESTADO");
        colEstado.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getEstado().toString()));
        colEstado.setPrefWidth(100);

        tablaEventos.getColumns().addAll(colNombre, colCat, colCiudad, colFecha, colDisp, colEstado);
        tablaEventos.setItems(FXCollections.observableArrayList(Launcher.eventos));

        Button btnComprarDesde = new Button("Comprar entrada");
        btnComprarDesde.setStyle(Estilos.BTN_PRIMARY_BLUE);
        btnComprarDesde.disableProperty().bind(
            tablaEventos.getSelectionModel().selectedItemProperty().isNull());
        btnComprarDesde.setOnAction(e -> {
            eventoActual = tablaEventos.getSelectionModel().getSelectedItem();
            onComprarEntradas();
        });

        btnBuscar.setOnAction(e -> {
            List<Evento> base = new ArrayList<>(Launcher.eventos);

            if (!cbCategoria.getValue().equals("Todas")) {
                Categoria catSel = Categoria.valueOf(cbCategoria.getValue());
                IIteradorEventos it = new IteradorCategoria(base, catSel);
                List<Evento> res = new ArrayList<>();
                while (it.hasNext()) res.add(it.next());
                base = res;
            }

            if (!txtCiudadFiltro.getText().isBlank()) {
                IIteradorEventos it = new IteradorCiudad(base, txtCiudadFiltro.getText().trim());
                List<Evento> res = new ArrayList<>();
                while (it.hasNext()) res.add(it.next());
                base = res;
            }

            if (dpDesde.getValue() != null && dpHasta.getValue() != null) {
                IIteradorEventos it = new IteradorFecha(base, dpDesde.getValue(), dpHasta.getValue());
                List<Evento> res = new ArrayList<>();
                while (it.hasNext()) res.add(it.next());
                base = res;
            }

            if (!txtBuscar.getText().isBlank()) {
                String term = txtBuscar.getText().trim().toLowerCase();
                List<Evento> res = new ArrayList<>();
                for (Evento ev : base)
                    if (ev.getNombre().toLowerCase().contains(term)) res.add(ev);
                base = res;
            }

            tablaEventos.setItems(FXCollections.observableArrayList(base));
        });

        root.getChildren().addAll(tituloBox, filtros, tablaEventos, btnComprarDesde);
        scroll.setContent(root);
        setContenido(scroll);
    }

    // ── Comprar Entradas ─────────────────────────────────────────────

    @FXML
    public void onComprarEntradas() {
        resaltarBoton(btnComprar);
        if (eventoActual == null) {
            mostrarSelectorEvento();
        } else {
            mostrarMapaAsientos();
        }
    }

    private void mostrarSelectorEvento() {
        VBox root = new VBox(14);
        root.setStyle("-fx-padding: 16; -fx-background-color: #f5f5f5;");
        root.setAlignment(Pos.TOP_LEFT);

        Label titulo = new Label("Comprar Entradas");
        titulo.setStyle(Estilos.SECTION_TITLE);
        Label sub = new Label("Selecciona un evento para ver el mapa de asientos");
        sub.setStyle(Estilos.SECTION_SUB);

        ComboBox<Evento> cbEvento = new ComboBox<>();
        cbEvento.getItems().addAll(Launcher.eventos);
        cbEvento.setPromptText("Elige un evento...");
        cbEvento.setStyle(Estilos.INPUT_STYLE);
        cbEvento.setPrefWidth(380);
        cbEvento.setCellFactory(lv -> new ListCell<>() {
            @Override protected void updateItem(Evento ev, boolean empty) {
                super.updateItem(ev, empty);
                setText((empty || ev == null) ? null : ev.getNombre() + " — " + ev.getCiudad());
            }
        });
        cbEvento.setButtonCell(new ListCell<>() {
            @Override protected void updateItem(Evento ev, boolean empty) {
                super.updateItem(ev, empty);
                setText((empty || ev == null) ? null : ev.getNombre() + " — " + ev.getCiudad());
            }
        });

        Button btnVer = new Button("Ver asientos");
        btnVer.setStyle(Estilos.BTN_PRIMARY_BLUE);
        btnVer.setOnAction(e -> {
            if (cbEvento.getValue() == null) return;
            eventoActual = cbEvento.getValue();
            mostrarMapaAsientos();
        });

        HBox fila = new HBox(10, cbEvento, btnVer);
        fila.setAlignment(Pos.CENTER_LEFT);
        root.getChildren().addAll(titulo, sub, fila);
        setContenido(root);
    }

    private void mostrarMapaAsientos() {
        asientosSeleccionados.clear();
        botonesAsiento.clear();

        vboxLineas   = new VBox(6);
        lblTotal     = new Label("$0");
        lblMsgCompra = new Label();
        cbMetodo     = new ComboBox<>();

        lblTotal.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #111111;");
        lblMsgCompra.setStyle("-fx-font-size: 12px; -fx-text-fill: #0F6E56;");

        cbMetodo.getItems().addAll("Tarjeta", "PSE");
        cbMetodo.setPromptText("Método de pago");
        cbMetodo.setStyle(Estilos.INPUT_STYLE);
        cbMetodo.setMaxWidth(Double.MAX_VALUE);

        Button btnConfirmar = new Button("Confirmar compra");
        btnConfirmar.setStyle(Estilos.BTN_PRIMARY_BLUE);
        btnConfirmar.setMaxWidth(Double.MAX_VALUE);
        btnConfirmar.setOnAction(e -> onConfirmarCompra());

        VBox infoEvento = new VBox(4);
        infoEvento.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 6; -fx-padding: 8 10 8 10;");
        Label lNombreEv = new Label(eventoActual.getNombre());
        lNombreEv.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: #111111;");
        Label lFechaEv = new Label(eventoActual.getFechaHora().toLocalDate() + " — " + eventoActual.getCiudad());
        lFechaEv.setStyle(Estilos.SECTION_SUB);
        infoEvento.getChildren().addAll(lNombreEv, lFechaEv);

        Label lblResumen = new Label("Resumen del pedido");
        lblResumen.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #111111;" +
            "-fx-border-color: transparent transparent #e0e0e0 transparent; -fx-padding: 0 0 8 0;");

        Label lblTotalTit = new Label("Total");
        lblTotalTit.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #111111;");
        Region spacerTotal = new Region();
        HBox.setHgrow(spacerTotal, Priority.ALWAYS);
        HBox hboxTotal = new HBox(spacerTotal, lblTotalTit, new Label("  "), lblTotal);
        hboxTotal.setAlignment(Pos.CENTER_LEFT);

        Label lblMetodoTit = new Label("Método de pago:");
        lblMetodoTit.setStyle(Estilos.LABEL_CAMPO);

        VBox panelDerecho = new VBox(10,
            lblResumen, infoEvento,
            new Separator(),
            vboxLineas,
            new Separator(),
            hboxTotal,
            lblMetodoTit, cbMetodo,
            btnConfirmar, lblMsgCompra);
        panelDerecho.setStyle(Estilos.CARD);
        panelDerecho.setPrefWidth(220);
        panelDerecho.setMaxWidth(220);

        Label lEscenario = new Label("  ESCENARIO  ");
        lEscenario.setStyle("-fx-background-color: #e0e0e0; -fx-padding: 4;" +
            "-fx-background-radius: 4; -fx-font-size: 11px; -fx-text-fill: #555555;");

        Label legDisp = crearLeyenda("#9FE1CB", "Disponible");
        Label legVip  = crearLeyenda("#FAC775", "VIP");
        Label legOcup = crearLeyenda("#e0e0e0", "Ocupado");
        Label legSel  = crearLeyenda("#0F6E56", "Seleccionado");
        HBox leyenda  = new HBox(14, legDisp, legVip, legOcup, legSel);
        leyenda.setAlignment(Pos.CENTER_LEFT);

        VBox gridContainer = new VBox(12);
        for (Zona zona : eventoActual.getRecinto().getZonas()) {
            Label lblZona = new Label(zona.getNombre() + " — $" + (int) zona.getPrecioBase() + " c/u");
            lblZona.setStyle("-fx-font-size: 11px; -fx-font-weight: bold; -fx-text-fill: #555555;");

            boolean esVip = zona.getPrecioBase() > 80000;
            final String estiloDisp = esVip
                ? "-fx-background-color: #FAC775; -fx-text-fill: #633806; " +
                  "-fx-background-radius: 4; -fx-font-size: 10px; -fx-cursor: hand;" +
                  "-fx-pref-width: 34; -fx-pref-height: 30;"
                : "-fx-background-color: #9FE1CB; -fx-text-fill: #085041; " +
                  "-fx-background-radius: 4; -fx-font-size: 10px; -fx-cursor: hand;" +
                  "-fx-pref-width: 34; -fx-pref-height: 30;";
            final String estiloOcup =
                "-fx-background-color: #e0e0e0; -fx-text-fill: #888888; " +
                "-fx-background-radius: 4; -fx-font-size: 10px;" +
                "-fx-pref-width: 34; -fx-pref-height: 30;";
            final String estiloSel =
                "-fx-background-color: #0F6E56; -fx-text-fill: #ffffff; -fx-font-weight: bold; " +
                "-fx-background-radius: 4; -fx-font-size: 10px; -fx-cursor: hand;" +
                "-fx-pref-width: 34; -fx-pref-height: 30;";

            Map<String, Integer> filaMap = new LinkedHashMap<>();
            int rowIdx = 0;
            for (Asiento a : zona.getAsientos())
                if (!filaMap.containsKey(a.getFila())) filaMap.put(a.getFila(), rowIdx++);

            GridPane gridZona = new GridPane();
            gridZona.setHgap(4); gridZona.setVgap(4);

            for (Asiento a : zona.getAsientos()) {
                Button btn = new Button(a.getFila() + a.getNumero());
                btn.setPrefWidth(34); btn.setPrefHeight(30);
                int row = filaMap.get(a.getFila());
                int col = a.getNumero() - 1;

                if (!a.isDisponible()) {
                    btn.setStyle(estiloOcup);
                    btn.setDisable(true);
                } else {
                    btn.setStyle(estiloDisp);
                    btn.setOnAction(ev -> {
                        if (asientosSeleccionados.contains(a)) {
                            asientosSeleccionados.remove(a);
                            btn.setStyle(estiloDisp);
                        } else {
                            asientosSeleccionados.add(a);
                            btn.setStyle(estiloSel);
                        }
                        actualizarResumen();
                    });
                }
                botonesAsiento.put(a, btn);
                gridZona.add(btn, col, row);
            }
            gridContainer.getChildren().addAll(lblZona, gridZona);
        }

        ScrollPane scrollGrid = new ScrollPane(gridContainer);
        scrollGrid.setFitToWidth(true);
        scrollGrid.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        VBox panelIzq = new VBox(10, lEscenario, leyenda, scrollGrid);
        panelIzq.setStyle(Estilos.CARD);
        panelIzq.setPadding(new Insets(12));

        SplitPane split = new SplitPane(panelIzq, panelDerecho);
        split.setOrientation(Orientation.HORIZONTAL);
        split.setDividerPositions(0.68);
        split.setStyle("-fx-background-color: #f5f5f5; -fx-padding: 12;");

        setContenido(split);
    }

    private Label crearLeyenda(String color, String texto) {
        Label l = new Label("● " + texto);
        l.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 11px;" +
            " -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 1, 0, 0, 0);");
        return l;
    }

    private void actualizarResumen() {
        if (vboxLineas == null || lblTotal == null) return;
        vboxLineas.getChildren().clear();
        double total = 0;
        for (Asiento a : asientosSeleccionados) {
            Label desc = new Label("Fila " + a.getFila() + " N°" + a.getNumero()
                + " (" + a.getZona().getNombre() + ")");
            desc.setStyle(Estilos.LABEL_CAMPO);
            desc.setWrapText(true);
            Label precio = new Label("$" + (int) a.getZona().getPrecioBase());
            precio.setStyle(Estilos.LABEL_CAMPO);
            Region sp = new Region();
            HBox.setHgrow(sp, Priority.ALWAYS);
            HBox linea = new HBox(4, desc, sp, precio);
            linea.setAlignment(Pos.CENTER_LEFT);
            vboxLineas.getChildren().add(linea);
            total += a.getZona().getPrecioBase();
        }
        lblTotal.setText("$" + (int) total);
    }

    private void onConfirmarCompra() {
        if (asientosSeleccionados.isEmpty()) {
            lblMsgCompra.setText("Selecciona al menos un asiento.");
            lblMsgCompra.setStyle("-fx-text-fill: #A32D2D; -fx-font-size: 12px;");
            return;
        }
        if (cbMetodo.getValue() == null) {
            lblMsgCompra.setText("Selecciona un método de pago.");
            lblMsgCompra.setStyle("-fx-text-fill: #A32D2D; -fx-font-size: 12px;");
            return;
        }

        double total = asientosSeleccionados.stream()
            .mapToDouble(a -> a.getZona().getPrecioBase()).sum();

        IMetodoPago metodo = "PSE".equals(cbMetodo.getValue())
            ? new PSEAdapter(new PSE())
            : new TarjetaAdapter(new TarjetaCredito());

        Compra.Builder builder = new Compra.Builder()
            .conUsuario(SesionActual.getInstancia().getUsuarioActual())
            .conEvento(eventoActual)
            .conPago(new Pago(total, metodo));

        for (Asiento a : new ArrayList<>(asientosSeleccionados)) {
            a.ocupar();
            IEntrada entrada = new EntradaBase(
                "Fila " + a.getFila() + " N°" + a.getNumero(),
                a.getZona().getPrecioBase(),
                a);
            builder.conEntrada(entrada);
            Button b = botonesAsiento.get(a);
            if (b != null) {
                b.setStyle("-fx-background-color: #e0e0e0; -fx-text-fill: #888888;" +
                    "-fx-background-radius: 4; -fx-font-size: 10px;" +
                    "-fx-pref-width: 34; -fx-pref-height: 30;");
                b.setDisable(true);
                b.setOnAction(null);
            }
        }

        Compra compra = builder.build();
        Launcher.compras.add(compra);
        PersistenciaCompras.guardarCompras(Launcher.compras);

        asientosSeleccionados.clear();
        actualizarResumen();
        lblMsgCompra.setText("Compra confirmada. ID: " + compra.getIdCompra());
        lblMsgCompra.setStyle("-fx-text-fill: #0F6E56; -fx-font-size: 12px;");
    }

    // ── Mis Compras ──────────────────────────────────────────────────

    @FXML
    public void onMisCompras() {
        resaltarBoton(btnMisCompras);

        VBox root = new VBox(14);
        root.setStyle("-fx-padding: 16; -fx-background-color: #f5f5f5;");

        Label titulo = new Label("Mis Compras");
        titulo.setStyle(Estilos.SECTION_TITLE);

        List<Compra> misCompras = new ArrayList<>();
        for (Compra c : Launcher.compras)
            if (c.getUsuario() == SesionActual.getInstancia().getUsuarioActual())
                misCompras.add(c);

        if (misCompras.isEmpty()) {
            Label msg = new Label("Aún no has realizado ninguna compra.");
            msg.setStyle(Estilos.SECTION_SUB);
            root.getChildren().addAll(titulo, msg);
            setContenido(root);
            return;
        }

        TableView<Compra> tabla = new TableView<>();
        tabla.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0;");
        VBox.setVgrow(tabla, Priority.ALWAYS);

        TableColumn<Compra, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getIdCompra()));
        colId.setPrefWidth(50);

        TableColumn<Compra, String> colEvento = new TableColumn<>("EVENTO");
        colEvento.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getEvento().getNombre()));
        colEvento.setPrefWidth(200);

        TableColumn<Compra, String> colFecha = new TableColumn<>("FECHA");
        colFecha.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(
            d.getValue().getFechaCreacion().toString()));
        colFecha.setPrefWidth(100);

        TableColumn<Compra, Integer> colEntradas = new TableColumn<>("ENTRADAS");
        colEntradas.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getEntradas().size()));
        colEntradas.setPrefWidth(80);

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
        colAcc.setPrefWidth(90);
        colAcc.setCellFactory(col -> new TableCell<>() {
            final Button btnCancelar = new Button("Cancelar");
            { btnCancelar.setStyle(Estilos.BTN_SECONDARY); }
            @Override protected void updateItem(Void v, boolean empty) {
                super.updateItem(v, empty);
                if (empty) { setGraphic(null); return; }
                Compra c = getTableView().getItems().get(getIndex());
                if (c.getEstado() == EstadoCompra.CREADA) {
                    btnCancelar.setOnAction(e -> {
                        if (c.cancelar()) {
                            for (IEntrada ie : c.getEntradas()) {
                                if (ie instanceof EntradaBase eb && eb.getAsiento() != null)
                                    eb.getAsiento().liberar();
                            }
                            for (int i = 0; i < c.getEntradas().size(); i++)
                                c.getEvento().liberarLugar();
                            PersistenciaCompras.guardarCompras(Launcher.compras);
                            getTableView().refresh();
                        }
                    });
                    setGraphic(btnCancelar);
                } else {
                    setGraphic(null);
                }
            }
        });

        tabla.getColumns().addAll(colId, colEvento, colFecha, colEntradas, colTotal, colEstado, colAcc);
        tabla.setItems(FXCollections.observableArrayList(misCompras));

        root.getChildren().addAll(titulo, tabla);
        setContenido(root);
    }

    private String badgeCompra(EstadoCompra estado) {
        return switch (estado) {
            case PAGADA, CONFIRMADA -> Estilos.BADGE_GREEN;
            case CREADA             -> Estilos.BADGE_AMBER;
            case CANCELADA          -> Estilos.BADGE_RED;
            default                 -> Estilos.BADGE_GRAY;
        };
    }

    // ── Mi Perfil ────────────────────────────────────────────────────

    @FXML
    public void onMiPerfil() {
        resaltarBoton(btnPerfil);

        VBox root = new VBox(16);
        root.setStyle("-fx-padding: 16; -fx-background-color: #f5f5f5;");

        Label titulo = new Label("Mi Perfil");
        titulo.setStyle(Estilos.SECTION_TITLE);

        var u = SesionActual.getInstancia().getUsuarioActual();

        VBox card = new VBox(12);
        card.setStyle(Estilos.CARD);
        card.setMaxWidth(480);
        card.getChildren().addAll(
            dato("Nombre",     u.getNombre()),
            dato("Correo",     u.getCorreo()),
            dato("Rol",        u.getRol().toString()),
            dato("ID usuario", String.valueOf(u.getIdUsuario()))
        );

        root.getChildren().addAll(titulo, card);
        setContenido(root);
    }

    private HBox dato(String etiqueta, String valor) {
        Label lbl = new Label(etiqueta + ":");
        lbl.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #555555; -fx-min-width: 100;");
        Label val = new Label(valor);
        val.setStyle("-fx-font-size: 13px; -fx-text-fill: #111111;");
        HBox hb = new HBox(10, lbl, val);
        hb.setAlignment(Pos.CENTER_LEFT);
        return hb;
    }

    // ── Cerrar Sesión ────────────────────────────────────────────────

    @FXML
    public void onCerrarSesion() {
        eventoActual = null;
        asientosSeleccionados.clear();
        botonesAsiento.clear();
        SesionActual.getInstancia().cerrarSesion();
        launcher.showLogin();
    }
}
