package co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.decorator;

public class EntradaBase implements IEntrada{
    private String description;
    private double precio;

    /**
     * Crea una entrada base con su descripción y precio inicial.
     *
     * @param descripcion texto que describe la entrada
     * @param precio      precio base de la entrada
     */
    public EntradaBase(String descripcion, double precio) {
        this.description = descripcion;
        this.precio = precio;
    }

    /**
     * Retorna la descripción de la entrada.
     *
     * @return texto descriptivo de la entrada
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Retorna el precio base de la entrada.
     *
     * @return precio de la entrada
     */
    @Override
    public double getPrecio() {
        return precio;
    }



}
