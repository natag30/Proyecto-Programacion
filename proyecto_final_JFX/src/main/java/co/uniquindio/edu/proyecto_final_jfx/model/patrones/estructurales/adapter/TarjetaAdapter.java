package co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.adapter;

public class TarjetaAdapter implements IMetodoPago {
    private TarjetaCredito tarjetaCredito;

    /**
     * Crea un adaptador que usa la tarjeta de crédito dada para realizar pagos.
     *
     * @param tarjetaCredito tarjeta de crédito con la que se procesan los pagos
     */
    public TarjetaAdapter(TarjetaCredito tarjetaCredito) {
        this.tarjetaCredito = tarjetaCredito;
    }

    /**
     * Realiza el pago del monto indicado usando la tarjeta de crédito.
     *
     * @param monto cantidad de dinero a pagar
     */
    @Override
    public void realizarPago(double monto) {
        tarjetaCredito.doPayment(monto);
        System.out.println("Pagando " + monto + " con Tarjeta de Crédito.");
    }

}
