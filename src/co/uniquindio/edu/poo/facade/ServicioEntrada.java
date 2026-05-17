package co.uniquindio.edu.poo.facade;

import co.uniquindio.edu.poo.model.Asiento;
import co.uniquindio.edu.poo.model.Usuario;

import java.util.List;

public class ServicioEntrada {

    public void generarEntrada(Usuario usuario, List<Asiento> asiento){

        System.out.println("Su entrada a sido Generada ");
        System.out.println("Usuario :"+usuario.getNombre());
        System.out.println("Asiento :");
        for(Asiento asiento :asientos){
                System.out.println("el numero se asiento es :"+asiento.getNumero());
            }
    }
}
