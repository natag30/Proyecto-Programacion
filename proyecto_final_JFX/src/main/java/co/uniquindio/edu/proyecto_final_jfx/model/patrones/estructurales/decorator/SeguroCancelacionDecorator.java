package co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.decorator;

public class SeguroCancelacionDecorator extends ServicioDecorator{

    /**
     * Crea un decorador que agrega un seguro de cancelación a la entrada recibida.
     *
     * @param entrada entrada a la que se le agrega el seguro de cancelación
     */
    public SeguroCancelacionDecorator(IEntrada entrada) {
        super(entrada);
    }

    /**
     * Retorna la descripción de la entrada con el seguro de cancelación incluido.
     *
     * @return descripción extendida con ", con Seguro de Cancelación"
     */
    @Override
    public String getDescription() {
        return entrada.getDescription() + ", con Seguro de Cancelación";
    }

    /**
     * Retorna el precio de la entrada más el costo del seguro de cancelación.
     *
     * @return precio total con seguro de cancelación sumado
     */
    @Override
    public double getPrecio() {
        return entrada.getPrecio() + 15000.0;
    }

}