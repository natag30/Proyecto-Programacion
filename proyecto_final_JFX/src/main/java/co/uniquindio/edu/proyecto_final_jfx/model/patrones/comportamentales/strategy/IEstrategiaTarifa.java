package co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.strategy;

import co.uniquindio.edu.proyecto_final_jfx.model.evento.Zona;
import co.uniquindio.edu.proyecto_final_jfx.model.usuario.Usuario;

public interface IEstrategiaTarifa {

    /**
     * Calcula y retorna el precio final según la zona y el usuario.
     *
     * @param zona    zona del recinto para la que se calcula el precio
     * @param usuario usuario al que se le aplica la tarifa
     * @return precio final calculado
     */
    double calcular(Zona zona, Usuario usuario);
}
