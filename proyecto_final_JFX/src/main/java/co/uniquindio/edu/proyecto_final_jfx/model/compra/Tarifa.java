package co.uniquindio.edu.proyecto_final_jfx.model.compra;

import co.uniquindio.edu.proyecto_final_jfx.model.evento.Zona;
import co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.strategy.IEstrategiaTarifa;
import co.uniquindio.edu.proyecto_final_jfx.model.usuario.Usuario;

public class Tarifa {

    private double precioBase;
    private double factorAjuste;
    private IEstrategiaTarifa estrategia;

    /**
     * Crea una tarifa con precio base, factor de ajuste y la estrategia de cálculo.
     *
     * @param precioBase   precio de partida antes de aplicar la estrategia
     * @param factorAjuste factor numérico que ajusta el precio
     * @param estrategia   estrategia que define cómo se calcula el precio final
     */
    public Tarifa(double precioBase, double factorAjuste, IEstrategiaTarifa estrategia) {
        this.precioBase    = precioBase;
        this.factorAjuste  = factorAjuste;
        this.estrategia    = estrategia;
    }

    /**
     * Calcula y retorna el precio final usando la estrategia, la zona y el usuario dados.
     *
     * @param zona    zona del recinto para la que se calcula el precio
     * @param usuario usuario al que se le aplica la tarifa
     * @return precio final calculado por la estrategia
     */
    public double calcularPrecioFinal(Zona zona, Usuario usuario) {
        return estrategia.calcular(zona, usuario);
    }

    /**
     * Cambia la estrategia de cálculo de la tarifa.
     *
     * @param estrategia nueva estrategia que se quiere usar
     */
    public void setEstrategia(IEstrategiaTarifa estrategia) {
        this.estrategia = estrategia;
    }

    /**
     * Retorna el precio base de la tarifa.
     *
     * @return precio base
     */
    public double getPrecioBase()           { return precioBase; }

    /**
     * Retorna el factor de ajuste de la tarifa.
     *
     * @return factor de ajuste
     */
    public double getFactorAjuste()         { return factorAjuste; }

    /**
     * Retorna la estrategia de cálculo actual.
     *
     * @return estrategia de tarifa
     */
    public IEstrategiaTarifa getEstrategia(){ return estrategia; }

    @Override
    public String toString() {
        return "Tarifa{precioBase=" + precioBase +
                ", factorAjuste=" + factorAjuste +
                ", estrategia=" + estrategia.getClass().getSimpleName() + '}';
    }
}
