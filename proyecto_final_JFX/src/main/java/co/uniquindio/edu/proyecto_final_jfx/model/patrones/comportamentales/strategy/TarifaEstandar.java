package co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.strategy;

import co.uniquindio.edu.proyecto_final_jfx.model.evento.Zona;
import co.uniquindio.edu.proyecto_final_jfx.model.usuario.Usuario;

public class TarifaEstandar implements IEstrategiaTarifa {

    /**
     * Retorna el precio base de la zona sin aplicar ningún descuento.
     *
     * @param zona    zona del recinto
     * @param usuario usuario al que se le aplica la tarifa (no se usa en esta estrategia)
     * @return precio base de la zona
     */
    @Override
    public double calcular(Zona zona, Usuario usuario) {
        return zona.getPrecioBase();
    }
}
