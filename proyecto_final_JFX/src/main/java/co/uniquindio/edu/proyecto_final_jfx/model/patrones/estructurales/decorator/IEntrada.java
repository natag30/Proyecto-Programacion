package co.uniquindio.edu.proyecto_final_jfx.model.patrones.estructurales.decorator;

public interface IEntrada {

    /**
     * Retorna la descripción de la entrada con todos los servicios que tiene.
     *
     * @return texto con la descripción de la entrada
     */
    String getDescription();

    /**
     * Retorna el precio total de la entrada incluyendo todos los servicios agregados.
     *
     * @return precio total de la entrada
     */
    double getPrecio();

}
