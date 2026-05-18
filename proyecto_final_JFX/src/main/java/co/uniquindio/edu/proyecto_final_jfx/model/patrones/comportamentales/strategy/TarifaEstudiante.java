package co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.strategy;

import co.uniquindio.edu.proyecto_final_jfx.model.evento.Zona;
import co.uniquindio.edu.proyecto_final_jfx.model.usuario.Usuario;

public class TarifaEstudiante implements IEstrategiaTarifa {

    private static final double DESCUENTO = 0.20;

    /**
     * Calcula el precio con un descuento del 20% sobre el precio base de la zona.
     *
     * @param zona    zona del recinto
     * @param usuario usuario al que se le aplica la tarifa (no se usa en esta estrategia)
     * @return precio con descuento de estudiante aplicado
     */
    @Override
    public double calcular(Zona zona, Usuario usuario) {
        return zona.getPrecioBase() * (1 - DESCUENTO);
    }
}
