package co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.decorator;

public class AccesoVIPDecorator extends ServicioDecorator{

    /**
     * Crea un decorador que agrega acceso VIP a la entrada recibida.
     *
     * @param entrada entrada a la que se le agrega el acceso VIP
     */
    public AccesoVIPDecorator(IEntrada entrada) {
        super(entrada);
    }

    /**
     * Retorna la descripción de la entrada con el acceso VIP incluido.
     *
     * @return descripción extendida "con Acceso VIP"
     */
    @Override
    public String getDescription() {
        return entrada.getDescription()+", con Acceso VIP";
    }

    /**
     * Retorna el precio de la entrada más el costo del acceso VIP.
     *
     * @return precio total con acceso VIP sumado
     */
    @Override
    public double getPrecio() {
        return entrada.getPrecio() + 50000.0;
    }

}
