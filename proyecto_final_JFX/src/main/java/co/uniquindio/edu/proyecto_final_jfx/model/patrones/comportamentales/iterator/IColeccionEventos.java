package co.uniquindio.edu.proyecto_final_jfx.model.patrones.comportamentales.iterator;

public interface IColeccionEventos {

    /**
     * Crea y retorna un iterador para recorrer los eventos de la colección.
     *
     * @return iterador sobre los eventos
     */
    IIteradorEventos crearIterator();
}
