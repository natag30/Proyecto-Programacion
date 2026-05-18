package co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.adapter;

public interface IMetodoPago {

    /**
     * Realiza el pago del monto indicado usando el método de pago implementado.
     *
     * @param monto cantidad de dinero a pagar
     */
    void realizarPago(double monto);

}