package co.uniquindio.edu.proyecto_final_jfx.model.compra;

import co.uniquindio.edu.proyecto_final_jfx.model.enums.EstadoCompra;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Evento;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.decorator.IEntrada;
import co.uniquindio.edu.proyecto_final_jfx.model.usuario.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Compra {

    private static int contador = 0;

    private int idCompra;
    private LocalDate fechaCreacion;
    private double total;
    private EstadoCompra estado;
    private List<IEntrada> entradas;
    private List<ServicioAdicional> servicios;
    private Pago pago;
    private Usuario usuario;
    private Evento evento;

    private Compra(Builder builder) {
        this.idCompra      = ++contador;
        this.fechaCreacion = LocalDate.now();
        this.estado        = EstadoCompra.CREADA;
        this.usuario       = builder.usuario;
        this.evento        = builder.evento;
        this.entradas      = builder.entradas;
        this.servicios     = builder.servicios;
        this.pago          = builder.pago;
        this.total         = getTotal();
    }

    /**
     * Calcula y retorna el total de la compra sumando el precio de todas las entradas.
     *
     * @return suma de los precios de las entradas
     */
    public double getTotal() {
        return entradas.stream().mapToDouble(IEntrada::getPrecio).sum();
    }

    /**
     * Cambia el estado de la compra a CONFIRMADA si ya fue pagada.
     *
     * @return true si se confirmó, false si aún no estaba pagada
     */
    public boolean confirmar() {
        if (estado != EstadoCompra.PAGADA) return false;
        estado = EstadoCompra.CONFIRMADA;
        return true;
    }

    /**
     * Cambia el estado de la compra a CANCELADA si estaba creada o pagada.
     *
     * @return true si se canceló, false si el estado no lo permite
     */
    public boolean cancelar() {
        if (estado != EstadoCompra.CREADA && estado != EstadoCompra.PAGADA) return false;
        estado = EstadoCompra.CANCELADA;
        return true;
    }

    /**
     * Retorna el identificador único de la compra.
     *
     * @return id de la compra
     */
    public int getIdCompra()                      { return idCompra; }

    /**
     * Retorna la fecha en que se creó la compra.
     *
     * @return fecha de creación
     */
    public LocalDate getFechaCreacion()           { return fechaCreacion; }

    /**
     * Retorna el total de la compra. Si recompute es true, lo recalcula desde las entradas.
     *
     * @param recompute indica si se debe recalcular el total
     * @return valor total de la compra
     */
    public double getTotal(boolean recompute)     { return recompute ? getTotal() : total; }

    /**
     * Retorna el estado actual de la compra.
     *
     * @return estado de la compra
     */
    public EstadoCompra getEstado()               { return estado; }

    /**
     * Retorna la lista de entradas de la compra.
     *
     * @return lista de entradas
     */
    public List<IEntrada> getEntradas()           { return entradas; }

    /**
     * Retorna la lista de servicios adicionales de la compra.
     *
     * @return lista de servicios adicionales
     */
    public List<ServicioAdicional> getServicios() { return servicios; }

    /**
     * Retorna el pago asociado a la compra.
     *
     * @return pago de la compra
     */
    public Pago getPago()                         { return pago; }

    /**
     * Retorna el usuario que realizó la compra.
     *
     * @return usuario comprador
     */
    public Usuario getUsuario()                   { return usuario; }

    /**
     * Retorna el evento al que corresponde la compra.
     *
     * @return evento de la compra
     */
    public Evento getEvento()                     { return evento; }

    public void setEstado(EstadoCompra estado)    { this.estado = estado; }

    @Override
    public String toString() {
        return "Compra{idCompra=" + idCompra +
                ", fechaCreacion=" + fechaCreacion +
                ", total=" + total +
                ", estado=" + estado +
                ", usuario=" + usuario.getNombre() +
                ", evento=" + evento.getNombre() +
                ", entradas=" + entradas.size() + '}';
    }

    // ─── Builder ─────────────────────────────────────────────────────────────

    public static class Builder {

        private Usuario usuario;
        private Evento evento;
        private List<IEntrada> entradas          = new ArrayList<>();
        private List<ServicioAdicional> servicios = new ArrayList<>();
        private Pago pago;

        /**
         * Asigna el usuario a la compra que se está construyendo.
         *
         * @param u usuario que realiza la compra
         * @return el mismo Builder para encadenar llamadas
         */
        public Builder conUsuario(Usuario u) {
            this.usuario = u;
            return this;
        }

        /**
         * Asigna el evento a la compra que se está construyendo.
         *
         * @param e evento al que pertenece la compra
         * @return el mismo Builder para encadenar llamadas
         */
        public Builder conEvento(Evento e) {
            this.evento = e;
            return this;
        }

        /**
         * Agrega una entrada a la lista de entradas de la compra.
         *
         * @param e entrada que se quiere agregar
         * @return el mismo Builder para encadenar llamadas
         */
        public Builder conEntrada(IEntrada e) {
            this.entradas.add(e);
            return this;
        }

        /**
         * Agrega un servicio adicional a la compra.
         *
         * @param s servicio que se quiere agregar
         * @return el mismo Builder para encadenar llamadas
         */
        public Builder conServicio(ServicioAdicional s) {
            this.servicios.add(s);
            return this;
        }

        /**
         * Asigna el pago a la compra que se está construyendo.
         *
         * @param p pago que se quiere asociar
         * @return el mismo Builder para encadenar llamadas
         */
        public Builder conPago(Pago p) {
            this.pago = p;
            return this;
        }

        /**
         * Crea y retorna la compra con los datos cargados en el Builder.
         *
         * @return nueva instancia de Compra
         */
        public Compra build() {
            if (usuario == null)    throw new IllegalStateException("El usuario no puede ser null");
            if (evento == null)     throw new IllegalStateException("El evento no puede ser null");
            if (entradas.isEmpty()) throw new IllegalStateException("La compra debe tener al menos una entrada");
            return new Compra(this);
        }
    }
}