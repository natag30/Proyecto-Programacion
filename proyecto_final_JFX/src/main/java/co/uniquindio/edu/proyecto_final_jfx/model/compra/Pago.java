package co.uniquindio.edu.proyecto_final_jfx.model.compra;

import co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.adapter.IMetodoPago;

import java.time.LocalDate;

public class Pago {

    private static int contador = 0;

    private int idPago;
    private double monto;
    private LocalDate fecha;
    private String estadoPago;
    private IMetodoPago metodoPago;

    /**
     * Crea un pago con el monto y el método de pago indicados, y lo deja en estado PENDIENTE.
     *
     * @param monto      cantidad de dinero a pagar
     * @param metodoPago método con el que se realiza el pago
     */
    public Pago(double monto, IMetodoPago metodoPago) {
        this.idPago     = ++contador;
        this.monto      = monto;
        this.metodoPago = metodoPago;
        this.fecha      = LocalDate.now();
        this.estadoPago = "PENDIENTE";
    }

    /**
     * Realiza el pago usando el método de pago registrado.
     * Si el pago se hace bien, cambia el estado a COMPLETADO; si falla, lo deja en FALLIDO.
     *
     * @return true si el pago se completó, false si ocurrió un error
     */
    public boolean procesar() {
        try {
            metodoPago.realizarPago(monto);
            estadoPago = "COMPLETADO";
            return true;
        } catch (Exception e) {
            estadoPago = "FALLIDO";
            return false;
        }
    }

    /**
     * Retorna el identificador único del pago.
     *
     * @return id del pago
     */
    public int getIdPago()          { return idPago; }

    /**
     * Retorna el monto del pago.
     *
     * @return monto a pagar
     */
    public double getMonto()        { return monto; }

    /**
     * Retorna la fecha en que se creó el pago.
     *
     * @return fecha del pago
     */
    public LocalDate getFecha()     { return fecha; }

    /**
     * Retorna el estado actual del pago.
     *
     * @return estado del pago como texto
     */
    public String getEstadoPago()   { return estadoPago; }

    /**
     * Retorna el método de pago usado.
     *
     * @return método de pago
     */
    public IMetodoPago getMetodoPago() { return metodoPago; }

    @Override
    public String toString() {
        return "Pago{idPago=" + idPago +
                ", monto=" + monto +
                ", fecha=" + fecha +
                ", estadoPago='" + estadoPago + '\'' + '}';
    }
}