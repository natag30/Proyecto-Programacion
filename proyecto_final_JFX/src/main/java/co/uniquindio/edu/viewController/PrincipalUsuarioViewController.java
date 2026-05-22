package co.uniquindio.edu.viewController;

import co.uniquindio.edu.controller.CompraController;
import co.uniquindio.edu.controller.EventoController;
import co.uniquindio.edu.controller.UsuarioController;
import co.uniquindio.edu.proyecto_final_jfx.Launcher;
import co.uniquindio.edu.proyecto_final_jfx.model.compra.Compra;
import co.uniquindio.edu.proyecto_final_jfx.model.enums.Categoria;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Asiento;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Evento;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Zona;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.adapter.IMetodoPago;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.adapter.PSE;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.adapter.PSEAdapter;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.adapter.TarjetaAdapter;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.adapter.TarjetaCredito;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.decorator.AccesoVIPDecorator;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.decorator.EntradaBase;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.decorator.IEntrada;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.decorator.MerchandisingDecorator;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.decorator.SeguroCancelacionDecorator;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.facade.ServicioAsiento;
import co.uniquindio.edu.proyecto_final_jfx.model.usuario.Usuario;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrincipalUsuarioViewController {

    @FXML private Label lblBienvenida;
    @FXML private AnchorPane contenidoCentral;

    private Launcher launcher;
    private final EventoController eventoController = new EventoController();
    private final CompraController compraController = new CompraController();
    private final UsuarioController usuarioController = new UsuarioController();

    public void setLauncher(Launcher launcher) {
        this.launcher = launcher;
    }

    public void inicializar() {
        if (usuarioController.estaAutenticado()) {
            lblBienvenida.setText("Bienvenido, " + usuarioController.getUsuarioActual().getNombre());
        }
        onExplorarEventos();
    }

    @FXML
    private void onCerrarSesion() {
        usuarioController.cerrarSesion();
        launcher.showLogin();
    }

    @FXML
    private void onExplorarEventos() {
        VBox contenido = new VBox(10);
        contenido.setPadding(new Insets(10));

        Label titulo = new Label("Explorar Eventos");
        titulo.setStyle("-fx-font-size: 17px; -fx-font-weight: bold;");

        HBox filtros = new HBox(10);
        ComboBox<String> cbFiltro = new ComboBox<>();
        cbFiltro.getItems().addAll("Todos", "Por Categoria", "Por Ciudad", "Por Fecha");
        cbFiltro.setValue("Todos");

        ComboBox<Categoria> cbCategoria = new ComboBox<>();
        cbCategoria.getItems().addAll(Categoria.values());
        cbCategoria.setPromptText("Categoria");
        cbCategoria.setVisible(false);

        TextField tfCiudad = new TextField();
        tfCiudad.setPromptText("Ciudad");
        tfCiudad.setVisible(false);

        DatePicker dpDesde = new DatePicker();
        dpDesde.setPromptText("Desde");
        dpDesde.setPrefWidth(130);
        dpDesde.setVisible(false);
        dpDesde.setManaged(false);

        DatePicker dpHasta = new DatePicker();
        dpHasta.setPromptText("Hasta");
        dpHasta.setPrefWidth(130);
        dpHasta.setVisible(false);
        dpHasta.setManaged(false);

        cbFiltro.setOnAction(e -> {
            String val = cbFiltro.getValue();
            cbCategoria.setVisible("Por Categoria".equals(val));
            tfCiudad.setVisible("Por Ciudad".equals(val));
            boolean porFecha = "Por Fecha".equals(val);
            dpDesde.setVisible(porFecha);
            dpDesde.setManaged(porFecha);
            dpHasta.setVisible(porFecha);
            dpHasta.setManaged(porFecha);
        });

        filtros.getChildren().addAll(cbFiltro, cbCategoria, tfCiudad, dpDesde, dpHasta);

        TableView<Evento> tabla = new TableView<>();

        TableColumn<Evento, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getIdEvento()));
        colId.setPrefWidth(40);

        TableColumn<Evento, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getNombre()));
        colNombre.setPrefWidth(190);

        TableColumn<Evento, String> colCat = new TableColumn<>("Categoria");
        colCat.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getCategoria().name()));
        colCat.setPrefWidth(100);

        TableColumn<Evento, String> colCiudad = new TableColumn<>("Ciudad");
        colCiudad.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getCiudad()));
        colCiudad.setPrefWidth(90);

        TableColumn<Evento, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getEstado().name()));
        colEstado.setPrefWidth(90);

        TableColumn<Evento, Integer> colDisp = new TableColumn<>("Disponible");
        colDisp.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getAforoDisponible()));
        colDisp.setPrefWidth(70);

        tabla.getColumns().addAll(colId, colNombre, colCat, colCiudad, colEstado, colDisp);
        tabla.setPrefHeight(260);

        Button btnBuscar = crearBoton("Buscar", "#2980b9");
        btnBuscar.setOnAction(e -> {
            List<Evento> resultado;
            String filtro = cbFiltro.getValue();
            if ("Por Categoria".equals(filtro) && cbCategoria.getValue() != null) {
                resultado = eventoController.buscarPorCategoria(cbCategoria.getValue());
            } else if ("Por Ciudad".equals(filtro) && !tfCiudad.getText().isBlank()) {
                resultado = eventoController.buscarPorCiudad(tfCiudad.getText().trim());
            } else if ("Por Fecha".equals(filtro)
                    && dpDesde.getValue() != null && dpHasta.getValue() != null) {
                resultado = eventoController.buscarPorFecha(dpDesde.getValue(), dpHasta.getValue());
            } else {
                resultado = eventoController.getEventos();
            }
            tabla.setItems(FXCollections.observableArrayList(resultado));
        });

        tabla.setItems(FXCollections.observableArrayList(eventoController.getEventos()));

        contenido.getChildren().addAll(titulo, filtros, btnBuscar, tabla);
        setContenido(contenido);
    }

    @FXML
    private void onComprarEntrada() {
        VBox contenido = new VBox(10);
        contenido.setPadding(new Insets(10));

        Label titulo = new Label("Comprar Entrada");
        titulo.setStyle("-fx-font-size: 17px; -fx-font-weight: bold;");

        // Estilos reutilizables para el mapa de asientos
        final String ESTILO_DISPONIBLE   = "-fx-background-color: #bbf7d0; -fx-text-fill: #14532d; -fx-background-radius: 6; -fx-font-size: 11px; -fx-cursor: hand;";
        final String ESTILO_SELECCIONADO = "-fx-background-color: #2563eb; -fx-text-fill: white; -fx-background-radius: 6; -fx-font-size: 11px; -fx-font-weight: bold; -fx-cursor: hand;";
        final String ESTILO_OCUPADO      = "-fx-background-color: #fca5a5; -fx-text-fill: #7f1d1d; -fx-background-radius: 6; -fx-font-size: 11px;";

        // ComboBox de evento
        ComboBox<Evento> cbEvento = new ComboBox<>();
        cbEvento.getItems().addAll(eventoController.getEventos());
        cbEvento.setPromptText("Selecciona un evento");
        cbEvento.setPrefWidth(320);
        cbEvento.setCellFactory(lv -> new ListCell<>() {
            @Override protected void updateItem(Evento ev, boolean empty) {
                super.updateItem(ev, empty);
                setText((empty || ev == null) ? null : ev.getNombre() + " - " + ev.getCiudad());
            }
        });
        cbEvento.setButtonCell(new ListCell<>() {
            @Override protected void updateItem(Evento ev, boolean empty) {
                super.updateItem(ev, empty);
                setText((empty || ev == null) ? null : ev.getNombre() + " - " + ev.getCiudad());
            }
        });

        // ComboBox de zona
        ComboBox<Zona> cbZona = new ComboBox<>();
        cbZona.setPromptText("Selecciona una zona");
        cbZona.setPrefWidth(280);
        cbZona.setCellFactory(lv -> new ListCell<>() {
            @Override protected void updateItem(Zona z, boolean empty) {
                super.updateItem(z, empty);
                setText((empty || z == null) ? null : z.getNombre() + " - $" + z.getPrecioBase());
            }
        });
        cbZona.setButtonCell(new ListCell<>() {
            @Override protected void updateItem(Zona z, boolean empty) {
                super.updateItem(z, empty);
                setText((empty || z == null) ? null : z.getNombre() + " - $" + z.getPrecioBase());
            }
        });

        // Estado de selección múltiple de asientos
        final List<Asiento>          asientosSeleccionados = new ArrayList<>();
        final Map<Asiento, Button>   botonesSeleccionados  = new HashMap<>();

        Label lblAsientoInfo = new Label();
        lblAsientoInfo.setStyle("-fx-font-size: 11px; -fx-text-fill: #1d4ed8; -fx-font-weight: bold;");

        // Mapa de asientos (inicialmente oculto)
        Label lblMapaTitulo = new Label("Mapa de asientos:");
        lblMapaTitulo.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #374151;");
        lblMapaTitulo.setVisible(false);
        lblMapaTitulo.setManaged(false);

        HBox leyenda = new HBox(16);
        Label legVerde = new Label("●  Disponible");
        legVerde.setStyle("-fx-text-fill: #16a34a; -fx-font-size: 11px;");
        Label legRojo = new Label("●  Ocupado");
        legRojo.setStyle("-fx-text-fill: #dc2626; -fx-font-size: 11px;");
        Label legAzul = new Label("●  Seleccionado");
        legAzul.setStyle("-fx-text-fill: #2563eb; -fx-font-size: 11px;");
        leyenda.getChildren().addAll(legVerde, legRojo, legAzul);
        leyenda.setVisible(false);
        leyenda.setManaged(false);

        FlowPane mapaAsientos = new FlowPane();
        mapaAsientos.setHgap(6);
        mapaAsientos.setVgap(6);
        mapaAsientos.setPrefWrapLength(480);
        mapaAsientos.setVisible(false);
        mapaAsientos.setManaged(false);

        // Al cambiar evento: limpiar zona, selección y mapa
        cbEvento.setOnAction(e -> {
            Evento ev = cbEvento.getValue();
            cbZona.getItems().clear();
            if (ev != null) cbZona.getItems().addAll(ev.getRecinto().getZonas());
            mapaAsientos.getChildren().clear();
            asientosSeleccionados.clear();
            botonesSeleccionados.clear();
            lblAsientoInfo.setText("");
            lblMapaTitulo.setVisible(false);
            lblMapaTitulo.setManaged(false);
            leyenda.setVisible(false);
            leyenda.setManaged(false);
            mapaAsientos.setVisible(false);
            mapaAsientos.setManaged(false);
        });

        // Servicios adicionales
        CheckBox chkVip    = new CheckBox("Acceso VIP (+$50.000)");
        CheckBox chkSeguro = new CheckBox("Seguro de cancelacion (+$15.000)");
        CheckBox chkMerch  = new CheckBox("Merchandising (+$20.000)");

        ComboBox<String> cbPago = new ComboBox<>();
        cbPago.getItems().addAll("PSE", "Tarjeta de Credito");
        cbPago.setPromptText("Metodo de pago");
        cbPago.setPrefWidth(280);

        Label lblTotal = new Label("Total: $0");
        lblTotal.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");
        Label lblMsg = new Label();

        // Spinner de cantidad (se define antes de actualizarTotal para poder capturarlo)
        Spinner<Integer> spinnerCantidad = new Spinner<>(1, 10, 1);
        spinnerCantidad.setPrefWidth(80);
        spinnerCantidad.setEditable(true);

        HBox cantidadBox = new HBox(8);
        cantidadBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        Label lblCantidad = new Label("Cantidad de asientos:");
        lblCantidad.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #4a5568;");
        cantidadBox.getChildren().addAll(lblCantidad, spinnerCantidad);

        Label lblCantidadInfo = new Label("Selecciona hasta 1 asiento en el mapa.");
        lblCantidadInfo.setStyle("-fx-font-size: 11px; -fx-text-fill: #64748b;");

        // Runnable para actualizar el total (usa spinnerCantidad, por eso se define aquí)
        Runnable actualizarTotal = () -> {
            Zona z = cbZona.getValue();
            if (z == null) { lblTotal.setText("Total: $0"); return; }
            int    cantidad       = spinnerCantidad.getValue();
            double precioUnitario = z.getPrecioBase();
            if (chkVip.isSelected())    precioUnitario += 50000;
            if (chkSeguro.isSelected()) precioUnitario += 15000;
            if (chkMerch.isSelected())  precioUnitario += 20000;
            double total = precioUnitario * cantidad;
            lblTotal.setText("Total: $" + (int) total
                    + "  (" + cantidad + " x $" + (int) precioUnitario + ")");
        };

        // Listener del spinner: ajustar info, deseleccionar excedente y recalcular total
        spinnerCantidad.valueProperty().addListener((obs, oldVal, newVal) -> {
            lblCantidadInfo.setText("Selecciona hasta " + newVal + " asiento(s) en el mapa.");
            if (asientosSeleccionados.size() > newVal) {
                for (Asiento a : asientosSeleccionados) {
                    Button b = botonesSeleccionados.get(a);
                    if (b != null) b.setStyle(ESTILO_DISPONIBLE);
                }
                asientosSeleccionados.clear();
                botonesSeleccionados.clear();
                lblAsientoInfo.setText("");
            }
            actualizarTotal.run();
        });

        // Al cambiar zona: recalcular total, limpiar selección y construir mapa
        cbZona.setOnAction(e -> {
            actualizarTotal.run();
            mapaAsientos.getChildren().clear();
            asientosSeleccionados.clear();
            botonesSeleccionados.clear();
            lblAsientoInfo.setText("");

            Zona z = cbZona.getValue();
            if (z == null) return;

            List<Asiento> listaAsientos = z.getAsientos();
            if (listaAsientos.isEmpty()) {
                mapaAsientos.getChildren().add(new Label("Esta zona no tiene asientos asignados."));
                lblMapaTitulo.setVisible(true);
                lblMapaTitulo.setManaged(true);
                leyenda.setVisible(false);
                leyenda.setManaged(false);
                mapaAsientos.setVisible(true);
                mapaAsientos.setManaged(true);
                return;
            }

            for (Asiento a : listaAsientos) {
                Button btnAsiento = new Button(a.getFila() + a.getNumero());
                btnAsiento.setPrefWidth(52);
                btnAsiento.setPrefHeight(36);

                if (!a.isDisponible()) {
                    btnAsiento.setStyle(ESTILO_OCUPADO);
                    btnAsiento.setDisable(true);
                } else {
                    btnAsiento.setStyle(ESTILO_DISPONIBLE);
                    btnAsiento.setOnAction(ev -> {
                        if (asientosSeleccionados.contains(a)) {
                            // Toggle off: deseleccionar y volver a verde
                            asientosSeleccionados.remove(a);
                            botonesSeleccionados.remove(a);
                            btnAsiento.setStyle(ESTILO_DISPONIBLE);
                        } else {
                            // Toggle on: agregar si no se alcanzó el límite
                            if (asientosSeleccionados.size() < spinnerCantidad.getValue()) {
                                asientosSeleccionados.add(a);
                                botonesSeleccionados.put(a, btnAsiento);
                                btnAsiento.setStyle(ESTILO_SELECCIONADO);
                            } else {
                                lblAsientoInfo.setText("Ya seleccionaste " + spinnerCantidad.getValue()
                                        + " asiento(s). Deselecciona uno para cambiar.");
                                return;
                            }
                        }
                        if (asientosSeleccionados.isEmpty()) {
                            lblAsientoInfo.setText("");
                        } else {
                            StringBuilder sb = new StringBuilder("Seleccionados: ");
                            for (Asiento sel : asientosSeleccionados) {
                                sb.append(sel.getFila()).append(sel.getNumero()).append("  ");
                            }
                            lblAsientoInfo.setText(sb.toString().trim());
                        }
                    });
                }
                mapaAsientos.getChildren().add(btnAsiento);
            }

            lblMapaTitulo.setVisible(true);
            lblMapaTitulo.setManaged(true);
            leyenda.setVisible(true);
            leyenda.setManaged(true);
            mapaAsientos.setVisible(true);
            mapaAsientos.setManaged(true);
        });

        chkVip.setOnAction(e -> actualizarTotal.run());
        chkSeguro.setOnAction(e -> actualizarTotal.run());
        chkMerch.setOnAction(e -> actualizarTotal.run());

        Button btnComprar = crearBoton("Confirmar Compra", "#27ae60");
        btnComprar.setOnAction(e -> {
            Evento evSel      = cbEvento.getValue();
            Zona   zonaSel    = cbZona.getValue();
            String metodoPago = cbPago.getValue();
            int    cantidad   = spinnerCantidad.getValue();

            if (evSel == null || zonaSel == null || metodoPago == null) {
                estilo(lblMsg, "Selecciona evento, zona y metodo de pago.", false);
                return;
            }
            if (!zonaSel.getAsientos().isEmpty() && asientosSeleccionados.isEmpty()) {
                estilo(lblMsg, "Selecciona al menos un asiento en el mapa.", false);
                return;
            }
            if (!zonaSel.getAsientos().isEmpty() && asientosSeleccionados.size() < cantidad) {
                estilo(lblMsg, "Selecciona exactamente " + cantidad + " asiento(s) en el mapa.", false);
                return;
            }
            if (!evSel.hayDisponibilidad()) {
                estilo(lblMsg, "No hay disponibilidad en este evento.", false);
                return;
            }

            Usuario usuario = usuarioController.getUsuarioActual();

            // Reservar todos los asientos de una vez con el Facade
            if (!asientosSeleccionados.isEmpty()) {
                ServicioAsiento servicioAsiento = new ServicioAsiento();
                boolean reservado = servicioAsiento.reservarAsientos(new ArrayList<>(asientosSeleccionados));
                if (!reservado) {
                    estilo(lblMsg, "Uno o mas asientos ya no estan disponibles.", false);
                    for (Asiento a : asientosSeleccionados) {
                        Button b = botonesSeleccionados.get(a);
                        if (b != null) {
                            b.setStyle(a.isDisponible() ? ESTILO_DISPONIBLE : ESTILO_OCUPADO);
                            if (!a.isDisponible()) b.setDisable(true);
                        }
                    }
                    asientosSeleccionados.clear();
                    botonesSeleccionados.clear();
                    lblAsientoInfo.setText("");
                    return;
                }
            }

            IMetodoPago metodo = "PSE".equals(metodoPago)
                    ? new PSEAdapter(new PSE())
                    : new TarjetaAdapter(new TarjetaCredito());

            int    comprasExitosas = 0;
            int    ultimoId        = 0;
            double totalAcumulado  = 0;

            List<Asiento> listaParaIterar = asientosSeleccionados.isEmpty()
                    ? new ArrayList<>() : new ArrayList<>(asientosSeleccionados);

            if (listaParaIterar.isEmpty()) {
                // Zona sin mapa: crear 'cantidad' compras genéricas
                for (int i = 0; i < cantidad; i++) {
                    IEntrada entrada = new EntradaBase("Entrada " + zonaSel.getNombre(), zonaSel.getPrecioBase());
                    if (chkVip.isSelected())    entrada = new AccesoVIPDecorator(entrada);
                    if (chkSeguro.isSelected()) entrada = new SeguroCancelacionDecorator(entrada);
                    if (chkMerch.isSelected())  entrada = new MerchandisingDecorator(entrada);
                    Compra compra = compraController.realizarCompra(usuario, evSel, entrada, metodo);
                    if (compra != null) {
                        comprasExitosas++;
                        ultimoId       = compra.getIdCompra();
                        totalAcumulado += compra.getTotal();
                    }
                }
            } else {
                // Zona con mapa: una compra por asiento seleccionado
                for (Asiento a : listaParaIterar) {
                    IEntrada entrada = new EntradaBase(
                            "Entrada " + zonaSel.getNombre() + " " + a.getFila() + a.getNumero(),
                            zonaSel.getPrecioBase());
                    if (chkVip.isSelected())    entrada = new AccesoVIPDecorator(entrada);
                    if (chkSeguro.isSelected()) entrada = new SeguroCancelacionDecorator(entrada);
                    if (chkMerch.isSelected())  entrada = new MerchandisingDecorator(entrada);
                    Compra compra = compraController.realizarCompra(usuario, evSel, entrada, metodo);
                    if (compra != null) {
                        comprasExitosas++;
                        ultimoId       = compra.getIdCompra();
                        totalAcumulado += compra.getTotal();
                        Button b = botonesSeleccionados.get(a);
                        if (b != null) {
                            b.setStyle(ESTILO_OCUPADO);
                            b.setDisable(true);
                        }
                    }
                }
            }

            asientosSeleccionados.clear();
            botonesSeleccionados.clear();
            lblAsientoInfo.setText("");

            if (comprasExitosas == 0) {
                estilo(lblMsg, "El pago no pudo procesarse.", false);
            } else {
                estilo(lblMsg, comprasExitosas + " entrada(s) compradas."
                        + "  Ultimo ID: " + ultimoId
                        + "  Total: $" + (int) totalAcumulado, true);
            }
        });

        contenido.getChildren().addAll(titulo,
                new Label("Evento:"), cbEvento,
                new Label("Zona:"), cbZona,
                cantidadBox, lblCantidadInfo,
                lblMapaTitulo, leyenda, mapaAsientos, lblAsientoInfo,
                chkVip, chkSeguro, chkMerch,
                new Label("Metodo de pago:"), cbPago,
                lblTotal, btnComprar, lblMsg);
        setContenido(contenido);
    }

    @FXML
    private void onMisCompras() {
        VBox contenido = new VBox(10);
        contenido.setPadding(new Insets(10));

        Label titulo = new Label("Mis Compras");
        titulo.setStyle("-fx-font-size: 17px; -fx-font-weight: bold;");

        Usuario actual = usuarioController.getUsuarioActual();
        List<Compra> misCompras = compraController.getComprasPorUsuario(actual);

        if (misCompras.isEmpty()) {
            contenido.getChildren().addAll(titulo,
                    new Label("Aun no has realizado ninguna compra."));
            setContenido(contenido);
            return;
        }

        TableView<Compra> tabla = new TableView<>();

        TableColumn<Compra, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getIdCompra()));
        colId.setPrefWidth(50);

        TableColumn<Compra, String> colEvento = new TableColumn<>("Evento");
        colEvento.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getEvento().getNombre()));
        colEvento.setPrefWidth(200);

        TableColumn<Compra, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getEstado().name()));
        colEstado.setPrefWidth(100);

        TableColumn<Compra, Double> colTotal = new TableColumn<>("Total ($)");
        colTotal.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getTotal()));
        colTotal.setPrefWidth(100);

        TableColumn<Compra, String> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(
                d.getValue().getFechaCreacion().toString()));
        colFecha.setPrefWidth(120);

        tabla.getColumns().addAll(colId, colEvento, colEstado, colTotal, colFecha);
        tabla.setItems(FXCollections.observableArrayList(misCompras));
        tabla.setPrefHeight(350);

        contenido.getChildren().addAll(titulo, tabla);
        setContenido(contenido);
    }

    @FXML
    private void onMiPerfil() {
        VBox contenido = new VBox(12);
        contenido.setPadding(new Insets(20));

        Label titulo = new Label("Mi Perfil");
        titulo.setStyle("-fx-font-size: 17px; -fx-font-weight: bold;");

        Usuario u = usuarioController.getUsuarioActual();

        contenido.getChildren().addAll(
                titulo,
                dato("Nombre",   u.getNombre()),
                dato("Correo",   u.getCorreo()),
                dato("Telefono", String.valueOf(u.getTelefono())),
                dato("Rol",      u.getRol().name()),
                dato("ID",       String.valueOf(u.getIdUsuario()))
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

    private void estilo(Label lbl, String msg, boolean ok) {
        lbl.setText(msg);
        lbl.setStyle("-fx-text-fill: " + (ok ? "#27ae60" : "#e74c3c") + ";");
    }

    private HBox dato(String etiqueta, String valor) {
        Label lbl = new Label(etiqueta + ":  ");
        lbl.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");
        Label val = new Label(valor);
        val.setStyle("-fx-font-size: 13px;");
        HBox hb = new HBox(lbl, val);
        hb.setPadding(new Insets(3, 0, 3, 0));
        return hb;
    }
}