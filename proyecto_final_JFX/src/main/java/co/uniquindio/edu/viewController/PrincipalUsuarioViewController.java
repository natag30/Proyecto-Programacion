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
import java.util.List;

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

        // Estilos para botones del mapa de asientos
        final String estiloDisponible   = "-fx-background-color: #bbf7d0; -fx-text-fill: #14532d; -fx-background-radius: 6; -fx-font-size: 11px; -fx-cursor: hand;";
        final String estiloSeleccionado = "-fx-background-color: #2563eb; -fx-text-fill: white;   -fx-background-radius: 6; -fx-font-size: 11px; -fx-font-weight: bold; -fx-cursor: hand;";
        final String estiloOcupado      = "-fx-background-color: #fca5a5; -fx-text-fill: #7f1d1d; -fx-background-radius: 6; -fx-font-size: 11px;";

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

        // Estado de selección de asiento
        final Asiento[] asientoSeleccionado = {null};
        final Button[]  botonSeleccionado   = {null};
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

        // Al cambiar evento: limpiar zona, mapa y selección
        cbEvento.setOnAction(e -> {
            Evento ev = cbEvento.getValue();
            cbZona.getItems().clear();
            if (ev != null) cbZona.getItems().addAll(ev.getRecinto().getZonas());
            mapaAsientos.getChildren().clear();
            asientoSeleccionado[0] = null;
            botonSeleccionado[0]   = null;
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

        Runnable actualizarTotal = () -> {
            Zona z = cbZona.getValue();
            if (z == null) { lblTotal.setText("Total: $0"); return; }
            double t = z.getPrecioBase();
            if (chkVip.isSelected())    t += 50000;
            if (chkSeguro.isSelected()) t += 15000;
            if (chkMerch.isSelected())  t += 20000;
            lblTotal.setText("Total: $" + t);
        };

        // Al cambiar zona: actualizar total y construir mapa de asientos
        cbZona.setOnAction(e -> {
            actualizarTotal.run();
            mapaAsientos.getChildren().clear();
            asientoSeleccionado[0] = null;
            botonSeleccionado[0]   = null;
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
                    btnAsiento.setStyle(estiloOcupado);
                    btnAsiento.setDisable(true);
                } else {
                    btnAsiento.setStyle(estiloDisponible);
                    btnAsiento.setOnAction(ev -> {
                        if (botonSeleccionado[0] != null) {
                            botonSeleccionado[0].setStyle(estiloDisponible);
                        }
                        asientoSeleccionado[0] = a;
                        botonSeleccionado[0]   = btnAsiento;
                        btnAsiento.setStyle(estiloSeleccionado);
                        lblAsientoInfo.setText("Asiento seleccionado: " + a.getFila() + a.getNumero());
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

            if (evSel == null || zonaSel == null || metodoPago == null) {
                estilo(lblMsg, "Selecciona evento, zona y metodo de pago.", false);
                return;
            }
            if (!evSel.hayDisponibilidad()) {
                estilo(lblMsg, "No hay disponibilidad en este evento.", false);
                return;
            }

            // Validar que se seleccione asiento si la zona los tiene
            if (!zonaSel.getAsientos().isEmpty() && asientoSeleccionado[0] == null) {
                estilo(lblMsg, "Selecciona un asiento en el mapa.", false);
                return;
            }

            // Reservar el asiento seleccionado
            if (asientoSeleccionado[0] != null) {
                ServicioAsiento servicioAsiento = new ServicioAsiento();
                List<Asiento> aReservar = new ArrayList<>();
                aReservar.add(asientoSeleccionado[0]);
                if (!servicioAsiento.reservarAsientos(aReservar)) {
                    estilo(lblMsg, "El asiento ya no esta disponible.", false);
                    if (botonSeleccionado[0] != null) {
                        botonSeleccionado[0].setStyle(estiloOcupado);
                        botonSeleccionado[0].setDisable(true);
                    }
                    asientoSeleccionado[0] = null;
                    botonSeleccionado[0]   = null;
                    return;
                }
            }

            Usuario usuario = usuarioController.getUsuarioActual();

            IEntrada entrada = new EntradaBase("Entrada " + zonaSel.getNombre(), zonaSel.getPrecioBase());
            if (chkVip.isSelected())    entrada = new AccesoVIPDecorator(entrada);
            if (chkSeguro.isSelected()) entrada = new SeguroCancelacionDecorator(entrada);
            if (chkMerch.isSelected())  entrada = new MerchandisingDecorator(entrada);

            IMetodoPago metodo = "PSE".equals(metodoPago)
                    ? new PSEAdapter(new PSE())
                    : new TarjetaAdapter(new TarjetaCredito());

            Compra compra = compraController.realizarCompra(usuario, evSel, entrada, metodo);
            if (compra == null) {
                estilo(lblMsg, "El pago no pudo procesarse.", false);
                return;
            }

            // Marcar el asiento ocupado en el mapa tras compra exitosa
            if (botonSeleccionado[0] != null) {
                botonSeleccionado[0].setStyle(estiloOcupado);
                botonSeleccionado[0].setDisable(true);
            }
            asientoSeleccionado[0] = null;
            botonSeleccionado[0]   = null;
            lblAsientoInfo.setText("");

            estilo(lblMsg, "Compra realizada! ID: " + compra.getIdCompra()
                    + "  Total: $" + compra.getTotal(), true);
        });

        contenido.getChildren().addAll(titulo,
                new Label("Evento:"), cbEvento,
                new Label("Zona:"), cbZona,
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