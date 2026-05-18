package co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.decorator;

public class MerchandisingDecorator extends ServicioDecorator{

    /**
     * Crea un decorador que agrega merchandising a la entrada recibida.
     *
     * @param entrada entrada a la que se le agrega el merchandising
     */
    public MerchandisingDecorator(IEntrada entrada) {
        super(entrada);
    }

    /**
     * Retorna la descripción de la entrada con el merchandising incluido.
     *
     * @return descripción extendida con ", con Merchandising"
     */
    @Override
    public String getDescription() {
        return entrada.getDescription() + ", con Merchandising";
    }

    /**
     * Retorna el precio de la entrada más el costo del merchandising.
     *
     * @return precio total con merchandising sumado
     */
    @Override
    public double getPrecio() {
        return entrada.getPrecio() + 20000.0;
    }

}