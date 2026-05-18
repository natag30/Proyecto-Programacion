package co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.adapter;

public class PSEAdapter implements IMetodoPago {

    private PSE pse;

    /**
     * Crea un adaptador que usa el objeto PSE dado para realizar pagos.
     *
     * @param pse objeto PSE con el que se van a procesar los pagos
     */
    public PSEAdapter(PSE pse) {
        this.pse = pse;
    }

    /**
     * Realiza el pago del monto indicado a través de PSE.
     *
     * @param monto cantidad de dinero a pagar
     */
    @Override
    public void realizarPago(double monto) {
        String amount = String.valueOf(monto);
        pse.sendPayment(amount, "COP");
        System.out.println("Pagando " + monto + " con PSE.");
    }

}
