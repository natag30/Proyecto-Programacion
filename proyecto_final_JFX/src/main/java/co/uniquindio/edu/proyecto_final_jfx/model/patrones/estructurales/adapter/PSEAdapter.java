package co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.adapter;

import main.java.co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.adapter.PSE;

public class PSEAdapter implements IMetodoPago {

    private PSE pse;

    public PSEAdapter(PSE pse) {
        this.pse = pse;
    }

    @Override
    public void realizarPago(double monto) {
        String amount = String.valueOf(monto);
        pse.sendPayment(amount, "COP");
        System.out.println("Pagando " + monto + " con PSE.");
    }

}