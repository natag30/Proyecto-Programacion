package co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.facade;
import co.uniquindio.edu.proyecto_final_jfx.model.evento.Asiento;

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
