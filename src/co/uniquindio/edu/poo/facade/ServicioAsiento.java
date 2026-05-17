package co.uniquindio.edu.poo.facade;


import co.uniquindio.edu.poo.model.Asiento;

import java.util.List;

public class ServicioAsiento {


    public boolean reservarAsientos(List<Asiento> asientos) {
        for (Asiento asiento : asientos) {
            if (!asiento.isDisponible()) {
                System.out.println("El Asiento" + asiento.getNumero() + "No esta Disponible");
                return false;
            }

        }
        for(Asiento asiento : asientos){
            asiento.ocupar();
            System.out.println("Asiento reservado"+asiento.getNumero());
        }
       return true;
    }
}
