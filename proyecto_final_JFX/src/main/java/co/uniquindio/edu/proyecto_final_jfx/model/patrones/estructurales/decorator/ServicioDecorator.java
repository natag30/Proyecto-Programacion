package co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.decorator;

public abstract class ServicioDecorator implements IEntrada{
    protected IEntrada entrada;

    /**
     * Crea un decorador que envuelve la entrada recibida para agregarle servicios.
     *
     * @param entrada entrada a la que se le agregan servicios
     */
    public ServicioDecorator(IEntrada entrada) {
        this.entrada = entrada;
    }

    /**
     * Retorna la descripción de la entrada decorada.
     *
     * @return descripción de la entrada con los servicios agregados
     */
    @Override
    public String getDescription() {
        return entrada.getDescription();
    }

    /**
     * Retorna el precio de la entrada decorada.
     *
     * @return precio de la entrada con los servicios incluidos
     */
    @Override
    public double getPrecio() {
        return entrada.getPrecio();
    }

}
