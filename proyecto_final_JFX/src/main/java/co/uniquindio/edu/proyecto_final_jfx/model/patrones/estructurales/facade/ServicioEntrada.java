package co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.facade;

import co.uniquindio.edu.proyecto_final_jfx.model.evento.Asiento;
import co.uniquindio.edu.proyecto_final_jfx.model.usuario.Usuario;

import java.util.List;

public class ServicioEntrada {

    public void generarEntrada(Usuario usuario, List<Asiento> asiento){

        System.out.println("Su entrada a sido Generada ");
        System.out.println("Usuario :"+usuario.getNombre());
        System.out.println("Asiento :");
        for(Asiento a :asiento){
                System.out.println("el numero se asiento es :"+a.getNumero());
            }
    }
}
