package co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.adapter;


import main.java.co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.adapter.TarjetaCredito;

public class TarjetaAdapter implements IMetodoPago {
    private TargetaCredito tarjetaCredito;

    public TarjetaAdapter(TarjetaCredito tarjetaCredito) {
        this.tarjetaCredito = tarjetaCredito;
    }

    @Override
    public void realizarPago(double monto) {
        tarjetaCredito.doPayment(monto);
        System.out.println("Pagando " + monto + " con Tarjeta de Crédito.");
    }

}
