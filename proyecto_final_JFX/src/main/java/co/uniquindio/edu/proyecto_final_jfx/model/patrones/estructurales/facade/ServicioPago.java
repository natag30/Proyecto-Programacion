package co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.facade;

import co.uniquindio.edu.proyecto_final_jfx.model.compra.Pago;

public class ServicioPago {

    public boolean procesarPago(Pago pago){
        if(pago.getMonto()<=0){
            System.out.println("El Pago no ha sido realizado");
            return false;
        }
        System.out.println("El Pago ha sido realizado Correctamente");
        System.out.println("Pago a realizar :"+pago.getMonto());

        return true;
    }
}
