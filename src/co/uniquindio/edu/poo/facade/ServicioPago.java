package co.uniquindio.edu.poo.facade;

import co.uniquindio.edu.poo.model.Pago;

public class ServicioPago {

    public boolean procesarPago(Pago pago){
        if(pago.getValor()<=0){
            System.out.println("El Pago no ha sido realizado");
            return false;
        }
        System.out.println("El Pago ha sido realizado Correctamente");
        System.out.println("Pago a realizar :"+pago.getValor());

        return true;
    }
}
