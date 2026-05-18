package co.uniquindio.edu.proyecto_final_jfx.model.patrones.creacionales.factory;

public class TeatroFactory extends EventoFactory{

    public Evento crearEvento(String nombre,
                              String ciudad,
                              double precioBase) {

        Evento teatro = new Evento();

        teatro.setNombre(nombre);
        teatro.setCiudad(ciudad);
        teatro.setPrecioBase(precioBase);
        teatro.setCategoria("Teatro");

        return teatro;
    }
}
